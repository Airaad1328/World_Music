package com.gmail.clarkin200.WorldMusicDemo.exception;

public class UserUsernameAlreadyExistException extends RuntimeException {
    public UserUsernameAlreadyExistException(String username) {
        super("User with username " + username + " already exist");
    }
}
