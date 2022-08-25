package com.learning.accountservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.learning.accountservice.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        String message;
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        if (authException instanceof LockedException) {         // If user is locked - change response message
            message = "User account is locked";
        } else {
            message = httpStatus.getReasonPhrase();
        }

            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setStatus(httpStatus.value());
            errorResponse.setError(httpStatus.getReasonPhrase());
            errorResponse.setMessage(message);
            errorResponse.setPath(request.getServletPath());

            ObjectMapper objectMapper = new ObjectMapper();                 // Class for serialize/deserialize - JSON
            objectMapper.registerModule(new JavaTimeModule());              // Module for LocalDateTime support - timestamp from errorResponse
            objectMapper.setDateFormat(new StdDateFormat());                // StdDateFormat = yyyy-MM-ddTHH:mm:ss.SSS
            String body = objectMapper.writeValueAsString(errorResponse);   // Writes to String serialized object (errorResponse)

            response.setStatus(httpStatus.value());
            response.setContentType("application/json");
            response.getWriter().write(body);

    }

}
