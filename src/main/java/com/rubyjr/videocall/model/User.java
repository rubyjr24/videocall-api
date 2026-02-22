/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rubyjr.videocall.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

/**
 *
 * @author Ruben
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String name;
    @Basic(optional = false)
    @Column(nullable = false, length = 256)
    private String email;
    @Basic(optional = false)
    @Column(nullable = false, length = 256)
    private String password;
    @Basic(optional = false)
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private List<RoomInvitation> roomInvitationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userFavoriteId", fetch = FetchType.LAZY)
    private List<UserFavorite> userFavoriteList;
    @JoinColumn(name = "auth_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Auth authId;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String name, String email, String password, Date createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<RoomInvitation> getRoomInvitationsList() {
        return roomInvitationList;
    }

    public void setRoomInvitationsList(List<RoomInvitation> roomInvitationList) {
        this.roomInvitationList = roomInvitationList;
    }

    public List<UserFavorite> getUserFavoritesList() {
        return userFavoriteList;
    }

    public void setUserFavoritesList(List<UserFavorite> userFavoriteList) {
        this.userFavoriteList = userFavoriteList;
    }

    public Auth getAuthId() {
        return authId;
    }

    public void setAuthId(Auth authId) {
        this.authId = authId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", roomInvitationList=" + roomInvitationList +
                ", userFavoriteList=" + userFavoriteList +
                ", authId=" + authId +
                '}';
    }
}
