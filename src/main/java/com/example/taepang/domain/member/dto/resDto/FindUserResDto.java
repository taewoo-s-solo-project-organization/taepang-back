package com.example.taepang.domain.member.dto.resDto;

import java.time.LocalDateTime;

import com.example.taepang.domain.member.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindUserResDto { // TODO :  CreateUserResDto 와 매우 비슷하기 때문에 수정 필요
	private final String username;
	private final String phoneNumber;
	private final String email;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;

	public static FindUserResDto from(User user) {
		return FindUserResDto.builder()
			.username(user.getUsername())
			.phoneNumber(user.getPhoneNumber())
			.email(user.getEmail())
			.createdAt(user.getCreatedAt())
			.updatedAt(user.getUpdatedAt())
			.build();
	}
}
