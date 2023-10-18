package com.example.accounts.mapper;

import com.example.accounts.dto.AccountsDto;
import com.example.accounts.entity.Account;

public class AccountMapper {
	
	public static AccountsDto mapTOAccountDto(Account account, AccountsDto accountsDto) {
		accountsDto.setAccountNumber(account.getAccountNumber());
		accountsDto.setAccountType(account.getAccountType());
		accountsDto.setBranchAddress(account.getBranchAddress());
		return accountsDto;
	}
	
	public static Account mapTOAccount(Account account, AccountsDto accountsDto) {
		account.setAccountNumber(accountsDto.getAccountNumber());
		account.setAccountType(accountsDto.getAccountType());
		account.setBranchAddress(accountsDto.getBranchAddress());
		return account;
	}
}
