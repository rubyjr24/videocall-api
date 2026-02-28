/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rubyjr.videocall.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserFavorite> userFavoriteListUser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userFavorite", fetch = FetchType.LAZY)
    private List<UserFavorite> userFavoriteListUserFavorite;
    @JoinColumn(name = "auth_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Auth auth;

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

    public List<RoomInvitation> getRoomInvitationList() {
        return roomInvitationList;
    }

    public void setRoomInvitationList(List<RoomInvitation> roomInvitationList) {
        this.roomInvitationList = roomInvitationList;
    }

    public List<UserFavorite> getUserFavoriteListUser() {
        return userFavoriteListUser;
    }

    public void setUserFavoriteListUser(List<UserFavorite> userFavoriteListUser) {
        this.userFavoriteListUser = userFavoriteListUser;
    }

    public List<UserFavorite> getUserFavoriteListUserFavorite() {
        return userFavoriteListUserFavorite;
    }

    public void setUserFavoriteListUserFavorite(List<UserFavorite> userFavoriteListUserFavorite) {
        this.userFavoriteListUserFavorite = userFavoriteListUserFavorite;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
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
                ", auth=" + auth +
                '}';
    }
}
