package com.rubyjr.videocall.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserFavoritePK implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_favorite_id")
    private Long userFavoriteId;

    public UserFavoritePK() {}

    public UserFavoritePK(Long userId, Long userFavoriteId) {
        this.userId = userId;
        this.userFavoriteId = userFavoriteId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getUserFavoriteId() {
        return userFavoriteId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserFavoritePK that = (UserFavoritePK) o;
        return Objects.equals(userId, that.userId) && Objects.equals(userFavoriteId, that.userFavoriteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userFavoriteId);
    }

    @Override
    public String toString() {
        return "UserFavoritePK{" +
                "userId=" + userId +
                ", userFavoriteId=" + userFavoriteId +
                '}';
    }
}