package com.rubyjr.videocall.dto.responses;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {

    private String code;
    private String message;
    private Map<String, String> errors;

    public ErrorResponseDto() {
    }

    public ErrorResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponseDto(String code, Map<String, String> errors) {
        this.code = code;
        this.errors = errors;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}