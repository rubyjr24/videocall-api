package com.rubyjr.videocall.controller;

import com.rubyjr.videocall.dto.requests.SignalMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;


@Controller
public class VideoCallWebsocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/room/{roomId}/signal")
    public void signal(
        @DestinationVariable String roomId,
        SignalMessage message,
        Principal principal
    ) {
        message.setFrom(principal.getName());

        if (message.getTo() != null){
             simpMessagingTemplate.convertAndSendToUser(
                message.getTo(),
                String.format("/private/call/%d/signal", message.getRoomId()),
                message
             );
        }else{
            simpMessagingTemplate.convertAndSend(
                "/app/room/" + roomId,
                message
            );
        }

    }

}
