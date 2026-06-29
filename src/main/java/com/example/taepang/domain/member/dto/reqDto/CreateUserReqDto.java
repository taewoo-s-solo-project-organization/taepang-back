package com.example.taepang.domain.member.dto.reqDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserReqDto {
	
	private String username;
	private String phoneNumber;
	private String email;

}
