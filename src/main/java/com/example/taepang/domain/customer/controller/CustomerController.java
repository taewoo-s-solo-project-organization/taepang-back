package com.example.taepang.domain.customer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taepang.domain.auth.annotation.CurrentMember;
import com.example.taepang.domain.auth.dto.VerifiedMember;
import com.example.taepang.domain.customer.dto.reqDto.ModifyCustomerReqDto;
import com.example.taepang.domain.customer.dto.resDto.FindCustomerResDto;
import com.example.taepang.domain.customer.dto.resDto.UpdateCustomerResDto;
import com.example.taepang.domain.customer.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

	// 해당 컨트롤러의 서비스들은 인증된 유저만 허가 된다.

	private final CustomerService customerService;

	// 손님 자신의 객체 조회 (일단은 id 값 기준으로)
	@GetMapping("/info")
	public ResponseEntity<FindCustomerResDto> getCustomerInfo(@CurrentMember VerifiedMember member) {
		return ResponseEntity.status(HttpStatus.OK).body(customerService.findUser(member.getId()));
	}

	// 멤버 객체 수정 (일부 수정, id 값 기준)
	@PatchMapping("/modify")
	public ResponseEntity<UpdateCustomerResDto> modifyCustomer(@CurrentMember VerifiedMember member,
		@RequestBody ModifyCustomerReqDto reqDto) {
		return ResponseEntity.status(HttpStatus.OK).body(customerService.modifyUser(member.getId(), reqDto));
	}

	// 회원 탈퇴
	@DeleteMapping("/remove")
	public ResponseEntity<Void> removeCustomer(@CurrentMember VerifiedMember member) {
		customerService.removeUser(member.getId());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
