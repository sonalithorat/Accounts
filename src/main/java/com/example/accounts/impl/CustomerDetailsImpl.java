package com.example.accounts.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.accounts.clients.CardsFeignClient;
import com.example.accounts.clients.LoanFeignClient;
import com.example.accounts.dto.AccountsDto;
import com.example.accounts.dto.CardsDto;
import com.example.accounts.dto.CustomerDetailsDto;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.dto.LoansDto;
import com.example.accounts.entity.Account;
import com.example.accounts.entity.Customer;
import com.example.accounts.exception.ResourceNotFoundException;
import com.example.accounts.mapper.AccountMapper;
import com.example.accounts.mapper.CustomerMapper;
import com.example.accounts.repository.AccountRepository;
import com.example.accounts.repository.CustomerRepository;
import com.example.accounts.service.ICustomerService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerDetailsImpl implements ICustomerService {

	// here autowired is not required we are using constructor injection using
	// @AllArgsConstructor
	private LoanFeignClient loanFeignClient;

	private CardsFeignClient cardsFeignClient;

	private CustomerRepository customerRepository;

	private AccountRepository accountRepository;

	@Override
	public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

		Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

		CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer,
				new CustomerDetailsDto());
		customerDetailsDto.setAccountsDto(AccountMapper.mapTOAccountDto(account, new AccountsDto()));
		ResponseEntity<LoansDto> loansEntity = loanFeignClient.fetchLoanDetails(mobileNumber, correlationId);
		if (null != loansEntity) {
			customerDetailsDto.setLoansDto(loansEntity.getBody());
		}
		ResponseEntity<CardsDto> cardsEntity = cardsFeignClient.fetchCardDetails(mobileNumber, correlationId);
		if (null != cardsEntity) {
			customerDetailsDto.setCardsDto(cardsEntity.getBody());
		}
		return customerDetailsDto;
	}

}
