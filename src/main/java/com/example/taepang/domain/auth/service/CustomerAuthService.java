package com.example.taepang.domain.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.taepang.domain.auth.dto.LoginReqDto;
import com.example.taepang.domain.auth.dto.LoginResDto;
import com.example.taepang.domain.auth.dto.SignupReqDto;
import com.example.taepang.domain.customer.entity.Customer;
import com.example.taepang.domain.customer.repository.CustomerRepository;
import com.example.taepang.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerAuthService implements AuthService {

	private final CustomerRepository customerRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	@Override
	public void signup(SignupReqDto signupReqDto) {
		if (customerRepository.existsByEmail(signupReqDto.getEmail())) {
			throw new IllegalArgumentException("이미 사용중인 이메일 입니다.");
		}

		String hashedPassword = passwordEncoder.encode(signupReqDto.getPassword());

		Customer customer = Customer.createUser(
			signupReqDto.getUsername(), signupReqDto.getPhoneNumber(), hashedPassword, signupReqDto.getEmail()
		);

		customerRepository.save(customer);

	}

	@Override
	public LoginResDto login(LoginReqDto loginReqDto) {
		Customer customer = customerRepository.findByEmail(loginReqDto.getEmail()).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 이메일 입니다.")
		);

		if (!passwordEncoder.matches(loginReqDto.getPassword(), customer.getPassword())) {
			throw new IllegalArgumentException("올바르지 않은 정보입니다.");
		}
		String token = jwtUtil.createToken(customer.getId(), customer.getEmail());

		return LoginResDto.builder()
			.accessToken(token)
			.build();
	}

}
