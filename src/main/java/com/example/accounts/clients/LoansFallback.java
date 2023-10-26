package com.example.accounts.clients;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.accounts.dto.CardsDto;
import com.example.accounts.dto.LoansDto;

@Component
public class LoansFallback implements LoanFeignClient{

	@Override
	public ResponseEntity<LoansDto> fetchLoanDetails(String mobileNumber, String correlationId) {
		System.out.println("in Loans fallback methos");
		return null;
	}

}
