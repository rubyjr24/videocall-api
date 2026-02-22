package com.rubyjr.videocall.repository;

import com.rubyjr.videocall.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {

}