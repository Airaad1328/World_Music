package com.gmail.clarkin200.WorldMusicDemo.exception;

public class HubSessionNotExistException extends RuntimeException {
    public HubSessionNotExistException(String hubSessionId) {
        super("HubSession with id " + hubSessionId + " not exist");
    }
}
