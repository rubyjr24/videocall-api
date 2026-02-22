package com.rubyjr.videocall.utilities.auth;

import com.rubyjr.videocall.utilities.Assert;
import com.rubyjr.videocall.utilities.Properties;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    public static final String USER_ID_FIELD = "userId";
    public static final String EMAIL_FIELD = "email";

    private final Key key;

    public JwtUtil(Properties properties) {
        this.key = Keys.hmacShaKeyFor(properties.getTokenSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    public String generateUserToken(Long userId, String email, Date expiration) {

        Assert.isNull(userId, "userId cannot be null");
        Assert.isNull(email, "email cannot be null");
        Assert.isNull(expiration, "expiration cannot be null");

        try{
            return Jwts.builder()
                    .claim(USER_ID_FIELD, userId)
                    .claim(EMAIL_FIELD, email)
                    .setIssuedAt(new Date())
                    .setExpiration(expiration)
                    .signWith(key)
                    .compact();
        } catch (JwtException e) {
            return null;
        }

    }

    public String getEmail(String token) {
        try{
            return (String) Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get(EMAIL_FIELD);
        }catch (JwtException e){
            return null;
        }
    }

    public Long getUserId(String token) {
        try{
            return Long.valueOf(Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get(USER_ID_FIELD).toString());
        } catch (JwtException e) {
            return null;
        }
    }

    public boolean isTokenValid(String token) {
        try {
            getUserId(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
