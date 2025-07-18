package com.gmail.clarkin200.WorldMusicDemo.controller;

import com.gmail.clarkin200.WorldMusicDemo.service.UserService;
import com.gmail.clarkin200.WorldMusicDemo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    public UserController (@Qualifier("userService") UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser (@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Delete is ok");
    }
}
