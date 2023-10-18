package com.example.accounts.service;

import com.example.accounts.dto.CustomerDto;

public interface Example {
	 boolean updateAccount(CustomerDto customerDto);
	 default void print() {
		 System.out.println("printed in Example");
	 }
}
