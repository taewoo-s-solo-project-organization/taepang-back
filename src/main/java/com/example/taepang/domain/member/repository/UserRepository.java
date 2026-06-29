package com.example.taepang.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taepang.domain.member.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);
}
