package com.rubyjr.videocall.dto.requests;

import jakarta.validation.constraints.*;

import java.util.List;

public class VideoCallRequestDto {

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotNull
    @NotEmpty
    private List<@Email String> emails;

    public VideoCallRequestDto() {
    }

    public VideoCallRequestDto(String name) {
        this.name = name;
    }

    public VideoCallRequestDto(String name, List<String> emails) {
        this.name = name;
        this.emails = emails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }
}
