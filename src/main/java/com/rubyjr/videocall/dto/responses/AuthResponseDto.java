package com.rubyjr.videocall.dto.responses;

import java.util.Date;

public class AuthResponseDto {

    private Long userId;
    private String email;
    private String name;
    private String token;
    private Date expiresAt;

    public AuthResponseDto() {
    }

    public AuthResponseDto(String token, Date expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public AuthResponseDto(Long userId, String email, String name, String token, Date expiresAt) {
        this.userId = userId;
        this.email = email;
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}