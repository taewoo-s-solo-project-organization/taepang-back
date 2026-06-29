package com.example.taepang.domain.member.entity;

import java.time.LocalDateTime;

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
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;
	private String phoneNumber;
	@Column(nullable = false, unique = true)
	private String email;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@Builder
	public User(String username, String phoneNumber, String email) {
		this.username = username;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();

	}

	public static User createUser(String username, String phoneNumber, String email) {
		return User.builder()
			.username(username)
			.phoneNumber(phoneNumber)
			.email(email)
			.build();
	}

}
