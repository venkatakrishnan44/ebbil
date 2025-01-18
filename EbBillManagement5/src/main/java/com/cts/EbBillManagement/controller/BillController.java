package com.cts.EbBillManagement.controller;
import com.cts.EbBillManagement.model.Bill;
import com.cts.EbBillManagement.service.BillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {
	
	@Autowired
    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    
    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Bill>> getBills() {
        List<Bill> bills = billService.findAllBills();
        return ResponseEntity.ok(bills);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
        return billService.getBillById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    
    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Bill>> getUnpaidBillsByCustomerId(@PathVariable String customerId) {
        List<Bill> unPaidBills = billService.getUnpaidBillsByCustomerId(customerId);
        return ResponseEntity.ok(unPaidBills);
    }
    
    
    @PostMapping("/createBill/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Bill> createBill(@RequestBody @Valid Bill bill, @PathVariable("id") String customerId) {
        Bill savedBill = billService.createBill(bill, customerId);
        return new ResponseEntity<>(savedBill, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteBill(@PathVariable Long id) {
    	
        if (billService.deleteBill(id)) {
        	 return ResponseEntity.ok("Successfully deleted");
        } 
        else {
        	return new ResponseEntity<>("Bill not found", HttpStatus.NOT_FOUND);
        }   
    }
    
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Bill> updateBill(@PathVariable("id") Long id, @RequestBody @Valid Bill billDetails) {
        return billService.getBillById(id)
                .map(bill -> {
                    bill.setUnitConsumed(billDetails.getUnitConsumed());
                    bill.setBillDueDate(billDetails.getBillDueDate());
                    bill.setDurationInMonths(billDetails.getDurationInMonths());
                    bill.setAmountDue(billDetails.getAmountDue());
                    bill.setIsPaid(billDetails.getIsPaid());
                    Bill updatedBill = billService.updateBill(bill);
                    return ResponseEntity.ok(updatedBill);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
 

}
