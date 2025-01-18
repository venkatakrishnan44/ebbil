package com.cts.EbBillManagement.repository;

import com.cts.EbBillManagement.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByCustomerCustomerIdAndIsPaidFalse(String customerId);

}

