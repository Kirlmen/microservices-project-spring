package com.kirl.accounts.repository;

import com.kirl.accounts.entity.Accounts;
import com.kirl.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByMobileNumber(String mobileNumber);

}
