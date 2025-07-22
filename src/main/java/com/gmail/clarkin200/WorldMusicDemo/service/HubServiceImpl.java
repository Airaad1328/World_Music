package com.gmail.clarkin200.WorldMusicDemo.service;

import com.gmail.clarkin200.WorldMusicDemo.dto.UserDto;
import com.gmail.clarkin200.WorldMusicDemo.exception.HubSessionNotExistException;
import com.gmail.clarkin200.WorldMusicDemo.model.HubSession;
import com.gmail.clarkin200.WorldMusicDemo.model.user.UserCredential;
import com.gmail.clarkin200.WorldMusicDemo.repository.postgres.UserRepository;
import com.gmail.clarkin200.WorldMusicDemo.repository.redis.HubSessionRepository;
import com.gmail.clarkin200.WorldMusicDemo.repository.redis.UserRedisRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("hubService")
public class HubServiceImpl implements HubService{

    private HubSessionRepository hubSessionRepository;
    private UserService userService;
    private UserRedisRepository userRedisRepository;

    public HubServiceImpl (@Qualifier("hubSessionRepository")HubSessionRepository hubSessionRepository,
                           @Qualifier("userService")UserService userService,
                           @Qualifier("userRedisRepository")UserRedisRepository userRedisRepository) {
        this.hubSessionRepository = hubSessionRepository;
        this.userService = userService;
        this.userRedisRepository = userRedisRepository;
    }

    @Override
    public Optional<HubSession> findById(Long id) {
        return hubSessionRepository.findById(id.toString());
    }

    @Override
    public Optional<List<HubSession>> fetchAll() {
        return hubSessionRepository.fetchAll();
    }

    @Override
    public boolean deleteById(Long id) {
        return hubSessionRepository.deleteById(id.toString());
    }

    @Override
    public Optional<HubSession> create(HubSession entity) {
        UserCredential user = (UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user);
        entity.setHubSessionId(user.getUserId());
        entity.getUsers().put(user.getUserId(),userService.findById(user.getUserId()).get());
        return hubSessionRepository.create(entity);
    }

    @Override
    public Optional<HubSession> update(HubSession entity) {
        return create(entity);
    }

    @Override
    public HubSession addUserToHubById(Long userId, Long hubId) {
        HubSession hubSession = hubSessionRepository.findById(hubId.toString()).get();
        if(userService.findById(userId).isPresent()) {
            UserDto userToAdd = userService.findById(userId).get();
            hubSession.getUsers().put(userId,userToAdd);
        }
        update(hubSession);
        hubSession = hubSessionRepository.findById(hubId.toString()).get();
        return hubSession;
    }

    @Override
    public HubSession deleteUserFromHubById(Long userId, Long hubId) {
        HubSession hubSession = hubSessionRepository.findById(hubId.toString()).get();
        if(userService.findById(userId).isPresent()) {
            UserDto userToDelete = userService.findById(userId).get();
            hubSession.getUsers().remove(userToDelete);
            update(hubSession);
        }
        hubSession = hubSessionRepository.findById(hubId.toString()).get();
        return hubSession;

    }

    @Override
    public HubSession joinHubSession(Long hubId) {
        Optional<HubSession> hubSession = hubSessionRepository.findById(hubId.toString());
        if(hubSession.isEmpty()) {
            throw new HubSessionNotExistException(hubId.toString());
        }
        UserCredential user = (UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Add User HubSession id to Redis
        UserDto oldUser = userService.findById(user.getUserId()).get();
        UserDto newUser = new UserDto(oldUser.id(),oldUser.username(),oldUser.email(), oldUser.password(),hubSession.get().getHubSessionId());
        userRedisRepository.create(newUser);
        return addUserToHubById(user.getUserId(),hubId);
    }

    @Override
    public boolean leaveHubSession (Long hubId) {
        Optional<HubSession> hubSession = hubSessionRepository.findById(hubId.toString());
        if(hubSession.isEmpty()) {
            throw new HubSessionNotExistException(hubId.toString());
        }
        UserCredential user = (UserCredential) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Delete User HubSession id to Redis
        UserDto oldUser = userService.findById(user.getUserId()).get();
        UserDto newUser = new UserDto(oldUser.id(),oldUser.username(),oldUser.email(), oldUser.password(),null);
        userRedisRepository.create(newUser);
        return deleteUserFromHubById(user.getUserId(),hubId) != null;

    }
}
