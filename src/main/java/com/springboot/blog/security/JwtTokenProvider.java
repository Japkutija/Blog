package com.springboot.blog.security;

import com.springboot.blog.exception.BlogAPIException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecret;
    @Value("${app-jwtExpirationMs}")
    private long jwtExpirationDate;

    /**
     * This method is used to generate a JWT token.
     *
     * @param authentication The authentication object from which the username is extracted.
     * @return String Returns a JWT token.
     */
    public String generateToken(Authentication authentication) {
        // Extract the username from the authentication object
        String username = authentication.getName();

        // Get the current date
        var currentDate = new Date();
        // Calculate the expiration date based on the current date and the jwtExpirationDate
        var expirationDate = new Date(currentDate.getTime() + jwtExpirationDate);

        // Build the JWT token with the username as subject, the current date as issued date,
        // the calculated expiration date, and sign it with the secret key
        var token = Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(key())
                .compact();

        // Return the generated token
        return token;
    }

    /**
     * This method is used to generate a SecretKey for JWT token signing.
     *
     * @return Key Returns a SecretKey generated from the jwtSecret.
     */
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * This method is used to validate the JWT token.
     *
     * @param token The JWT token to be validated.
     * @return boolean Returns true if the token is valid, otherwise throws an exception.
     * @throws BlogAPIException if the token is malformed, expired, unsupported or if the JWT claims string is null or empty.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) key())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException exception) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException exception) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException exception) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException exception) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is null empty");
        }
    }
}
