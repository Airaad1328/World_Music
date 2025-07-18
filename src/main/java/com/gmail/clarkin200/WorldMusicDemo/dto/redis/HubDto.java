package com.gmail.clarkin200.WorldMusicDemo.dto.redis;

import java.util.List;

public record HubDto(String id, List<UserHubDtoResponse> userList) {
}
