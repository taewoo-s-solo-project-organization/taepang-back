package com.example.taepang.domain.member.dto.reqDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ModifyUserReqDto {

	// TODO : CreateUserReqDto 와 완전히 동일하다. 중복 제거 또는 구조화 필요
	private final String username;
	private final String email;
	private final String phoneNumber;

}

