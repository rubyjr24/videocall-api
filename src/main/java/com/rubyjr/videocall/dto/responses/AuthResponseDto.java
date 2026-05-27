package com.rubyjr.videocall.dto.responses;

import java.util.Date;

public class AuthResponseDto {

    private Long userId;
    private String token;
    private Date expiresAt;

    public AuthResponseDto() {
    }

    public AuthResponseDto(String token, Date expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public AuthResponseDto(Long userId, String token, Date expiresAt) {
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}