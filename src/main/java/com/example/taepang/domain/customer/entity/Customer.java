package com.example.taepang.domain.customer.entity;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import com.example.taepang.domain.customer.dto.reqDto.ModifyCustomerReqDto;
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
@Table(name = "customers")
public class Customer extends TimeStamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;
	private String phoneNumber;
	@Column(nullable = false, unique = true)
	private String email;

	@Builder
	public Customer(String username, String phoneNumber, String email) {
		this.username = username;
		this.phoneNumber = phoneNumber;
		this.email = email;

	}

	public static Customer createUser(String username, String phoneNumber, String email) {
		return Customer.builder()
			.username(username)
			.phoneNumber(phoneNumber)
			.email(email)
			.build();
	}

	public void update(ModifyCustomerReqDto reqDto) {
		updateField(reqDto.getEmail(), this.email, val -> this.email = val);
		updateField(reqDto.getUsername(), this.username, val -> this.username = val);
		updateField(reqDto.getPhoneNumber(), this.phoneNumber, val -> this.phoneNumber = val);
	}

	private <T, V> void updateField(T value, V info, Consumer<T> setter) {
		if (value != null && !value.equals(info)) {
			setter.accept(value);
			this.updatedAt = LocalDateTime.now();
			// 가독성 위해 여러번 update
			// update 내용이 많아지면 한번만 발생하도록 수정 필요
		}
	}

	public void delete() {
		this.deletedAt = LocalDateTime.now();
	}
}
