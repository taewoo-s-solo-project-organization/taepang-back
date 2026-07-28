package com.example.taepang.domain.auth.service;

import com.example.taepang.domain.auth.dto.LoginReqDto;
import com.example.taepang.domain.auth.dto.LoginResDto;
import com.example.taepang.domain.auth.dto.SignupReqDto;

public interface AuthService {

	// 1. 회원 가입 메서드 (void : 따로 로그인, SignupResDto : 회원 가입 후 바로 로그인)
	void signup(SignupReqDto signupReqDto);

	// 2. 로그인 메서드
	LoginResDto login(LoginReqDto loginReqDto);
}
