package com.cts.EbBillManagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.EbBillManagement.model.CustomUser;
import com.cts.EbBillManagement.model.Customer;
import com.cts.EbBillManagement.repository.CustomUserRepository;

@Component
public class CustomUserService implements UserDetailsService{
	private static final Logger logger = LoggerFactory.getLogger(CustomUserService.class);

	@Autowired
	private  CustomUserRepository customUserRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		logger.info("Attempting to load user by username: {}", username); 
        Optional<Customer> user = customUserRepository.findByName(username);
        return user.map(CustomUser::new)
                .orElseThrow(() -> {
                    logger.warn("User not found with username: {}", username); 
                    return new UsernameNotFoundException("User not found");
                });
    }
	}


