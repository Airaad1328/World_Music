package com.gmail.clarkin200.WorldMusicDemo.controller;

import com.gmail.clarkin200.WorldMusicDemo.dto.UserDto;
import com.gmail.clarkin200.WorldMusicDemo.model.user.User;
import com.gmail.clarkin200.WorldMusicDemo.repository.redis.UserRedisRepository;
import com.gmail.clarkin200.WorldMusicDemo.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestController {

    private UserService userService;

    public TestController (@Qualifier("userService")UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> findById (@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id).get());
    }

    /*@GetMapping("/get")
    public ResponseEntity<List<UserDto>> getSession () {
        List<User> userList =  repository.fetchAll();
        List<UserDto> dtoList = userList.stream().map(user ->  { return new UserDto(user.getId(),user.getUsername(),user.getEmail(),
                user.getPassword(),user.getHubSessionId());})
                .toList();
        return ResponseEntity.ok()
                .body(dtoList);
    }*/
}
