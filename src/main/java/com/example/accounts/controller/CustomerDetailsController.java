package com.example.accounts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.accounts.constants.AccountConstants;
import com.example.accounts.dto.CustomerDetailsDto;
import com.example.accounts.service.ICustomerService;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor /// as here we are using constuctor autowirring for IAccountService
@Validated
public class CustomerDetailsController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerDetailsController.class);
	ICustomerService iCustomerService;

	@GetMapping("/fetchCustomerDetails")
	public ResponseEntity<CustomerDetailsDto> getCustomer(
			@RequestHeader("eazybank-correlation-id") String correlationId,
			@RequestParam @Pattern(regexp = AccountConstants.MOBILE_NUMBER_PATTERN, message = "mobileNumber must be 10 digit") String mobileNumber) {
		logger.debug("eazyBank-correlation-id found: {} ", correlationId);
		CustomerDetailsDto customerDetailsDto = iCustomerService.fetchCustomerDetails(mobileNumber, correlationId);
		return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
	}
}
