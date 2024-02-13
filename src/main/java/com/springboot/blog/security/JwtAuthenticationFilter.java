package com.springboot.blog.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    /**
     * This method is part of the JwtAuthenticationFilter class and is responsible for filtering incoming HTTP requests.
     * It extracts the JWT token from the request, validates it, and sets the authentication in the SecurityContext if the token is valid.
     *
     * @param request     the HttpServletRequest which represents the client's request
     * @param response    the HttpServletResponse which represents the server's response
     * @param filterChain the FilterChain which is used to invoke the next filter in the chain
     * @throws ServletException if the request for the POST could not be handled
     * @throws IOException      if an input or output error is detected when the servlet handles the request
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // get JWT token from http request
        String token = getTokenFromRequest(request);

        // validate the token
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            // get the username from the token
            String username = jwtTokenProvider.getUsernameFromToken(token);

            // get the user details from the userDetailsService / database
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // create an authentication token using the user details
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            // set the details of the authentication token
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Logs in the user for the current session
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // continue with the next filter in the chain
        filterChain.doFilter(request, response);
    }

    /**
     * This method is used to extract the JWT token from the HTTP request.
     * The token is expected to be in the Authorization header and should start with "Bearer ".
     *
     * @param request the HttpServletRequest from which the JWT token is to be extracted
     * @return the JWT token if it exists and is valid, otherwise returns the Authorization header as is
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        // Get the value of the Authorization header
        String bearerToken = request.getHeader("Authorization");

        // If the value is not null and starts with "Bearer "
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            // Return the token starting from the 7th character, which is the token itself
            return bearerToken.substring(7);
        }
        // If the token is not present or not valid, return the Authorization header as is
        return bearerToken;
    }
}
