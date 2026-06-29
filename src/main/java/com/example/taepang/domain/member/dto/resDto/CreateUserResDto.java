package com.example.taepang.domain.member.dto.resDto;

import java.time.LocalDateTime;

import com.example.taepang.domain.member.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserResDto {
	private final String username;
	private final String phoneNumber;
	private final String email;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;

	public static CreateUserResDto from(User user) {
		return CreateUserResDto.builder()
			.username(user.getUsername())
			.phoneNumber(user.getPhoneNumber())
			.email(user.getEmail())
			.createdAt(user.getCreatedAt())
			.updatedAt(user.getUpdatedAt())
			.build();
	}
}
