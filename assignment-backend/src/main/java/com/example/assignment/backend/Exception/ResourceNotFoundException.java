package com.example.assignment.backend.Exception;


import org.springframework.web.bind.annotation.ControllerAdvice;


public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
