package com.gmail.clarkin200.WorldMusicDemo.model;

import com.gmail.clarkin200.WorldMusicDemo.dto.UserDto;

import java.util.*;

public class HubSession {

    private Long hubSessionId;
    private Map<Long,UserDto> users;

    public HubSession(Long hubSessionId, List<Long> userIds) {
        this.hubSessionId = hubSessionId;
        this.users = new HashMap();
    }

    public HubSession () {
        this.users = new HashMap();
    }

    public Long getHubSessionId() {
        return hubSessionId;
    }

    public void setHubSessionId(Long hubSessionId) {
        this.hubSessionId = hubSessionId;
    }

    public Map<Long,UserDto> getUsers() {
        return users;
    }

    public void setUsers(Map<Long,UserDto> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HubSession session = (HubSession) o;
        return Objects.equals(hubSessionId, session.hubSessionId) && Objects.equals(users, session.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hubSessionId, users);
    }

    @Override
    public String toString() {
        return "HubSession{" +
                "hubSessionId=" + hubSessionId +
                ", users=" + users +
                '}';
    }
}
