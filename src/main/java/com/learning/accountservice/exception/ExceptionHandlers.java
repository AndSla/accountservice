package com.learning.accountservice.exception;

import com.learning.accountservice.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlers {

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

    @ExceptionHandler(SamePasswordException.class)
    public ResponseEntity<ErrorResponse> handleSamePasswordException(
            HttpServletRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String message = "The passwords must be different!";

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(httpStatus.value());
        errorResponse.setError(httpStatus.getReasonPhrase());
        errorResponse.setMessage(message);
        errorResponse.setPath(request.getServletPath());

        return ResponseEntity
                .status(httpStatus)
                .body(errorResponse);
    }

    @ExceptionHandler(BreachedPasswordException.class)
    public ResponseEntity<ErrorResponse> handleBreachedPasswordException(
            HttpServletRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String message = "The password is in the hacker's database!";

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