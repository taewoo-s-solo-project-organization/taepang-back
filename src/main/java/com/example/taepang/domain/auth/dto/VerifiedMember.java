package com.example.taepang.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VerifiedMember {
	private final Long id;
	private final String email;

}

