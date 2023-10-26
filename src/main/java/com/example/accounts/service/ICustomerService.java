package com.example.accounts.service;

import org.springframework.stereotype.Service;

import com.example.accounts.dto.CustomerDetailsDto;

@Service
public interface ICustomerService {
	CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);
}
