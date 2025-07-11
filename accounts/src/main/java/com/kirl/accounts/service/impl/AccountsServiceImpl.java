package com.kirl.accounts.service.impl;

import com.kirl.accounts.constants.AccountsConstants;
import com.kirl.accounts.dto.AccountsDto;
import com.kirl.accounts.dto.CustomerDto;
import com.kirl.accounts.entity.Accounts;
import com.kirl.accounts.entity.Customer;
import com.kirl.accounts.exception.CustomerAlreadyExistException;
import com.kirl.accounts.exception.ResourceNotFoundException;
import com.kirl.accounts.mapper.AccountsMapper;
import com.kirl.accounts.mapper.CustomerMapper;
import com.kirl.accounts.repository.AccountsRepository;
import com.kirl.accounts.repository.CustomerRepository;
import com.kirl.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {
	AccountsRepository accountsRepository;
	CustomerRepository customerRepository;


	@Override
	public void createAccount(CustomerDto customerDto) {
		Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
		Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());

		if (optionalCustomer.isPresent()) {
			throw new CustomerAlreadyExistException("Customer already registered with given mobileNumber " + customerDto.getMobileNumber());
		}
		Customer savedCustomer = customerRepository.save(customer); //pullin the id of the customer who saved
		accountsRepository.save(createNewAccount(savedCustomer));
	}

	/**
	 * @param customer - Customer object
	 * @return new account details
	 */
	private Accounts createNewAccount(Customer customer) {
		Accounts newAccount = new Accounts();
		newAccount.setCustomerId(customer.getCustomerId());
		long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

		newAccount.setAccountType(AccountsConstants.SAVINGS);
		newAccount.setBranchAddress(AccountsConstants.ADDRESS);
		newAccount.setAccountNumber(randomAccNumber);

		return newAccount;
	}

	@Override
	public CustomerDto fetchAccount(String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
				() -> new ResourceNotFoundException("customer", "mobileNumber", mobileNumber)
		);
		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("accounts", "customerId", customer.getCustomerId().toString())
		);

		CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
		customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
		return customerDto;
	}

	@Override
	public boolean updateAccount(CustomerDto customerDto) {
		AccountsDto accountsDto = customerDto.getAccountsDto();
		boolean isUpdated = false;
		if (accountsDto != null) {
			Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
					() -> new ResourceNotFoundException("account", "Account Number", accountsDto.getAccountNumber().toString())
			);
			AccountsMapper.mapToAccounts(accountsDto, accounts); //received data is in accountsDto and we are moving that data to accounts.
			accounts = accountsRepository.save(accounts);

			long customerId = accounts.getCustomerId();
			Customer customer = customerRepository.findById(customerId).orElseThrow(
					() -> new ResourceNotFoundException("customer", "Customer ID", String.valueOf(customerId))
			);
			CustomerMapper.mapToCustomer(customerDto, customer);
			customerRepository.save(customer);
			isUpdated = true;
		}
		return isUpdated;
	}

	@Override
	public boolean deleteAccount(String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
				() -> new ResourceNotFoundException("customer", "mobileNumber", mobileNumber)
		);
		accountsRepository.deleteByCustomerId(customer.getCustomerId());
		customerRepository.deleteById(customer.getCustomerId());
		return true;
	}
}
