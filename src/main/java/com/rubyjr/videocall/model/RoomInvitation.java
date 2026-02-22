/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rubyjr.videocall.model;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;

/**
 *
 * @author Ruben
 */
@Entity
@Table(name = "room_invitations")
public class RoomInvitation implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RoomInvitationPK roomInvitationPK;
    @Basic(optional = false)
    @Column(name = "is_owner", nullable = false)
    private boolean isOwner;
    @Column(name = "joined_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinedAt;
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Room room;
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    public RoomInvitation() {
    }

    public RoomInvitation(RoomInvitationPK roomInvitationPK) {
        this.roomInvitationPK = roomInvitationPK;
    }

    public RoomInvitation(RoomInvitationPK roomInvitationPK, boolean isOwner) {
        this.roomInvitationPK = roomInvitationPK;
        this.isOwner = isOwner;
    }

    public RoomInvitation(long roomId, long userId) {
        this.roomInvitationPK = new RoomInvitationPK(roomId, userId);
    }

    public RoomInvitationPK getRoomInvitationsPK() {
        return roomInvitationPK;
    }

    public void setRoomInvitationsPK(RoomInvitationPK roomInvitationPK) {
        this.roomInvitationPK = roomInvitationPK;
    }

    public boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }

    public Date getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Date joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Room getRooms() {
        return room;
    }

    public void setRooms(Room room) {
        this.room = room;
    }

    public User getUsers() {
        return user;
    }

    public void setUsers(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomInvitationPK != null ? roomInvitationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoomInvitation)) {
            return false;
        }
        RoomInvitation other = (RoomInvitation) object;
        if ((this.roomInvitationPK == null && other.roomInvitationPK != null) || (this.roomInvitationPK != null && !this.roomInvitationPK.equals(other.roomInvitationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RoomInvitations{" +
                "roomInvitationsPK=" + roomInvitationPK +
                ", isOwner=" + isOwner +
                ", joinedAt=" + joinedAt +
                ", room=" + room +
                ", user=" + user +
                '}';
    }
}
