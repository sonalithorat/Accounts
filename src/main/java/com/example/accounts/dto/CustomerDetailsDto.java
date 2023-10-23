package com.example.accounts.dto;

import com.example.accounts.constants.AccountConstants;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerDetailsDto {
	@NotEmpty(message = "name can not be null or empty")
	private String name;
	
	@NotEmpty(message = "mobemailileNumber can not be null or empty")
	@Email(message = "email should be valid value")
	private String email;
	
	@NotEmpty(message = "mobileNumber can not be null or empty")
	@Pattern(regexp = AccountConstants.MOBILE_NUMBER_PATTERN, message = "mobileNumber must be 10 digit")
	private String mobileNumber;
	
	private AccountsDto accountsDto;
	private LoansDto loansDto;
	private CardsDto cardsDto;
}
