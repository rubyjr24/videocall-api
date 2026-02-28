/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rubyjr.videocall.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;

/**
 *
 * @author Ruben
 */
@Embeddable
public class RoomInvitationPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "room_id", nullable = false)
    private long roomId;
    @Basic(optional = false)
    @Column(name = "user_id", nullable = false)
    private long userId;

    public RoomInvitationPK() {
    }

    public RoomInvitationPK(long roomId, long userId) {
        this.roomId = roomId;
        this.userId = userId;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RoomInvitationPK that = (RoomInvitationPK) o;
        return roomId == that.roomId && userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, userId);
    }

    @Override
    public String toString() {
        return "RoomInvitationPK{" +
                "roomId=" + roomId +
                ", userId=" + userId +
                '}';
    }
}
