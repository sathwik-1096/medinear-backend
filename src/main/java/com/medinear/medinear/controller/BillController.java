package com.medinear.medinear.controller;

import com.medinear.medinear.dto.BillRequestDto;
import com.medinear.medinear.dto.BillResponseDto;
import com.medinear.medinear.entity.Bill;
import com.medinear.medinear.service.BillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping
    public ResponseEntity<BillResponseDto> generateBill(
            @Valid @RequestBody BillRequestDto request) {

        return ResponseEntity.ok(
                billService.generateBill(request));
    }


    @GetMapping("/{id}")
    public ResponseEntity<BillResponseDto> getBillById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                billService.getBillById(id));
    }

    @GetMapping("/number/{billNumber}")
    public ResponseEntity<BillResponseDto> getBillByBillNumber(
            @PathVariable String billNumber) {

        return ResponseEntity.ok(
                billService.getBillByBillNumber(billNumber));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BillResponseDto>> getBillsByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                billService.getBillsByUser(userId));
    }

    @GetMapping("/pharmacy/{pharmacyId}")
    public ResponseEntity<List<BillResponseDto>> getBillsByPharmacy(
            @PathVariable Long pharmacyId) {

        return ResponseEntity.ok(
                billService.getBillsByPharmacy(pharmacyId));
    }

    @GetMapping
    public List<BillResponseDto> getAllBills() {
        return billService.getAllBills();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<BillResponseDto> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody java.util.Map<String, String> request) {

        com.medinear.medinear.enums.OrderStatus status =
                com.medinear.medinear.enums.OrderStatus.valueOf(
                        request.get("status"));

        return ResponseEntity.ok(
                billService.updateOrderStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public void deleteBill(@PathVariable Long id) {

        billService.deleteBill(id);
    }
}