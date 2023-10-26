package com.example.accounts.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.accounts.dto.LoansDto;

@FeignClient(name = "loans", fallback = LoansFallback.class)
public interface LoanFeignClient {

	@GetMapping(value = "/api/fetch", consumes = "application/json")
	public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam String mobileNumber,
			@RequestHeader("eazybank-correlation-id") String correlationId);
}
