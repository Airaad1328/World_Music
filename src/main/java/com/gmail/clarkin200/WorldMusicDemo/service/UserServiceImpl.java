package com.gmail.clarkin200.WorldMusicDemo.service;

import com.gmail.clarkin200.WorldMusicDemo.dto.UserDto;
import com.gmail.clarkin200.WorldMusicDemo.exception.UserEmailAlreadyExistException;
import com.gmail.clarkin200.WorldMusicDemo.exception.UserUsernameAlreadyExistException;
import com.gmail.clarkin200.WorldMusicDemo.model.user.User;
import com.gmail.clarkin200.WorldMusicDemo.model.user.UserMapper;
import com.gmail.clarkin200.WorldMusicDemo.repository.postgres.UserRepository;
import com.gmail.clarkin200.WorldMusicDemo.repository.redis.UserRedisRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserRedisRepository userRedisRepository;
    private UserMapper userMapper;

    public UserServiceImpl (@Qualifier("userRepository")UserRepository userRepository,
                            @Qualifier("userRedisRepository")UserRedisRepository userRedisRepository,
                            @Qualifier("userMapper") UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userRedisRepository = userRedisRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        Optional<UserDto> cachedUser = userRedisRepository.findById(id.toString());
        if(cachedUser.isPresent()) {
            return cachedUser;
        }
        Optional<User> findedUser = userRepository.findById(id);
        if(findedUser.isPresent()) {
            userRedisRepository.create(userMapper.entityToFindByIdDto(findedUser.get()));
        }
        return Optional.of(userMapper.entityToFindByIdDto(findedUser.get()));
    }

    @Override
    public Optional<List<UserDto>> fetchAll() {
        Optional<List<UserDto>> cachedUsers = userRedisRepository.fetchAll();
        List<UserDto> usersDto;
        if(cachedUsers.isPresent()) {
            return cachedUsers;
        }
        List<User> users = userRepository.findAll();
        usersDto = users.stream()
                .map(user -> userMapper.entityToFindByIdDto(user))
                .toList();
        return Optional.of(usersDto);
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<User> toDeleteUser = userRepository.findById(id);
        if(toDeleteUser.isPresent()) {
            userRepository.deleteById(id);
            userRedisRepository.deleteById(id.toString());
            return true;
        }
        return false;
    }

    @Override
    public Optional<UserDto> create(UserDto entity) {
        if(userRepository.existsByEmail(entity.email())) {
            throw new UserEmailAlreadyExistException(entity.email());
        }
        if(userRepository.existsByUsername(entity.username())) {
            throw new UserUsernameAlreadyExistException(entity.username());
        }
        User userToSave = userMapper.dtoToRegistrationEntity(entity);
        userRepository.save(userToSave);
        UserDto toSaveRedis = userMapper.entityToFindByIdDto(userToSave);
        System.out.println(toSaveRedis);
        userRedisRepository.create(toSaveRedis);
        return Optional.of(entity);
    }

    @Override
    public Optional<UserDto> update(UserDto entity) {
        return Optional.empty();
    }
}
