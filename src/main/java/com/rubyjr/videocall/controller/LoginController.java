package com.rubyjr.videocall.controller;

import com.rubyjr.videocall.dto.responses.AuthResponseDto;
import com.rubyjr.videocall.dto.requests.LoginRequestDto;
import com.rubyjr.videocall.dto.requests.SignUpRequestDto;
import com.rubyjr.videocall.service.LoginService;
import com.rubyjr.videocall.utilities.auth.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
        Map<String, Object> details = (Map<String, Object>) authentication.getDetails();
        Long userId = (Long) details.get(JwtUtil.USER_ID_FIELD);

        this.loginService.logout(userId);
    }

}
