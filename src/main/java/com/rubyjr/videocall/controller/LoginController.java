package com.rubyjr.videocall.controller;

import com.rubyjr.videocall.dto.responses.AuthResponseDto;
import com.rubyjr.videocall.dto.requests.LoginRequestDto;
import com.rubyjr.videocall.dto.requests.SignUpRequestDto;
import com.rubyjr.videocall.service.LoginService;
import com.rubyjr.videocall.utilities.auth.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/auth/login")
    @ResponseBody
    public AuthResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return this.loginService.login(loginRequestDto);
    }

    @PostMapping("/auth/sign-up")
    @ResponseBody
    public AuthResponseDto signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        return this.loginService.signUp(signUpRequestDto);
    }

    @GetMapping(value = "/logout")
    public void logout(Authentication authentication){
        this.loginService.logout(AuthUtil.getUserIdFromAuthentication(authentication));
    }

}
