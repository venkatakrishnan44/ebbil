package com.cts.EbBillManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bill")
public class Bill {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @NotNull(message = "Unit consumed cannot be null")
    private Double unitConsumed;
    
    @FutureOrPresent(message = "Bill due date must be in the present or future")
    private LocalDate billDueDate;
    
  
    @Min(value = 1, message = "Duration must be at least 1 month")
    private int durationInMonths;

    private Double amountDue;
    
    @NotBlank(message = "Payment status cannot be blank")
    private String isPaid;

}
