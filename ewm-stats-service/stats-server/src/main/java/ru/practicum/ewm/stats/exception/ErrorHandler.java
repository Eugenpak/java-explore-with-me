package ru.practicum.ewm.stats.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        return Map.of("status", HttpStatus.BAD_REQUEST.name(),
                "error", e.getMessage(),
                "type error","MethodArgumentNotValidException");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    public Map<String, String> handleConstraintViolation(ConstraintViolationException e) {
        return Map.of("status", HttpStatus.BAD_REQUEST.name(),
                "error", e.getMessage(),
                "type error","ConstraintViolationException");
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    public Map<String, String> handleValidation(ValidationException e) {
        return Map.of("status", HttpStatus.BAD_REQUEST.name(),
                "error", e.getMessage(),
                "type error","ValidationException");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //500
    public Map<String, String> handleInternalServerError(Exception e) {
        return Map.of("status", HttpStatus.INTERNAL_SERVER_ERROR.name(),
                "error", e.getMessage(),
                "type error","Exception");
    }

}
