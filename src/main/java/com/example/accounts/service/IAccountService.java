package com.example.accounts.service;

import com.example.accounts.dto.CustomerDto;

public interface IAccountService {
	/*
	 * 
	 */
	void createAccount(CustomerDto customerDto);
	
	 CustomerDto fetchAccount(String mobileNumber);
	 
	 boolean updateAccount(CustomerDto customerDto);
	 
	 boolean deleteAccount(String mobileNumber);
	 
	 default void print() {
		 System.out.println("printed in IAccount");
	 }
}
