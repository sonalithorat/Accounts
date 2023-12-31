package com.example.accounts.impl;

import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.example.accounts.constants.AccountConstants;
import com.example.accounts.dto.AccountsDto;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.dto.AccountsMsgDto;
import com.example.accounts.entity.Account;
import com.example.accounts.entity.Customer;
import com.example.accounts.exception.CustomerAlreadyExistsException;
import com.example.accounts.exception.ResourceNotFoundException;
import com.example.accounts.mapper.AccountMapper;
import com.example.accounts.mapper.CustomerMapper;
import com.example.accounts.repository.AccountRepository;
import com.example.accounts.repository.CustomerRepository;
import com.example.accounts.service.IAccountService;

@Service
public class AccountsServiceImpl implements IAccountService {
	private static final Logger log = LoggerFactory.getLogger(AccountsServiceImpl.class);
	@Autowired
	AccountRepository accountRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	private StreamBridge streamBridge;

	@Override
	public void createAccount(CustomerDto customerDto) {
		Customer customer = CustomerMapper.mapTOCustomer(new Customer(), customerDto);
		Optional<Customer> opCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
		if (opCustomer.isPresent()) {
			throw new CustomerAlreadyExistsException(
					"Customer already exists with given mobile number" + customerDto.getMobileNumber());
		}
		Customer savedCustomer = customerRepository.save(customer);
		Account savedAccount = accountRepository.save(createNewAccount(savedCustomer));
		sendCommunication(savedAccount, savedCustomer);
	}

	private void sendCommunication(Account account, Customer customer) {
		var accountsMsgDto = new AccountsMsgDto(account.getAccountNumber(), customer.getName(), customer.getEmail(),
				customer.getMobileNumber());
		log.info("Sending Communication request for the details: {}", accountsMsgDto);
		var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
		log.info("Is the Communication request successfully triggered ? : {}", result);
	}

	private Account createNewAccount(Customer customer) {
		Account newAccount = new Account();
		newAccount.setCustomerId(customer.getCustomerId());
		long accNum = 1000000000L + new Random().nextInt();
		newAccount.setAccountNumber(accNum);
		newAccount.setAccountType(AccountConstants.SAVINGS);
		newAccount.setBranchAddress(AccountConstants.ADDRESS);
		return newAccount;
	}

	@Override
	public CustomerDto fetchAccount(String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

		Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

		CustomerDto customerDto = CustomerMapper.mapTOCustomerDto(customer, new CustomerDto());
		customerDto.setAccountsDto(AccountMapper.mapTOAccountDto(account, new AccountsDto()));
		return customerDto;
	}

	@Override
	public boolean updateAccount(CustomerDto customerDto) {
		boolean isUpdated = false;
		AccountsDto accountsDto = customerDto.getAccountsDto();
		if (accountsDto != null) {
			Account account = accountRepository.findById(accountsDto.getAccountNumber())
					.orElseThrow(() -> new ResourceNotFoundException("Account", "AccountNumber",
							accountsDto.getAccountNumber().toString()));

			AccountMapper.mapTOAccount(account, accountsDto);
			account = accountRepository.save(account);

			Long customerId = account.getCustomerId();
			Customer customer = customerRepository.findById(customerId)
					.orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerId", customerId.toString()));

			CustomerMapper.mapTOCustomer(customer, customerDto);
			customerRepository.save(customer);

		}
		isUpdated = true;

		return isUpdated;

	}

	@Override
	public boolean deleteAccount(String mobileNumber) {
		boolean isDeleted = false;
		Customer customer = customerRepository.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
		accountRepository.deleteByCustomerId(customer.getCustomerId());
		customerRepository.deleteById(customer.getCustomerId());
		return isDeleted;
	}

	@Override
	public boolean updateCommunicationStatus(Long accountNumber) {
		boolean isUpdated = false;
        if(accountNumber !=null ){
            Account accounts = accountRepository.findById(accountNumber).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountNumber.toString())
            );
            accounts.setCommunicationSw(true);
            accountRepository.save(accounts);
            isUpdated = true;
        }
        return  isUpdated;
	}

}
