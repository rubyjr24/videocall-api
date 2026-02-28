package com.rubyjr.videocall.controller;

import com.rubyjr.videocall.service.UserService;
import com.rubyjr.videocall.utilities.auth.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/delete-account")
    public void deleteAccount(Authentication authentication){
        Map<String, Object> details = (Map<String, Object>) authentication.getDetails();
        Long userId = (Long) details.get(JwtUtil.USER_ID_FIELD);

        this.userService.deleteAccount(userId);
    }
}
