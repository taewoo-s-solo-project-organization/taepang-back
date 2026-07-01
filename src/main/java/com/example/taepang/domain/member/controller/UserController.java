package com.example.taepang.domain.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taepang.domain.member.dto.reqDto.CreateUserReqDto;
import com.example.taepang.domain.member.dto.reqDto.ModifyUserReqDto;
import com.example.taepang.domain.member.dto.resDto.CreateUserResDto;
import com.example.taepang.domain.member.dto.resDto.FindUserResDto;
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

	// 멤버 객체 조회 (일단은 id 값 기준으로)
	@GetMapping("/info/{id}")
	public ResponseEntity<FindUserResDto> getUserInfo(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.findUser(id));
	}

	// 멤버 객체 수정 (일부 수정, id 값 기준)
	@PatchMapping("/modify/{id}")
	public ResponseEntity<FindUserResDto> modifyUser(@PathVariable Long id, @RequestBody ModifyUserReqDto reqDto) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.modifyUser(id, reqDto));
	}

	@DeleteMapping("/remove/{id}")
	public ResponseEntity<Void> removeUser(@PathVariable Long id) {
		userService.removeUser(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
