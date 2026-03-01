package com.rubyjr.videocall.exceptions;

public class ResourceNotBelongToUserException extends RuntimeException {
    public ResourceNotBelongToUserException(String message) {
        super(message);
    }
}
