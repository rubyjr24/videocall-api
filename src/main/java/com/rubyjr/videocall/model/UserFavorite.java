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
@Entity
@Table(name = "user_favorites", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "user_favorite_id"})})
public class UserFavorite implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private UserFavoritePK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_favorite_id", insertable = false, updatable = false)
    private User userFavorite;

    public UserFavorite() {
    }

    public UserFavorite(UserFavoritePK id) {
        this.id = id;
    }

    public UserFavoritePK getId() {
        return id;
    }

    public void setId(UserFavoritePK id) {
        this.id = id;
    }

    public User getUserFavorite() {
        return userFavorite;
    }

    public void setUserFavorite(User userFavorite) {
        this.userFavorite = userFavorite;
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
        UserFavorite that = (UserFavorite) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserFavorite{" +
                "id=" + id +
                '}';
    }
}
