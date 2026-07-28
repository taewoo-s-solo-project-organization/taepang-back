package com.example.taepang.domain.customer.dto.resDto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class CustomerResDto {

	private final String username;
	private final String phoneNumber;
	private final String email;
	private final LocalDateTime createdAt;

}
