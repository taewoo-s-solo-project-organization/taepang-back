package com.example.taepang.domain.member.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sellers")
public class Seller {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime registeredAt;
	private LocalDateTime updatedAt;

	private String businessNumber; // 사업자 번호
	private String account; // 계좌

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user; // Seller 없이는 User 가 있어도, User 없이는 Seller 가 있을 수 는 없기 때문에 외래키 주인을 이쪽에

}
