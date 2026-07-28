package com.example.taepang.domain.auth.dto;

import lombok.Getter;

@Getter
public class SignupReqDto {
	private String email; // 추후 이메일 인증 구현 예정 
	private String password;
	private String phoneNumber; // 휴대폰 인증은 과금 요소라 고민 필요
	private String username;
}
