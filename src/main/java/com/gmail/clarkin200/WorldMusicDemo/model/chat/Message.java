package com.gmail.clarkin200.WorldMusicDemo.model.chat;

import java.time.LocalDateTime;

public class Message {
    private Long ChatId;
    private Long MessageId;
    private String sendBy;
    private LocalDateTime createdAt;
    private MessageStatus messageStatus;
    private String content;

}
