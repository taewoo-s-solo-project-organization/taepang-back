package com.example.taepang.domain.auth.dto;

import lombok.Getter;

@Getter
public class SignupReqDto {
	private String email;
	private String password;
	private String phoneNumber;
	private String username;
}
