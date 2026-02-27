package com.rubyjr.videocall.dto;

import java.util.List;

public class RoomDto {

    private Long roomId;
    private String name;
    private List<UserDto> userInvitations;

    public RoomDto() {
    }

    public RoomDto(Long roomId, String name, List<UserDto> userInvitations) {
        this.roomId = roomId;
        this.name = name;
        this.userInvitations = userInvitations;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserDto> getUserInvitations() {
        return userInvitations;
    }

    public void setUserInvitations(List<UserDto> userInvitations) {
        this.userInvitations = userInvitations;
    }
}
