package com.gmail.clarkin200.WorldMusicDemo.repository.redis;

import com.gmail.clarkin200.WorldMusicDemo.dto.UserDto;
import com.gmail.clarkin200.WorldMusicDemo.model.HubSession;
import com.gmail.clarkin200.WorldMusicDemo.model.user.User;
import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("userRedisRepository")
public class UserRedisImpl implements UserRedisRepository {

    private RedisTemplate<String,Object> redisTemplate;
    private HashOperations<String,String, UserDto> hashOperations;
    public static final String KEY = "USER";


    public UserRedisImpl (RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Optional<List<UserDto>> fetchAll() {
        return Optional.ofNullable(hashOperations.values(KEY));
    }

    @Override
    public Optional<UserDto> findById(String id) {
        return Optional.ofNullable(hashOperations.get(KEY,id));
    }

    @Override
    public boolean deleteById(String id) {
        return hashOperations.delete(KEY, id) >= 1L;
    }

    @Override
    public Optional<UserDto> create(UserDto user) {
        hashOperations.put(KEY,user.id().toString(),user);
        return Optional.ofNullable(hashOperations.get(KEY,user.id().toString()));
    }
}
