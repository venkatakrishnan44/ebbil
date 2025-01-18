package com.cts.EbBillManagement.service;

import com.cts.EbBillManagement.model.Bill;
import com.cts.EbBillManagement.model.Customer;
import com.cts.EbBillManagement.repository.BillRepository;
import com.cts.EbBillManagement.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(BillService.class);

    private static final double RATE_PER_UNIT = 12.0; 
    
    public Bill createBill(Bill bill, String customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId);
        bill.setAmountDue(calculateBillAmount(bill.getUnitConsumed()));
        bill.setCustomer(customer);
        Bill savedBill = billRepository.save(bill);
        logger.info("Created bill with ID: {}", savedBill.getId());
        return savedBill;
    }
    
    public List<Bill> findAllBills() {
        logger.info("Fetching all bills");
        List<Bill> bills = billRepository.findAll();
        logger.info("Fetched {} bills", bills.size());
        return bills;
    }

    public Optional<Bill> getBillById(Long id) {
        logger.info("Fetching bill with ID: {}", id);
        Optional<Bill> bill = billRepository.findById(id);
        if (bill.isPresent()) {
            logger.info("Bill found with ID: {}", id);
        } else {
            logger.warn("No bill found with ID: {}", id);
        }
        return bill;
    }

    public List<Bill> getUnpaidBillsByCustomerId(String customerId) {
        logger.info("Fetching unpaid bills for customer ID: {}", customerId);
        List<Bill> unpaidBills = billRepository.findByCustomerCustomerIdAndIsPaidFalse(customerId);
        logger.info("Fetched {} unpaid bills for customer ID: {}", unpaidBills.size(), customerId);
        return unpaidBills;
    }

    public boolean deleteBill(Long id) {
        logger.info("Deleting bill with ID: {}", id);
        if (billRepository.existsById(id)) {
            billRepository.deleteById(id);
            logger.info("Deleted bill with ID: {}", id);
            return true;
        } 
        else {
            logger.warn("No bill found with ID: {}", id);
            return false;
        }
    }

    public double calculateBillAmount(double unitsConsumed) {
        logger.info("Calculating bill amount for units consumed: {}", unitsConsumed);
        double amount = unitsConsumed * RATE_PER_UNIT;
        logger.info("Calculated amount: {}", amount);
        return amount;   
    }
    
    public Bill updateBill(Bill bill) {
        return billRepository.save(bill);
    }
}
