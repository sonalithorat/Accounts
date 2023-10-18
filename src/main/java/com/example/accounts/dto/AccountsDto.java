package com.example.accounts.dto;

import com.example.accounts.constants.AccountConstants;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {
	@NotEmpty(message = "account number can not be null or empty")
	@Pattern(regexp = AccountConstants.MOBILE_NUMBER_PATTERN, message = "account number must be 10 digit")
	private Long accountNumber;
	
	@NotEmpty(message = "accountType can not be null or empty")
	private String accountType;
	
	@NotEmpty(message = "branchAddress can not be null or empty")
	private String branchAddress;
}
