package com.rubyjr.videocall.dto;

import java.util.Date;

public class AuthDto {

    private String token;
    private Date expiresAt;

    public AuthDto() {
    }

    public AuthDto(String token, Date expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }
}