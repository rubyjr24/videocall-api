package com.rubyjr.videocall.controller;

import com.rubyjr.videocall.dto.UserDto;
import com.rubyjr.videocall.dto.requests.ContactRequest;
import com.rubyjr.videocall.service.UserService;
import com.rubyjr.videocall.utilities.auth.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @DeleteMapping(value = "/delete-account")
    public void deleteAccount(Authentication authentication){
        this.userService.deleteAccount(AuthUtil.getUserIdFromAuthentication(authentication));
    }

    @GetMapping(value = "/contacts")
    public List<UserDto> getContacts(Authentication authentication){
        return this.userService.getContacts(AuthUtil.getUserIdFromAuthentication(authentication));
    }

    @PostMapping(value = "/contact")
    @ResponseBody
    public UserDto setContact(@RequestBody ContactRequest contactRequest, Authentication authentication){
        return this.userService.setContact(
                contactRequest,
                AuthUtil.getUserIdFromAuthentication(authentication)
        );
    }

    @DeleteMapping(value = "/contact/{userFavoriteId}")
    public void deleteContact(@PathVariable Long userFavoriteId, Authentication authentication){
        this.userService.deleteContact(
                userFavoriteId,
                AuthUtil.getUserIdFromAuthentication(authentication)
        );
    }
}
