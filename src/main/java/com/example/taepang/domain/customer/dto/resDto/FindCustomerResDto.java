package com.example.taepang.domain.customer.dto.resDto;

import java.time.LocalDateTime;

import com.example.taepang.domain.customer.entity.Customer;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class FindCustomerResDto extends CustomerResDto {

	private final LocalDateTime updatedAt;

	public static FindCustomerResDto from(Customer customer) {
		return FindCustomerResDto.builder()
			.username(customer.getUsername())
			.phoneNumber(customer.getPhoneNumber())
			.email(customer.getEmail())
			.createdAt(customer.getCreatedAt())
			.updatedAt(customer.getUpdatedAt())
			.build();
	}
}
