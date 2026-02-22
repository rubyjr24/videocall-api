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
@Entity
@Table(name = "user_favorites", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "user_favorite_id"})})
public class UserFavorite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @JoinColumn(name = "user_favorite_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User userFavoriteId;
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User userId;

    public UserFavorite() {
    }

    public UserFavorite(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserFavoriteId() {
        return userFavoriteId;
    }

    public void setUserFavoriteId(User userFavoriteId) {
        this.userFavoriteId = userFavoriteId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof UserFavorite)) {
            return false;
        }
        UserFavorite other = (UserFavorite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserFavorite{" +
                "id=" + id +
                ", userFavoriteId=" + userFavoriteId +
                ", userId=" + userId +
                '}';
    }
}
