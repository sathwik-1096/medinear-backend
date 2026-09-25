package com.medinear.medinear.controller;

import com.medinear.medinear.entity.Bill;
import com.medinear.medinear.entity.BillItem;
import com.medinear.medinear.entity.Medicine;
import com.medinear.medinear.service.BillItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bill-items")
public class BillItemController {

    private final BillItemService billItemService;

    @Autowired
    public BillItemController(BillItemService billItemService) {
        this.billItemService = billItemService;
    }

    @PostMapping
    public BillItem addBillItem(@RequestBody BillItem billItem) {
        return billItemService.addBillItem(billItem);
    }

    @GetMapping("/bill")
    public List<BillItem> getBillItemsByBill(@RequestBody Bill bill) {
        return billItemService.getBillItemsByBill(bill);
    }

    @GetMapping("/medicine")
    public List<BillItem> getBillItemsByMedicine(@RequestBody Medicine medicine) {
        return billItemService.getBillItemsByMedicine(medicine);
    }
}