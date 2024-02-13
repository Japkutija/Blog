package com.springboot.blog.security;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecret;
    @Value("${app-jwtExpirationMs}")
    private long jwtExpirationDate;

    // Generate the token
    public String generateToken(Authentication authentication) {
        return null;
    }

}
