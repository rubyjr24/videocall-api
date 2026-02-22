package com.rubyjr.videocall.utilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Properties {

    @Value("${token.secret_key}")
    private String tokenSecretKey;

    @Value("${token.expiration_time}")
    private Long tokenExpirationTime;

    public Long getTokenExpirationTime() {
        return this.tokenExpirationTime;
    }

    public String getTokenSecretKey() {
        return this.tokenSecretKey;
    }

}
