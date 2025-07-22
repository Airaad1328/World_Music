package com.gmail.clarkin200.WorldMusicDemo.model.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Chat {
    private Long hubId;
    private List<Message> messages;

    public Chat(Long hubId, List<Message> messages) {
        this.hubId = hubId;
        this.messages = new ArrayList<>();
    }

    public Long getId() {
        return hubId;
    }

    public void setId(Long hubId) {
        this.hubId = hubId;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return Objects.equals(hubId, chat.hubId) && Objects.equals(messages, chat.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hubId, messages);
    }
}
