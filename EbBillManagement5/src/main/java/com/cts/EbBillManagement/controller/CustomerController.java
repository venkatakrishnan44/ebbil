package com.cts.EbBillManagement.controller;
import com.cts.EbBillManagement.model.Customer;
import com.cts.EbBillManagement.service.CustomerService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin	
@RequestMapping("/api/customers")
public class CustomerController {
	 @Autowired
	    private CustomerService customerService;


	    @GetMapping
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    public ResponseEntity<List<Customer>> getAllCustomers() {
	        return customerService.getAllCustomers();
	    }

	    @GetMapping("/{id}")
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
	        return customerService.getCustomerById(id);
	    }
	    
	    @DeleteMapping("/{id}")
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
	        return customerService.deleteCustomer(id);
	    }
	    
	    @PutMapping("/{id}")
	    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @Valid@RequestBody Customer customerDetails) {
	        return customerService.updateCustomer(id, customerDetails);
	    }

}
