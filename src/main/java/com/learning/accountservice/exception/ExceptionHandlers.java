package com.learning.accountservice.exception;

import com.learning.accountservice.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e,
            HttpServletRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String message;

        message = e.getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(httpStatus.value());
        errorResponse.setError(httpStatus.getReasonPhrase());
        errorResponse.setMessage(message);
        errorResponse.setPath(request.getServletPath());

        return ResponseEntity
                .status(httpStatus)
                .body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException e,
            HttpServletRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String message;

        message = e.getConstraintViolations().stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .collect(Collectors.joining(", "));

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

    @ExceptionHandler(DuplicatePeriodException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatePeriodException(
            HttpServletRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String message = "Error!";

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(httpStatus.value());
        errorResponse.setError(httpStatus.getReasonPhrase());
        errorResponse.setMessage(message);
        errorResponse.setPath(request.getServletPath());

        return ResponseEntity
                .status(httpStatus)
                .body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(
            HttpServletRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String message = "User not found!";

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