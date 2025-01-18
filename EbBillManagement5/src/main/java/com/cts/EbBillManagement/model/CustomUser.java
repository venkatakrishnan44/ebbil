package com.cts.EbBillManagement.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class CustomUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	String username;
	String password;
    List<GrantedAuthority> authorities;
	
	public CustomUser(Customer customer)
	{
		username=customer.getName();
		password=customer.getPassword();
		authorities=Arrays.stream(customer.getRole().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());//objects created and put into a list
	}
			
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

}
