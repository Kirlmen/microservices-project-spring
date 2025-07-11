package com.kirl.accounts.mapper;

import com.kirl.accounts.dto.AccountsDto;
import com.kirl.accounts.entity.Accounts;

public class AccountsMapper {

	//toAccountsDto
	public static AccountsDto mapToAccountsDto(Accounts accounts, AccountsDto accountsDto){
		accountsDto.setAccountNumber(accounts.getAccountNumber());
		accountsDto.setAccountType(accounts.getAccountType());
		accountsDto.setBranchAddress(accounts.getBranchAddress());

		return accountsDto;
	}

	//toAccounts
	public static Accounts mapToAccounts(AccountsDto accountsDto, Accounts accounts){
		accounts.setAccountNumber(accountsDto.getAccountNumber());
		accounts.setAccountType(accountsDto.getAccountType());
		accounts.setBranchAddress(accountsDto.getBranchAddress());
		return accounts;

	}
}
