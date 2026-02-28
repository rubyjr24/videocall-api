package com.rubyjr.videocall.service;

import com.rubyjr.videocall.model.User;
import com.rubyjr.videocall.repository.UserRepository;
import com.rubyjr.videocall.utilities.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void deleteAccount(Long userId){

        Optional<User> userOptional = this.userRepository.findById(userId);

        Assert.ifCondition(userOptional.isEmpty(), new RuntimeException()); //cambiar

        this.userRepository.delete(userOptional.get());
    }

}
