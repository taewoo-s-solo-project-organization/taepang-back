package com.example.taepang.domain.member.dto.resDto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class UserResDto {

	private final String username;
	private final String phoneNumber;
	private final String email;
	private final LocalDateTime createdAt;

}
