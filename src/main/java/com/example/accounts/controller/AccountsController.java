package com.example.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.accounts.constants.AccountConstants;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.dto.ResponseDto;
import com.example.accounts.service.IAccountService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor /// as here we are using constuctor autowirring for IAccountService
@Validated
public class AccountsController {

	@Autowired
	AccountConstants accountConstants;

	@Autowired
	IAccountService iaccountService;

	@GetMapping("sayHello")
	public String Hello() {
		return "hi world";
	}

	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
		iaccountService.createAccount(customerDto);
		iaccountService.print();
		return ResponseEntity.status(HttpStatus.CREATED.value())
				.body(new ResponseDto(AccountConstants.MESSAGE_201, AccountConstants.MESSAGE_201));
	}

	@GetMapping("/fetch")
	public ResponseEntity<CustomerDto> getCustomer(
			@RequestParam @Pattern(regexp = AccountConstants.MOBILE_NUMBER_PATTERN, message = "mobileNumber must be 10 digit") String mobileNumber) {
		CustomerDto customerDto = iaccountService.fetchAccount(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(customerDto);
	}

	@PutMapping("/update")
	public ResponseEntity<ResponseDto> update(@Valid @RequestBody CustomerDto customerDto) {

		boolean isUpdated = iaccountService.updateAccount(customerDto);
		if (isUpdated = false) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(AccountConstants.STATUS_500, AccountConstants.MESSAGE_500));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> delete(
			@RequestParam @Pattern(regexp = AccountConstants.MOBILE_NUMBER_PATTERN, message = "mobileNumber must be 10 digit") String mobileNumber) {
		boolean isDeleted = iaccountService.deleteAccount(mobileNumber);
		if (isDeleted = false) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto(AccountConstants.STATUS_500, AccountConstants.MESSAGE_500));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
		}

	}
}
