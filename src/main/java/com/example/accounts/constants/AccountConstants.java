package com.example.accounts.constants;

import org.springframework.stereotype.Component;

@Component
public class AccountConstants {
	public static final String SAVINGS = "savings";
	public static final String ADDRESS = "123 main street, New york";
	public static final String STATUS_201 = "201";
	public static final String MESSAGE_201 = "account created";
	public static final String STATUS_200 = "200";
	public static final String MESSAGE_200 = "success";
	public static final String STATUS_500 = "500";
	public static final String MESSAGE_500 = "failed";
	public static final String MOBILE_NUMBER_PATTERN = "(^$|[0-9]{10})";
}
