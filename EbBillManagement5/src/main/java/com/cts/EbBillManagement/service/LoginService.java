package com.cts.EbBillManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cts.EbBillManagement.model.Customer;
import com.cts.EbBillManagement.repository.CustomerRepository;

@Service
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class); 

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<String> createUser(String username, String useremail, String password, Customer customer) {
        logger.info("Creating user with username: {}, useremail: {}", username, useremail); 
        if (customerRepository.findByEmail(useremail) != null) {
            logger.warn("User already exists with email: {}", useremail); 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }
        if (customerRepository.findByName(username) != null) {
            logger.warn("Username already taken: {}", username); 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already taken");
        }
        Customer user = new Customer();
        user.setCustomerId(customer.getCustomerId());
        user.setName(username);
        user.setEmail(useremail);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhoneNumber(customer.getPhoneNumber());
        user.setRole(customer.getRole());
        customerRepository.save(user);
        logger.info("User with username: {} created successfully", username); 
        return ResponseEntity.status(HttpStatus.OK).body("Registration successful");
    }

    public ResponseEntity<String> loginUser(String username, String password) {
        logger.info("User login attempt with username: {}", username); 
        Customer user = customerRepository.findByName(username);
        if (user == null) {
            logger.warn("User not found with username: {}", username); 
            return ResponseEntity.status(404).body("User not found, please register");
        }
        boolean passwordMatch = passwordEncoder.matches(password, user.getPassword());
        if (passwordMatch) {
            logger.info("Login successful for username: {}", username); 
            return ResponseEntity.ok("Login successful");
        } else {
            logger.warn("Invalid password for username: {}", username); 
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
