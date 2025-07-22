package com.gmail.clarkin200.WorldMusicDemo.model.user;

import com.gmail.clarkin200.WorldMusicDemo.dto.UserDto;
import com.gmail.clarkin200.WorldMusicDemo.model.Role;
import com.gmail.clarkin200.WorldMusicDemo.repository.postgres.RoleRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("userMapper")
public class UserMapper {

    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserMapper(@Qualifier("roleRepository")RoleRepository roleRepository,
                      PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto entityToFindByIdDto (User user) {
        return new UserDto(user.getId(),user.getUsername(),user.getEmail(),
                user.getPassword(), user.getHubSessionId());

    }

    public User dtoToRegistrationEntity (UserDto dto) {
        User newUser = new User();
        newUser.setUsername(dto.username());
        newUser.setEmail(dto.email());
        newUser.setPassword(passwordEncoder.encode(dto.password()));
        newUser.getRoles().add(roleRepository.findById(1L).get());
        return newUser;
    }


    public User dtoToUpdateUser (UserDto dto ,User user) {

        if(dto.username() != null){
            user.setUsername(dto.username());
        }
        return user;
    }

}
