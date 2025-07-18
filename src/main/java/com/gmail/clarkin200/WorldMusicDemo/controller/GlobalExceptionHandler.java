package com.gmail.clarkin200.WorldMusicDemo.controller;

import com.gmail.clarkin200.WorldMusicDemo.exception.UserEmailAlreadyExistException;
import com.gmail.clarkin200.WorldMusicDemo.exception.UserEmailNotExist;
import com.gmail.clarkin200.WorldMusicDemo.exception.UserUsernameAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserEmailAlreadyExistException.class)
    public ResponseEntity<Map<String,String>> handleUserAlreadyExist (UserEmailAlreadyExistException exception) {
        Map <String,String> error = new HashMap<>();
        error.put("error","UserEmailAlreadyExists");
        error.put("message",exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UserUsernameAlreadyExistException.class)
    public ResponseEntity<Map<String,String>> handleUserUsernameAlreadyExist (UserUsernameAlreadyExistException exception) {
        Map <String,String> error = new HashMap<>();
        error.put("error","UserUsernameAlreadyExist");
        error.put("message",exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UserEmailNotExist.class)
    public ResponseEntity<Map<String,String>> handleUserEmailNotExist (UserEmailNotExist exception) {
        Map<String,String> error = new HashMap<>();
        error.put("error","UserEmailNotExist");
        error.put("message",exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
