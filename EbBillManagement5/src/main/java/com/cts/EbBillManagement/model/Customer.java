package com.cts.EbBillManagement.model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String customerId;

    @NotBlank(message = "name cannot be empty")
    private String name;
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;
    @NotBlank(message = "Password cannot be empty")
    private String password;
    
    private String role;


    
    @PrePersist
    void prePersist()
    {
    	if(this.role==null)
    	{
    		this.role="ROLE_USER";
    	}
    }
}

