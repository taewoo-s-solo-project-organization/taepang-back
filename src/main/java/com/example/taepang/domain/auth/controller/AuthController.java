package com.example.taepang.domain.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taepang.domain.auth.dto.LoginReqDto;
import com.example.taepang.domain.auth.dto.LoginResDto;
import com.example.taepang.domain.auth.dto.SignupReqDto;
import com.example.taepang.domain.auth.service.CustomerAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final CustomerAuthService customerAuthService;

	@PostMapping("/signup")
	public ResponseEntity<Void> SignUp(@RequestBody SignupReqDto reqDto) {
		customerAuthService.signup(reqDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResDto> Login(@RequestBody LoginReqDto reqDto) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(customerAuthService.login(reqDto));
	}
}
