package com.kirl.accounts.service.impl;

import com.kirl.accounts.dto.AccountsDto;
import com.kirl.accounts.dto.CardsDto;
import com.kirl.accounts.dto.CustomerDetailsDto;
import com.kirl.accounts.dto.LoansDto;
import com.kirl.accounts.entity.Accounts;
import com.kirl.accounts.entity.Customer;
import com.kirl.accounts.exception.ResourceNotFoundException;
import com.kirl.accounts.mapper.AccountsMapper;
import com.kirl.accounts.mapper.CustomerMapper;
import com.kirl.accounts.repository.AccountsRepository;
import com.kirl.accounts.repository.CustomerRepository;
import com.kirl.accounts.service.ICustomersService;
import com.kirl.accounts.service.client.CardsFeignClient;
import com.kirl.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

	private AccountsRepository accountsRepository;
	private CustomerRepository customerRepository;
	private CardsFeignClient cardsFeignClient;
	private LoansFeignClient loansFeignClient;

	@Override
	public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
				() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
		);
		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
		);
		CustomerDetailsDto detailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
		detailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

		ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoans(mobileNumber, correlationId);
		detailsDto.setLoansDto(loansDtoResponseEntity.getBody());

		ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCards(mobileNumber, correlationId);
		detailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

		return detailsDto;

	}
}
