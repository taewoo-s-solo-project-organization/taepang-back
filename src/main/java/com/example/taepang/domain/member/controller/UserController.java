package com.example.taepang.domain.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taepang.domain.member.dto.reqDto.CreateUserReqDto;
import com.example.taepang.domain.member.dto.resDto.CreateUserResDto;
import com.example.taepang.domain.member.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	// 멤버 객체 생성 (일반 사용자)
	@PostMapping("/join")
	public ResponseEntity<CreateUserResDto> createUser(@RequestBody CreateUserReqDto reqDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(reqDto));
	}
}
