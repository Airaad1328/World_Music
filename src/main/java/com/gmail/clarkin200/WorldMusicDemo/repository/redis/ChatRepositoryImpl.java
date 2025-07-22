package com.gmail.clarkin200.WorldMusicDemo.repository.redis;

import com.gmail.clarkin200.WorldMusicDemo.model.chat.Message;

import java.util.List;
import java.util.Optional;

public class ChatRepositoryImpl implements ChatRepository{
    private String KEY = "CHAT";

    @Override
    public Optional<List<Message>> fetchAll() {
        return Optional.empty();
    }

    @Override
    public Optional<Message> findById(String id) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }

    @Override
    public Optional<Message> create(Message entity) {
        return Optional.empty();
    }
}
