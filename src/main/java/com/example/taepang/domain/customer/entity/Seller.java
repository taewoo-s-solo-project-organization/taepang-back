package com.example.taepang.domain.customer.entity;

import com.example.taepang.global.TimeStamped;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sellers")
public class Seller extends TimeStamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String businessNumber; // 사업자 번호
	private String account; // 계좌

	@OneToOne
	@JoinColumn(name = "customer_id")
	private Customer customer; // Seller 없이는 User 가 있어도, User 없이는 Seller 가 있을 수 는 없기 때문에 외래키 주인을 이쪽에

}
