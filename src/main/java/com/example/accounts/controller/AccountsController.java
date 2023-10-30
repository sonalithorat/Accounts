package com.example.accounts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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

import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor /// as here we are using constuctor autowirring for IAccountService
@Validated
public class AccountsController {
	private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);

	@Autowired
	AccountConstants accountConstants;

	@Autowired
	IAccountService iaccountService;

	@Autowired
	Environment environment;

	@GetMapping("sayHello")
	public String Hello() {
		System.out.println("Hello");
		return "hi world";
	}

	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
		iaccountService.createAccount(customerDto);
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

	@Retry(name = "getBuildInfo",fallbackMethod = "getBuildInfoFallback")
	@GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo(@Value("${accounts.message}") String buildVersion) {
		logger.info("inside getBuildInfo method");
		throw new NullPointerException();
		/*
		 * return ResponseEntity .status(HttpStatus.OK) .body(buildVersion);
		 */
    }
	
	 public ResponseEntity<String> getBuildInfoFallback(Throwable throwable) 
	 {
		 logger.info("inside getBuildInfoFallback method");
		 return ResponseEntity
                 .status(HttpStatus.OK)
                 .body("9.0");
	 }
	@GetMapping("/message")
    public ResponseEntity<String> getMessage(@Value("${accounts.message}") String buildVersion) {
        return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(buildVersion);
    }
	
	@GetMapping("/contact-info")
    public ResponseEntity<String> getContactInfo() {
		
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("sonali thorat");
    }
}
