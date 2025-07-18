package com.gmail.clarkin200.WorldMusicDemo.exception;

public class UserEmailNotExist extends RuntimeException {
    public UserEmailNotExist(String email) {
        super("User with email " + email + " doesn't exist");
    }
}
