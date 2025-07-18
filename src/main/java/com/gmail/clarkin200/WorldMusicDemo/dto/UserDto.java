package com.gmail.clarkin200.WorldMusicDemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDto(Long id, String username, String email,
                      String password, Long hubSessionId) {
   public static UserDto of (SignUpRequest request) {
       return new UserDto(null,request.username(),request.email(),
               request.password(),null);
   }
}
