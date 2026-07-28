package com.example.taepang.domain.customer.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.taepang.domain.customer.dto.reqDto.ModifyCustomerReqDto;
import com.example.taepang.domain.customer.dto.resDto.FindCustomerResDto;
import com.example.taepang.domain.customer.dto.resDto.UpdateCustomerResDto;
import com.example.taepang.domain.customer.entity.Customer;
import com.example.taepang.domain.customer.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;

	private Customer findUserById(Long id) {
		return customerRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(
			() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID : " + id)
		);
	}

	public FindCustomerResDto findUser(Long id) {
		return FindCustomerResDto.from(findUserById(id));
	}

	@Transactional
	public UpdateCustomerResDto modifyUser(Long id, ModifyCustomerReqDto reqDto) {
		Customer customer = findUserById(id);
		customer.update(reqDto);// JPA 의 변경감지
		return UpdateCustomerResDto.from(customer);
	}

	@Transactional
	public void removeUser(Long id) {
		findUserById(id).delete();
	}
}
