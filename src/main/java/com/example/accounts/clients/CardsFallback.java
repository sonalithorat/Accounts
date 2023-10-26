package com.example.accounts.clients;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.accounts.dto.CardsDto;

@Component
public class CardsFallback implements CardsFeignClient{

	@Override
	public ResponseEntity<CardsDto> fetchCardDetails(String mobileNumber, String correlationId) {
		System.out.println("in Cards fallback methos");
		return null;
	}

}
