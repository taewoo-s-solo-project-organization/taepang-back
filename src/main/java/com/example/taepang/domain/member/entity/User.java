package com.example.taepang.domain.member.entity;

import com.example.taepang.domain.member.dto.reqDto.ModifyUserReqDto;
import com.example.taepang.global.TimeStamped;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends TimeStamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;
	private String phoneNumber;
	@Column(nullable = false, unique = true)
	private String email;

	@Builder
	public User(String username, String phoneNumber, String email) {
		this.username = username;
		this.phoneNumber = phoneNumber;
		this.email = email;

	}

	public static User createUser(String username, String phoneNumber, String email) {
		return User.builder()
			.username(username)
			.phoneNumber(phoneNumber)
			.email(email)
			.build();
	}

	public void updateUserInfo(ModifyUserReqDto reqDto) {
		// TODO : MapStruct 를 이용하여 if 문 간소화 하기
		if (reqDto.getEmail() != null) {
			this.email = reqDto.getEmail();
		}
		if (reqDto.getUsername() != null) {
			this.username = reqDto.getUsername();
		}
		if (reqDto.getPhoneNumber() != null) {
			this.phoneNumber = reqDto.getPhoneNumber();
		}

	}

}
