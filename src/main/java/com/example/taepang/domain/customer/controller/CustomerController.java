package com.example.taepang.domain.customer.controller;

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

import com.example.taepang.domain.customer.dto.reqDto.CreateCustomerReqDto;
import com.example.taepang.domain.customer.dto.reqDto.ModifyCustomerReqDto;
import com.example.taepang.domain.customer.dto.resDto.CreateCustomerResDto;
import com.example.taepang.domain.customer.dto.resDto.FindCustomerResDto;
import com.example.taepang.domain.customer.dto.resDto.UpdateCustomerResDto;
import com.example.taepang.domain.customer.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

	private final CustomerService customerService;

	// 멤버 객체 생성 (일반 사용자)
	@PostMapping("/join")
	public ResponseEntity<CreateCustomerResDto> createCustomer(@RequestBody CreateCustomerReqDto reqDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createUser(reqDto));
	}

	// 멤버 객체 조회 (일단은 id 값 기준으로)
	@GetMapping("/info/{id}")
	public ResponseEntity<FindCustomerResDto> getCustomerInfo(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(customerService.findUser(id));
	}

	// 멤버 객체 수정 (일부 수정, id 값 기준)
	@PatchMapping("/modify/{id}")
	public ResponseEntity<UpdateCustomerResDto> modifyCustomer(@PathVariable Long id,
		@RequestBody ModifyCustomerReqDto reqDto) {
		return ResponseEntity.status(HttpStatus.OK).body(customerService.modifyUser(id, reqDto));
	}

	@DeleteMapping("/remove/{id}")
	public ResponseEntity<Void> removeCustomer(@PathVariable Long id) {
		customerService.removeUser(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
