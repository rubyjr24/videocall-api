package com.rubyjr.videocall.controller;

import com.rubyjr.videocall.dto.responses.AuthResponseDto;
import com.rubyjr.videocall.dto.requests.LoginRequestDto;
import com.rubyjr.videocall.dto.requests.SignUpRequestDto;
import com.rubyjr.videocall.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return this.loginService.login(loginRequestDto);
    }

    @PostMapping("/sign-up")
    public AuthResponseDto signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        return this.loginService.signUp(signUpRequestDto);
    }


}
