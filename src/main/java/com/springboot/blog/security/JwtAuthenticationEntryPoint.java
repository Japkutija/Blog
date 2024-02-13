package com.springboot.blog.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
/**
 * This class is responsible for handling the initial authentication process.
 * It implements the AuthenticationEntryPoint interface which is an entry point of the application.
 * This is where the authentication process commences when there is a request to a secured HTTP resource but the user is not authenticated.
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * This method is invoked when a user tries to access a secured HTTP resource without being authenticated.
     * It sends an HTTP 401 Unauthorized error code and the exception message to the client.
     *
     * @param request the HttpServletRequest which represents the client's request
     * @param response the HttpServletResponse which represents the server's response
     * @param authException the AuthenticationException that caused the invocation of this method
     * @throws IOException if an input or output error is detected when the servlet handles the request
     * @throws ServletException if the request for the POST could not be handled
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
