package com.example.taepang.domain.customer.dto.resDto;

import com.example.taepang.domain.customer.entity.Customer;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CreateCustomerResDto extends CustomerResDto {

	private final Long id;

	public static CreateCustomerResDto from(Customer customer) {
		return CreateCustomerResDto.builder()
			.id(customer.getId())
			.username(customer.getUsername())
			.phoneNumber(customer.getPhoneNumber())
			.email(customer.getEmail())
			.createdAt(customer.getCreatedAt())
			.build();
	}
}
