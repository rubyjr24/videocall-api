package com.rubyjr.videocall.service;

import com.rubyjr.videocall.dto.requests.VideoCallRequestDto;
import com.rubyjr.videocall.dto.RoomDto;
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<RoomDto> getAllVideoCallsOfUser(Long userId){

        Optional<List<Room>> roomsOptional = this.roomRepository.findAllRoomsByUserIdFechingRoomInvitations(userId);

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

        return new RoomDto(
                room.getId(),
                room.getName(),
                users.stream()
                    .map(userMapper::toDto)
                    .toList()
        );
    }

}
