package com.gmail.clarkin200.WorldMusicDemo.service.security;

import com.gmail.clarkin200.WorldMusicDemo.dto.JwtAuthResponse;
import com.gmail.clarkin200.WorldMusicDemo.dto.SignInRequest;
import com.gmail.clarkin200.WorldMusicDemo.dto.SignUpRequest;
import com.gmail.clarkin200.WorldMusicDemo.dto.UserDto;
import com.gmail.clarkin200.WorldMusicDemo.model.user.UserCredential;
import com.gmail.clarkin200.WorldMusicDemo.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("authService")
public class AuthService {
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JWTService jwtService;
    private MyUserDetailsService myUserDetailsService;

    public AuthService (PasswordEncoder passwordEncoder,
                        AuthenticationManager authenticationManager,
                        MyUserDetailsService myUserDetailsService,
                        @Qualifier("userService") UserService userService,
                        @Qualifier("JWTService") JWTService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    public JwtAuthResponse signUp (SignUpRequest request) {
        UserDto userDto = UserDto.of(request);
        userService.create(userDto);
        UserDetails userCredential = myUserDetailsService.loadUserByUsername(request.email());
        String jwt = jwtService.generateToken(userCredential);
        return new JwtAuthResponse(jwt);
    }

    public JwtAuthResponse signIn (SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        ));
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.email());
        String jwt = jwtService.generateToken(userDetails);
        return new JwtAuthResponse(jwt);

    }
}
