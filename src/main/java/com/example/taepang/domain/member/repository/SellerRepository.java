package com.example.taepang.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taepang.domain.member.entity.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
