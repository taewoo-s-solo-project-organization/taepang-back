package com.example.taepang.domain.customer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taepang.domain.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	boolean existsByEmail(String email);

	Optional<Customer> findByIdAndDeletedAtIsNull(Long id);
}
