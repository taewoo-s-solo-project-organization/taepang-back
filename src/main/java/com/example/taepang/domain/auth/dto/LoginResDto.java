package com.example.taepang.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResDto {
	String accessToken;
}
