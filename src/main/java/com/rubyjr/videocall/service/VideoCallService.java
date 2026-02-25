package com.rubyjr.videocall.service;

import com.rubyjr.videocall.dto.requests.VideoCallRequestDto;
import com.rubyjr.videocall.dto.responses.VideoCallResponseDto;
import com.rubyjr.videocall.model.Room;
import com.rubyjr.videocall.model.RoomInvitation;
import com.rubyjr.videocall.model.RoomInvitationPK;
import com.rubyjr.videocall.repository.RoomInvitationRepository;
import com.rubyjr.videocall.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoCallService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomInvitationRepository roomInvitationRepository;

    public VideoCallResponseDto createVideoCall(VideoCallRequestDto videoCallRequestDto, Long userId){

        Room room = new Room();
        room.setName(videoCallRequestDto.getName());

        room = this.roomRepository.save(room);

        List<RoomInvitation> roomInvitationList = new ArrayList<>();

        // El primero que añadimos es el owner, el que hace la petición
        roomInvitationList.add(new RoomInvitation(
                new RoomInvitationPK(room.getId(), userId),
                true
        ));

        for (String email: videoCallRequestDto.getEmails()){
            roomInvitationList.add(
                new RoomInvitation(
                    new RoomInvitationPK(room.getId(), 1)
                )
            );

        }

        this.roomInvitationRepository.saveAll(roomInvitationList);


        return null;
    }

}
