package com.cts.EbBillManagement.repository;
import com.cts.EbBillManagement.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCustomerId(String customerId);
    Customer findByEmail(String email);
    Customer findByPhoneNumber(String phoneNumber);
	Customer findByName(String username);
	
}