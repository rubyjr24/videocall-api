package com.rubyjr.videocall.repository;

import com.rubyjr.videocall.model.UserFavorite;
import com.rubyjr.videocall.model.UserFavoritePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFavoriteRepository extends JpaRepository<UserFavorite, UserFavoritePK> {


}