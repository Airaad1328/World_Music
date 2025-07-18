package com.gmail.clarkin200.WorldMusicDemo.service.security;

import com.gmail.clarkin200.WorldMusicDemo.exception.UserEmailNotExist;
import com.gmail.clarkin200.WorldMusicDemo.model.user.User;
import com.gmail.clarkin200.WorldMusicDemo.model.user.UserCredential;
import com.gmail.clarkin200.WorldMusicDemo.repository.postgres.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public MyUserDetailsService (@Qualifier("userRepository")UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if(!userRepository.existsByEmail(email)) {
            throw new UserEmailNotExist(email);
        }
        User findUser = userRepository.findByEmail(email).get();
        findUser.getRoles().size();
        return new UserCredential(findUser);
    }
}
