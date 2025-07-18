package com.gmail.clarkin200.WorldMusicDemo.controller;

import com.gmail.clarkin200.WorldMusicDemo.dto.JwtAuthResponse;
import com.gmail.clarkin200.WorldMusicDemo.dto.SignInRequest;
import com.gmail.clarkin200.WorldMusicDemo.dto.SignUpRequest;
import com.gmail.clarkin200.WorldMusicDemo.service.security.AuthService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private AuthService authService;

    public AuthController (@Qualifier("authService") AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> signIn (@RequestBody SignInRequest request) {
        JwtAuthResponse response = authService.signIn(request);
        System.out.println(response);
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthResponse> signUp (@RequestBody SignUpRequest request) {
        JwtAuthResponse response = authService.signUp(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
