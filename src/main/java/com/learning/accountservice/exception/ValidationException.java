package com.learning.accountservice.exception;

import com.learning.accountservice.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ValidationException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e,
            HttpServletRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String message = "";

        if (e.getFieldError() != null) {
            message = e.getFieldError().getDefaultMessage();
        }

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(httpStatus.value());
        errorResponse.setError(httpStatus.getReasonPhrase());
        errorResponse.setMessage(message);
        errorResponse.setPath(request.getServletPath());

        return ResponseEntity
                .status(httpStatus)
                .body(errorResponse);
    }

}