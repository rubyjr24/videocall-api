package com.rubyjr.videocall.security;

import com.rubyjr.videocall.dto.responses.ErrorResponseDto;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class WebsocketExceptionHandler {

    private static final String ERROR_PATH = "/private/errors";

    @MessageExceptionHandler(Exception.class)
    @SendToUser(ERROR_PATH)
    public ErrorResponseDto handle(Exception ex) {
        ex.printStackTrace();
        return new ErrorResponseDto("INTERNAL_SERVER_ERROR", "Something went wrong");
    }

}
