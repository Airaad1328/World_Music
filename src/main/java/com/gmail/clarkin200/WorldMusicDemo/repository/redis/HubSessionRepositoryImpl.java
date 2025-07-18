package com.gmail.clarkin200.WorldMusicDemo.repository.redis;

import com.gmail.clarkin200.WorldMusicDemo.dto.redis.HubDto;
import com.gmail.clarkin200.WorldMusicDemo.dto.redis.UserHubDtoResponse;
import com.gmail.clarkin200.WorldMusicDemo.model.HubSession;
import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("hubSessionRepository")
public class HubSessionRepositoryImpl implements HubSessionRepository {

    private RedisTemplate<String,Object> redisTemplate;
    private HashOperations<String,String, HubSession> hashOperations;
    public static final String KEY = "HUBSESSION";

    public HubSessionRepositoryImpl (RedisTemplate<String,Object> redisTemplate ) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }


    @Override
    public Optional<List<HubSession>> fetchAll() {
        return Optional.ofNullable(hashOperations.values(KEY));
    }

    @Override
    public Optional<HubSession> findById(String id) {
        return  Optional.ofNullable(hashOperations.get(KEY,id));
    }

    @Override
    public boolean deleteById(String id) {
        return hashOperations.delete(id) > 1L;
    }

    @Override
    public Optional<HubSession> create(HubSession hub) {
        hashOperations.put(KEY,hub.getHubSessionId().toString(),hub);
        return Optional.ofNullable(hashOperations.get(KEY,hub.getHubSessionId().toString()));
    }
}
