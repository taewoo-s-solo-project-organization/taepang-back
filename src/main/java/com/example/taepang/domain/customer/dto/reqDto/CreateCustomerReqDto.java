package com.example.taepang.domain.customer.dto.reqDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCustomerReqDto {

	private String username;
	private String phoneNumber;
	private String email;

}
