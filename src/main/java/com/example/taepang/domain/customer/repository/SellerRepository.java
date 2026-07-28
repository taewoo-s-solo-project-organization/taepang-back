package com.example.taepang.domain.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taepang.domain.customer.entity.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
