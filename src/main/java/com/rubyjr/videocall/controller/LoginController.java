package com.rubyjr.videocall.controller;

import com.rubyjr.videocall.dto.AuthDto;
import com.rubyjr.videocall.dto.LoginDto;
import com.rubyjr.videocall.dto.SignUpDto;
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
    public AuthDto login(@Valid @RequestBody LoginDto loginDto) {
        return this.loginService.login(loginDto);
    }

    @PostMapping("/sign-up")
    public AuthDto signUp(@Valid @RequestBody SignUpDto signUpDto) {
        return this.loginService.signUp(signUpDto);
    }


}
