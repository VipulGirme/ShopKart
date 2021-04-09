package com.example.polls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.polls.security.ConfirmationToken;


public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, String> {
	ConfirmationToken findByConfirmationToken(String confirmationToken);
}