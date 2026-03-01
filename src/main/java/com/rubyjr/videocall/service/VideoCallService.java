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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.*;

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
        Assert.ifCondition(usersOptional.isEmpty(), new RuntimeException());// Cambiar

        List<User> users = usersOptional.get();
        Assert.ifCondition(users.contains(new User(userId)), new RuntimeException()); // Cambiar

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
                    user.getEmail(),
                    NEW_CALL_PATH,
                    roomDto
            );
        }

        return roomDto;
    }

    public RoomDto deleteRoom(Long roomId, Long userId){

        Optional<Room> roomOptional = this.roomRepository.findByIdFechingRoomInvitations(roomId);

        Assert.ifCondition(roomOptional.isEmpty(), new ResourceNotFoundException("The resource has not been found"));

        Room room = roomOptional.get();

        boolean isOwner = false;
        for (RoomInvitation roomInvitation : room.getRoomInvitationsList()){
            if (roomInvitation.getId().getUserId() == userId){
                isOwner = roomInvitation.isOwner();
                break;
            }
        }

        Assert.ifCondition(!isOwner, new ResourceNotBelongToUserException("The resource you are trying to delete does not belong to the user"));

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
                    user.getEmail(),
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

        RoomInvitation roomInvitationOfUser = null;
        for (RoomInvitation roomInvitation : room.getRoomInvitationsList()){
            if (roomInvitation.getId().getUserId() == userId){
                roomInvitationOfUser = roomInvitation;
                break;
            }
        }

        Assert.isNull(roomInvitationOfUser, new AccessDeniedException("You do not have access"));

        roomInvitationOfUser.setJoinedAt(new Date());
        roomInvitationOfUser = this.roomInvitationRepository.save(roomInvitationOfUser);

        this.simpMessagingTemplate.convertAndSend(
            String.format(NEW_PARTICIPANT_CALL_PATH, roomId),
            roomInvitationMapper.toDto(roomInvitationOfUser)
        );

        return roomMapper.toDto(room);

    }

}
