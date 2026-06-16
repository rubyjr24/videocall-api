package com.rubyjr.videocall.service;

import com.rubyjr.videocall.dto.requests.VideoCallRequestDto;
import com.rubyjr.videocall.dto.RoomDto;
import com.rubyjr.videocall.exceptions.ResourceNotBelongToUserException;
import com.rubyjr.videocall.exceptions.ResourceNotFoundException;
import com.rubyjr.videocall.mapper.RoomInvitationMapper;
import com.rubyjr.videocall.mapper.RoomMapper;
import com.rubyjr.videocall.mapper.UserMapper;
import com.rubyjr.videocall.model.Room;
import com.rubyjr.videocall.model.RoomInvitation;
import com.rubyjr.videocall.model.RoomInvitationPK;
import com.rubyjr.videocall.model.User;
import com.rubyjr.videocall.repository.RoomInvitationRepository;
import com.rubyjr.videocall.repository.RoomRepository;
import com.rubyjr.videocall.repository.UserRepository;
import com.rubyjr.videocall.utilities.Assert;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VideoCallService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomInvitationRepository roomInvitationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private RoomInvitationMapper roomInvitationMapper;

    private static final String NEW_CALL_PATH = "/private/call/new";
    private static final String DELETE_CALL_PATH = "/private/call/delete";
    private static final String NEW_PARTICIPANT_CALL_PATH = "/app/call/%d/new/participant";

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public List<RoomDto> getAllVideoCallsOfUser(Long userId){

        Optional<List<Room>> roomsOptional = this.roomRepository.findAllRoomsByUserIdFechingRoomInvitations(userId, true);

        Assert.ifCondition(roomsOptional.isEmpty(), new RuntimeException()); // Cambiar

        List<Room> rooms = roomsOptional.get();

        return rooms.stream()
                .map(roomMapper::toDto)
                .toList();
    }

    public List<RoomDto> getAllInvitationsOfUser(Long userId){

        Optional<List<Room>> roomsOptional = this.roomRepository.findAllRoomsByUserIdFechingRoomInvitations(userId, false);

        Assert.ifCondition(roomsOptional.isEmpty(), new RuntimeException()); // Cambiar

        List<Room> rooms = roomsOptional.get();

        return rooms.stream()
                .map(roomMapper::toDto)
                .toList();
    }

    @Transactional
    public RoomDto createVideoCall(VideoCallRequestDto videoCallRequestDto, Long userId){

        Room room = new Room();
        room.setName(videoCallRequestDto.getName());
        room = this.roomRepository.save(room);

        List<RoomInvitation> roomInvitationList = new ArrayList<>();

        // El primero que añadimos es el owner, el que hace la petición
        roomInvitationList.add(new RoomInvitation(
            new RoomInvitationPK(room.getId(), userId),
            true
        ));

        Optional<List<User>> usersOptional = this.userRepository.findByEmails(videoCallRequestDto.getEmails());
        Assert.ifCondition(usersOptional.isEmpty(), new RuntimeException("No users were found"));

        List<User> users = usersOptional.get();
        Assert.ifCondition(users.contains(new User(userId)), new RuntimeException("The email list cannot be self-contained"));

        for (User user: users){
            roomInvitationList.add(
                new RoomInvitation(
                    new RoomInvitationPK(room.getId(), user.getId())
                )
            );
        }

        this.roomInvitationRepository.saveAll(roomInvitationList);

        RoomDto roomDto = new RoomDto(
                room.getId(),
                room.getName(),
                users.stream()
                        .map(userMapper::toDto)
                        .toList());

        for (User user: users){
            if (Objects.equals(user.getId(), userId)) continue;
            this.simpMessagingTemplate.convertAndSendToUser(
                    user.getId().toString(),
                    NEW_CALL_PATH,
                    roomDto
            );
        }

        return roomDto;
    }

    @Transactional
    public RoomDto deleteRoom(Long roomId, Long userId){

        Optional<Room> roomOptional = this.roomRepository.findByIdFechingRoomInvitations(roomId);

        Assert.ifCondition(roomOptional.isEmpty(), new ResourceNotFoundException("The resource has not been found"));

        Room room = roomOptional.get();
        Assert.ifCondition(!this.isUserIdOwner(room, userId), new ResourceNotBelongToUserException("The resource you are trying to delete does not belong to the user"));

        this.roomRepository.deleteById(roomId);

        RoomDto roomDto = roomMapper.toDto(room);

        List<Long> userIds = new ArrayList<>();

        for (RoomInvitation roomInvitation : room.getRoomInvitationsList()){
            if (roomInvitation.getId().getUserId() == userId) continue;
            userIds.add(roomInvitation.getId().getUserId());
        }

        Optional<List<User>> usersOptional = this.userRepository.findByIds(userIds);

        if (usersOptional.isEmpty()) return roomDto; // No tiramos excepción

        for (User user: usersOptional.get()){
            this.simpMessagingTemplate.convertAndSendToUser(
                    user.getId().toString(),
                    DELETE_CALL_PATH,
                    roomDto
            );
        }

        return roomDto;
    }

    private RoomInvitation getRoomInvitation(Room room, Long userId){
        for (RoomInvitation roomInvitation : room.getRoomInvitationsList()){
            if (roomInvitation.getId().getUserId() == userId){
                return roomInvitation;
            }
        }

        return null;
    }

    private boolean isUserIdOwner(Room room, Long userId){
        RoomInvitation roomInvitation = this.getRoomInvitation(room, userId);
        return roomInvitation != null && roomInvitation.isOwner();
    }

    @Transactional
    public RoomDto editRoom(Long roomId, String name, List<String> emails, Long userId){

        Optional<Room> roomOptional = this.roomRepository.findByIdFechingRoomInvitations(roomId);

        Assert.ifCondition(roomOptional.isEmpty(), new ResourceNotFoundException("The resource has not been found"));

        Room room = roomOptional.get();
        RoomInvitation roomInvitation = this.getRoomInvitation(room, userId);
        Assert.ifCondition(roomInvitation == null || !roomInvitation.isOwner(), new ResourceNotBelongToUserException("The resource you are trying to delete does not belong to the user"));

        Optional<List<User>> usersOptional = this.userRepository.findByEmails(emails);
        Assert.ifCondition(usersOptional.isEmpty(), new RuntimeException("No users were found"));

        List<User> users = usersOptional.get();
        Assert.ifCondition(users.contains(new User(userId)), new RuntimeException("The email list cannot be self-contained"));

        List<RoomInvitation> beforeEditInvitations = new ArrayList<>(room.getRoomInvitationsList());

        room.getRoomInvitationsList().clear();

        List<RoomInvitationPK> idsToDelete = beforeEditInvitations.stream()
                .map(RoomInvitation::getId)
                .toList();

        if (!idsToDelete.isEmpty()) {
            this.roomInvitationRepository.deleteAllById(idsToDelete);
            this.roomInvitationRepository.flush(); // Forzar los DELETE en la BD ahora mismo
        }

        room.getRoomInvitationsList().add(roomInvitation);
        for (User user: users){
            RoomInvitation roomInvitation1 = new RoomInvitation(
                new RoomInvitationPK(room.getId(), user.getId())
            );

            roomInvitation1.setRoom(room);
            roomInvitation1.setUser(user);
            roomInvitation1.setOwner(Objects.equals(user.getId(), userId));

            room.getRoomInvitationsList().add(roomInvitation1);
        }

        room.setName(name);
        room = this.roomRepository.save(room);
        RoomDto roomDto = this.roomMapper.toDto(room);

        Set<RoomInvitation> roomInvitationSet = new HashSet<>(room.getRoomInvitationsList());
        List<User> usersNotInvited = beforeEditInvitations.stream()
            .filter(elemento -> !roomInvitationSet.contains(elemento))
            .map(RoomInvitation::getUser)
            .toList();

        for (User user: usersNotInvited){
            this.simpMessagingTemplate.convertAndSendToUser(
                user.getId().toString(),
                DELETE_CALL_PATH,
                roomDto
            );
        }

        return roomDto;
    }

    public RoomDto joinVideoCall(Long roomId, Long userId){

        Optional<Room> roomOptional = this.roomRepository.findByIdFechingRoomInvitations(roomId);

        Assert.ifCondition(roomOptional.isEmpty(), new ResourceNotFoundException("The resource has not been found"));

        Room room = roomOptional.get();
        RoomInvitation roomInvitationOfUser = this.getRoomInvitation(room, userId);

        Assert.isNull(roomInvitationOfUser, new AccessDeniedException("You do not have access"));

        roomInvitationOfUser.setJoinedAt(new Date());
        roomInvitationOfUser.setLeftAt(null);
        roomInvitationOfUser = this.roomInvitationRepository.save(roomInvitationOfUser);

        this.simpMessagingTemplate.convertAndSend(
            String.format(NEW_PARTICIPANT_CALL_PATH, roomId),
            roomInvitationMapper.toDto(roomInvitationOfUser)
        );

        return roomMapper.toDto(room);

    }

}
