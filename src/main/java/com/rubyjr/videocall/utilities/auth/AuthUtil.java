package com.rubyjr.videocall.utilities.auth;


import com.rubyjr.videocall.exceptions.NotValidTokenException;
import com.rubyjr.videocall.model.Auth;
import com.rubyjr.videocall.model.User;
import com.rubyjr.videocall.repository.AuthRepository;
import com.rubyjr.videocall.repository.UserRepository;
import com.rubyjr.videocall.utilities.Assert;
import com.rubyjr.videocall.utilities.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class AuthUtil {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    @Autowired
    private Properties properties;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserRepository userRepository;

    public Long validateAndGetUser(String token){

        NotValidTokenException exception = new NotValidTokenException("The token must be valid");

        Assert.ifCondition(!this.jwtUtil.isTokenValid(token), exception);
        Long userId = this.jwtUtil.getUserId(token);

        Optional<User> userOptional = this.userRepository.findById(userId);
        Assert.ifCondition(userOptional.isEmpty(), exception);

        Auth auth = userOptional.get().getAuthId();

        Assert.ifCondition(auth.getExpiredAt().after(new Date()), exception);

        return userId;
    }

    public String getTokenFromAuthorization(String headerValue){
        return headerValue.replaceFirst(BEARER, "");
    }

}
