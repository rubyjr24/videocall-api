package com.rubyjr.videocall.controller;

import com.rubyjr.videocall.dto.requests.VideoCallRequestDto;
import com.rubyjr.videocall.dto.RoomDto;
import com.rubyjr.videocall.service.VideoCallService;
import com.rubyjr.videocall.utilities.auth.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/videocall")
public class VideoCallController {

    @Autowired
    private VideoCallService videoCallService;

    @GetMapping
    @ResponseBody
    public List<RoomDto> getAllVideoCallsOfUser(Authentication authentication){
        return this.videoCallService.getAllVideoCallsOfUser(AuthUtil.getUserIdFromAuthentication(authentication));
    }

    @PostMapping(value = "/create")
    @ResponseBody
    public RoomDto createVideoCall(@Valid @RequestBody VideoCallRequestDto videoCallRequestDto, Authentication authentication){
        return this.videoCallService.createVideoCall(
                videoCallRequestDto,
                AuthUtil.getUserIdFromAuthentication(authentication)
        );
    }
}
