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
	 
	 /**
     *
     * @param accountNumber - Long
     * @return boolean indicating if the update of communication status is successful or not
     */
    boolean updateCommunicationStatus(Long accountNumber);
}
