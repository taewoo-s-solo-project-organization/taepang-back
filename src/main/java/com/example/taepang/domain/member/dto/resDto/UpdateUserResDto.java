package com.example.taepang.domain.member.dto.resDto;

import java.time.LocalDateTime;

import com.example.taepang.domain.member.entity.User;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class UpdateUserResDto extends UserResDto {

	private final LocalDateTime updatedAt;

	public static UpdateUserResDto from(User user) {
		return UpdateUserResDto.builder()
			.username(user.getUsername())
			.phoneNumber(user.getPhoneNumber())
			.email(user.getEmail())
			.createdAt(user.getCreatedAt())
			.updatedAt(user.getUpdatedAt())
			.build();
	}
}
