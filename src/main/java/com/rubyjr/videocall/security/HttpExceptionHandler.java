package com.rubyjr.videocall.security;


import com.rubyjr.videocall.dto.responses.ErrorResponseDto;
import com.rubyjr.videocall.exceptions.EmailAlreadyExistsException;
import com.rubyjr.videocall.exceptions.IncorrectPasswordOfUserException;
import com.rubyjr.videocall.exceptions.NotValidTokenException;
import com.rubyjr.videocall.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handle(UserNotFoundException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("USER_NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handle(EmailAlreadyExistsException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("EMAIL_ALREADY_EXISTS", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectPasswordOfUserException.class)
    public ResponseEntity<ErrorResponseDto> handle(IncorrectPasswordOfUserException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("INCORRECT_PASSWORD_OF_USER", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotValidTokenException.class)
    public ResponseEntity<ErrorResponseDto> handle(NotValidTokenException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("NOT_VALID_TOKEN", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handle(IllegalArgumentException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("MALFORMED_REQUEST", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handle(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
            .forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
            );

        ErrorResponseDto errorResponse = new ErrorResponseDto("MALFORMED_REQUEST", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handle(HttpMessageNotReadableException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("MALFORMED_REQUEST", "The request is not correctly formatted");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ErrorResponseDto> handle(HttpMessageConversionException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("MALFORMED_REQUEST", "The request is not correctly formatted");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDto> handle() {
        ErrorResponseDto errorResponse = new ErrorResponseDto("NOT_FOUND", "The request's endpoint is not found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handle(Exception ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto("INTERNAL_SERVER_ERROR", "Something went wrong");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
