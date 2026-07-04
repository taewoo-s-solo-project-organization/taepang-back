package com.example.taepang.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.taepang.domain.member.dto.reqDto.CreateUserReqDto;
import com.example.taepang.domain.member.dto.reqDto.ModifyUserReqDto;
import com.example.taepang.domain.member.dto.resDto.CreateUserResDto;
import com.example.taepang.domain.member.dto.resDto.FindUserResDto;
import com.example.taepang.domain.member.entity.User;
import com.example.taepang.domain.member.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private User findUserById(Long id) {
		return userRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID" + id)
		);
	}

	public CreateUserResDto createUser(CreateUserReqDto reqDto) {
		if (userRepository.existsByEmail(reqDto.getEmail())) {
			throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
		}

		User user = User.createUser(reqDto.getUsername(), reqDto.getPhoneNumber(), reqDto.getEmail());
		userRepository.save(user);
		return CreateUserResDto.from(user);
	}

	public FindUserResDto findUser(Long id) {
		return FindUserResDto.from(findUserById(id));
	}

	@Transactional
	public FindUserResDto modifyUser(Long id, ModifyUserReqDto reqDto) {
		User user = findUserById(id);
		user.update(reqDto);// JPA 의 변경감지
		return FindUserResDto.from(user);
	}

	@Transactional
	public void removeUser(Long id) {
		userRepository.delete(findUserById(id)); // TODO : Soft Delete 로 변경 필요
	}
}
