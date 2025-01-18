package com.cts.EbBillManagement.service;

import com.cts.EbBillManagement.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.cts.EbBillManagement.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
	
    @Autowired
    private CustomerRepository customerRepository;

    public ResponseEntity<List<Customer>> getAllCustomers() {     
    	 logger.info("Fetching all customers"); 
         List<Customer> customers = customerRepository.findAll();
         logger.info("Fetched {} customers", customers.size());
         return ResponseEntity.ok(customers);
    }

    public ResponseEntity<Customer> getCustomerById(String id) {
           	logger.info("Fetching customer with ID: {}", id); 
        Customer customer = customerRepository.findByCustomerId(id);
        if (customer != null) {
            logger.info("Customer found with ID: {}", id); 
            return ResponseEntity.ok(customer);
        } else {
            logger.warn("No customer found with ID: {}", id); 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    public ResponseEntity<Void> deleteCustomer(Long id) {
    	logger.info("Deleting customer with ID: {}", id); 
        try {
            customerRepository.deleteById(id);
            logger.info("Deleted customer with ID: {}", id); 
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            logger.error("Error deleting customer with ID: {}", id, e); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        }
        



    public ResponseEntity<Customer> updateCustomer(Long id, Customer customerDetails) {
    	 logger.info("Updating customer with ID: {}", id); // Added logging
         try {
             Customer customer = customerRepository.findById(id).orElse(null);
             if (customer != null) {
                 customer.setName(customerDetails.getName());
                 customer.setEmail(customerDetails.getEmail());
                 customer.setPhoneNumber(customerDetails.getPhoneNumber());
                 Customer updatedCustomer = customerRepository.save(customer);
                 logger.info("Updated customer with ID: {}", id); // Added logging
                 return ResponseEntity.ok(updatedCustomer);
             } else {
                 logger.warn("No customer found with ID: {}", id); // Added logging
                 return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
             }
         } catch (Exception e) {
             logger.error("Error updating customer with ID: {}", id, e); // Added logging
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
         }
     }
    }


