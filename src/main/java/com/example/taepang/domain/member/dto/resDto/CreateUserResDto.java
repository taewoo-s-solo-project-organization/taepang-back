package com.example.taepang.domain.member.dto.resDto;

import com.example.taepang.domain.member.entity.User;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CreateUserResDto extends UserResDto {

	private final Long id;

	public static CreateUserResDto from(User user) {
		return CreateUserResDto.builder()
			.id(user.getId())
			.username(user.getUsername())
			.phoneNumber(user.getPhoneNumber())
			.email(user.getEmail())
			.createdAt(user.getCreatedAt())
			.build();
	}
}
