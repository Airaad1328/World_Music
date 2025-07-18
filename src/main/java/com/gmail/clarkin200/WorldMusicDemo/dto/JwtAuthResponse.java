package com.gmail.clarkin200.WorldMusicDemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record JwtAuthResponse(String jwt) {
}
