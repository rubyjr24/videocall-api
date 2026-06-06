package com.rubyjr.videocall.component;

import com.rubyjr.videocall.dto.requests.SignalMessage;
import com.rubyjr.videocall.model.RoomInvitation;
import com.rubyjr.videocall.repository.RoomInvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Date;
import java.util.List;

@Component
public class WebSocketDisconnectListener {

    @Autowired
    private RoomInvitationRepository roomInvitationRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        // Envolver el mensaje para acceder a los headers de STOMP
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        Long userId = (headerAccessor.getUser() != null) ? Long.valueOf(headerAccessor.getUser().getName()) : -1;

        List<RoomInvitation> roomInvitations = this.roomInvitationRepository.findByLeftAt(userId);

        for (RoomInvitation roomInvitation: roomInvitations){

            SignalMessage signalMessage = new SignalMessage(
                "user-left",
                String.valueOf(userId),
                null,
                roomInvitation.getId().getRoomId(),
                null
            );

            simpMessagingTemplate.convertAndSend(
                "/app/room/" + roomInvitation.getId().getRoomId(),
                    signalMessage
            );

            roomInvitation.setLeftAt(new Date());
        }

        this.roomInvitationRepository.saveAll(roomInvitations);

    }
}