package com.rubyjr.videocall.controller;

import com.rubyjr.videocall.dto.requests.VideoCallRequestDto;
import com.rubyjr.videocall.dto.responses.VideoCallResponseDto;
import com.rubyjr.videocall.service.VideoCallService;
import com.rubyjr.videocall.utilities.auth.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/videocall")
public class VideoCallController {

    @Autowired
    private VideoCallService videoCallService;

    @PostMapping(value = "/create")
    @ResponseBody
    public VideoCallResponseDto createVideoCall(@Valid @RequestBody VideoCallRequestDto videoCallRequestDto, Authentication authentication){

        Map<String, Object> details = (Map<String, Object>) authentication.getDetails();
        Long userId = (Long) details.get(JwtUtil.USER_ID_FIELD);

        return this.videoCallService.createVideoCall(videoCallRequestDto, userId);
    }
}
