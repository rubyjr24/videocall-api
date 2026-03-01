/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rubyjr.videocall.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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
    protected RoomInvitationPK id;
    @Basic(optional = false)
    @Column(name = "is_owner", nullable = false)
    private boolean isOwner;
    @Column(name = "joined_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinedAt;
    @Column(name = "left_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date leftAt;
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Room room;
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    public RoomInvitation() {
    }

    public RoomInvitation(RoomInvitationPK id) {
        this.id = id;
    }

    public RoomInvitation(RoomInvitationPK id, boolean isOwner) {
        this.id = id;
        this.isOwner = isOwner;
    }

    public RoomInvitation(long roomId, long userId) {
        this.id = new RoomInvitationPK(roomId, userId);
    }

    public RoomInvitationPK getId() {
        return id;
    }

    public void setId(RoomInvitationPK id) {
        this.id = id;
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

    public Date getLeftAt() {
        return leftAt;
    }

    public void setLeftAt(Date leftAt) {
        this.leftAt = leftAt;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RoomInvitation that = (RoomInvitation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RoomInvitation{" +
                "leftAt=" + leftAt +
                ", joinedAt=" + joinedAt +
                ", isOwner=" + isOwner +
                ", id=" + id +
                '}';
    }
}
