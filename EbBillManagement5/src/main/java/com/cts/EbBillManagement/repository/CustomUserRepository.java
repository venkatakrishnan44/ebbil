package com.cts.EbBillManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.EbBillManagement.model.Customer;


public interface CustomUserRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findByName(String username);
	
}
