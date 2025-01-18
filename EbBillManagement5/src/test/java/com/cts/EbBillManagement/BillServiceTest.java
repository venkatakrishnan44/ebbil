package com.cts.EbBillManagement;



import com.cts.EbBillManagement.model.Bill;
import com.cts.EbBillManagement.repository.BillRepository;
import com.cts.EbBillManagement.service.BillService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BillServiceTest {

    @Mock
    private BillRepository billRepository;

    @InjectMocks
    private BillService billService;

    private Bill bill;

    @BeforeEach
    void setUp() {
        bill = new Bill();
        bill.setId(1L);
        
    }

    @Test
    void testFindAllBills() {
        List<Bill> bills = Arrays.asList(bill);
        when(billRepository.findAll()).thenReturn(bills);

        List<Bill> result = billService.findAllBills();

        assertEquals(1, result.size());
        verify(billRepository, times(1)).findAll();
    }

    @Test
    void testGetBillById() {
        when(billRepository.findById(1L)).thenReturn(Optional.of(bill));

        Optional<Bill> result = billService.getBillById(1L);

        assertTrue(result.isPresent());
        assertEquals(bill.getId(), result.get().getId());
        verify(billRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUnpaidBillsByCustomerId() {
        List<Bill> unpaidBills = Arrays.asList(bill);
        when(billRepository.findByCustomerCustomerIdAndIsPaidFalse("customerId")).thenReturn(unpaidBills);
       

        List<Bill> result = billService.getUnpaidBillsByCustomerId("customerId");

        assertEquals(1, result.size());
        verify(billRepository, times(1)).findByCustomerCustomerIdAndIsPaidFalse("customerId");
       
    }


    @Test
    void testDeleteBill() {
        when(billRepository.existsById(1L)).thenReturn(true);

        boolean result = billService.deleteBill(1L);

        assertTrue(result);
        verify(billRepository, times(1)).existsById(1L);
        verify(billRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBillNotFound() {
        when(billRepository.existsById(1L)).thenReturn(false);

        boolean result = billService.deleteBill(1L);

        assertFalse(result);
        verify(billRepository, times(1)).existsById(1L);
        verify(billRepository, times(0)).deleteById(1L);
    }
}