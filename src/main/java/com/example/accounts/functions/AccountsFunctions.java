package com.example.accounts.functions;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.accounts.service.IAccountService;

@Configuration
public class AccountsFunctions {

	@Autowired 
	IAccountService iAccountService;
	private static final Logger log = LoggerFactory.getLogger(AccountsFunctions.class);

    @Bean
    public Consumer<Long> updateCommunication() {
        return accountNumber -> {
            log.info("Updating Communication status for the account number : " + accountNumber.toString());
            iAccountService.updateCommunicationStatus(accountNumber);
        };
    }
}
