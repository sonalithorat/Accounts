package com.example.accounts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import com.example.accounts.dto.CustomerDto;
import com.example.accounts.entity.Account;

import jakarta.transaction.Transactional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByCustomerId(Long customerId);
	
	@Transactional
	@Modifying
	void deleteByCustomerId(Long customerId);
}
