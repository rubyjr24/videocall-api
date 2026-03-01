package com.rubyjr.videocall.dto;

import java.util.Date;

public class RoomInvitationDto {

    private RoomDto room;
    private UserDto user;
    private boolean isOwner;
    private Date joinedAt;

    public RoomInvitationDto() {
    }

    public RoomInvitationDto(RoomDto room, UserDto user, boolean isOwner, Date joinedAt) {
        this.room = room;
        this.user = user;
        this.isOwner = isOwner;
        this.joinedAt = joinedAt;
    }

    public RoomDto getRoom() {
        return room;
    }

    public void setRoom(RoomDto room) {
        this.room = room;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public Date getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Date joinedAt) {
        this.joinedAt = joinedAt;
    }
}