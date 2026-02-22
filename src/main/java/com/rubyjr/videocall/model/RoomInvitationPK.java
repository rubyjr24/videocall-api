/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rubyjr.videocall.model;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 *
 * @author Ruben
 */
@Embeddable
public class RoomInvitationPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "room_id", nullable = false)
    private int roomId;
    @Basic(optional = false)
    @Column(name = "user_id", nullable = false)
    private int userId;

    public RoomInvitationPK() {
    }

    public RoomInvitationPK(int roomId, int userId) {
        this.roomId = roomId;
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) roomId;
        hash += (int) userId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoomInvitationPK)) {
            return false;
        }
        RoomInvitationPK other = (RoomInvitationPK) object;
        if (this.roomId != other.roomId) {
            return false;
        }
        if (this.userId != other.userId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RoomInvitationsPK{" +
                "roomId=" + roomId +
                ", userId=" + userId +
                '}';
    }
}
