package com.learning.accountservice.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.learning.accountservice.model.enums.EventMsg;
import com.learning.accountservice.model.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException exc) throws IOException {

        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        String message = "Access Denied!";

        logger.info(EventMsg.ACCESS_DENIED.getMessage());

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
