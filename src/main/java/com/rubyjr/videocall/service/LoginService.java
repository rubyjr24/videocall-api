package com.rubyjr.videocall.service;

import com.rubyjr.videocall.dto.AuthDto;
import com.rubyjr.videocall.dto.LoginDto;
import com.rubyjr.videocall.dto.SignUpDto;
import com.rubyjr.videocall.exceptions.EmailAlreadyExistsException;
import com.rubyjr.videocall.exceptions.IncorrectPasswordOfUserException;
import com.rubyjr.videocall.exceptions.UserNotFoundException;
import com.rubyjr.videocall.model.Auth;
import com.rubyjr.videocall.model.User;
import com.rubyjr.videocall.repository.AuthRepository;
import com.rubyjr.videocall.repository.UserRepository;
import com.rubyjr.videocall.utilities.Assert;
import com.rubyjr.videocall.utilities.Properties;
import com.rubyjr.videocall.utilities.auth.AuthUtil;
import com.rubyjr.videocall.utilities.auth.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private Properties properties;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthUtil authUtil;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthDto login(LoginDto loginDto){

        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        Assert.isNull(email, "email cannot be null");
        Assert.isNull(password, "password cannot be null");

        Optional<User> optionalUser = this.userRepository.findByEmail(email);

        Assert.ifCondition(optionalUser.isEmpty(), new UserNotFoundException("There is not user with this email address"));

        User user = optionalUser.get();
        String passwordUserDb = user.getPassword();

        Date now = new Date();
        Date expiration = new Date(now.getTime() + this.properties.getTokenExpirationTime() * 1000);

        Optional<Auth> optionalAuthToken = this.authRepository.findById(user.getId());

        Auth auth = optionalAuthToken.orElseGet(() -> {
            Auth newAuthToken = new Auth();
            newAuthToken.setCreatedAt(now);
            newAuthToken.setExpiredAt(expiration);
            newAuthToken.setToken(this.jwtUtil.generateUserToken(user.getId(), user.getEmail(), expiration));

            return this.authRepository.save(newAuthToken);
        });

        Assert.ifCondition(!encoder.matches(password, passwordUserDb), new IncorrectPasswordOfUserException("Passwords are not equals"));

        if (auth.getExpiredAt().before(now)){

            auth.setCreatedAt(now);
            auth.setExpiredAt(expiration);
            auth.setToken(this.jwtUtil.generateUserToken(user.getId(), user.getEmail(), expiration));

            auth = this.authRepository.save(auth);

        }

        return new AuthDto(
            auth.getToken(),
            auth.getExpiredAt()
        );

    }

    @Transactional
    public AuthDto signUp(SignUpDto signUpDto){

        String email = signUpDto.getEmail();
        String password = signUpDto.getPassword();
        String name = signUpDto.getName();

        Assert.isNull(email, "email cannot be null");
        Assert.isNull(password, "password cannot be null");
        Assert.isNull(name, "name cannot be null");

        Boolean userExists = this.userRepository.existsByEmail(email);

        Assert.ifCondition(userExists, new EmailAlreadyExistsException("There is an user with this email"));

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));

        user = this.userRepository.save(user);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + this.properties.getTokenExpirationTime() * 1000);

        Auth authToken = new Auth();
        authToken.setCreatedAt(now);
        authToken.setExpiredAt(expiration);
        authToken.setToken(this.jwtUtil.generateUserToken(user.getId(), user.getEmail(), expiration));

        authToken = this.authRepository.save(authToken);

        return new AuthDto(
            authToken.getToken(),
            authToken.getExpiredAt()
        );

    }

}
