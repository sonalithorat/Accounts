package com.example.accounts.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.accounts.dto.CardsDto;
import com.example.accounts.dto.LoansDto;

@FeignClient(name = "cards", fallback = CardsFallback.class)
public interface CardsFeignClient {

	@GetMapping(value = "/api/fetch", consumes = "application/json")
	public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam String mobileNumber,
			@RequestHeader("eazybank-correlation-id") String correlationId);
}
