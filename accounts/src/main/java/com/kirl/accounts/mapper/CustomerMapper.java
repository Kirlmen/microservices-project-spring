package com.kirl.accounts.mapper;

import com.kirl.accounts.dto.CustomerDetailsDto;
import com.kirl.accounts.dto.CustomerDto;
import com.kirl.accounts.entity.Customer;

public class CustomerMapper {

	// Mapping entity ➡️ DTO (used when sending response back to client)
	public static CustomerDto mapToCustomerDto(Customer customer,CustomerDto customerDto){
		customerDto.setEmail(customer.getEmail());
		customerDto.setName(customer.getName());
		customerDto.setMobileNumber(customer.getMobileNumber());

		return customerDto;
	}

	public static CustomerDetailsDto mapToCustomerDetailsDto(Customer customer, CustomerDetailsDto customerDetailsDto){
		customerDetailsDto.setEmail(customer.getEmail());
		customerDetailsDto.setName(customer.getName());
		customerDetailsDto.setMobileNumber(customer.getMobileNumber());

		return customerDetailsDto;
	}

	// Mapping DTO ➡️ entity (used when saving data to DB)
	public static Customer mapToCustomer(CustomerDto customerDto, Customer customer){
		customer.setEmail(customerDto.getEmail());
		customer.setName(customerDto.getName());
		customer.setMobileNumber(customerDto.getMobileNumber());

		return customer;
	}
}
