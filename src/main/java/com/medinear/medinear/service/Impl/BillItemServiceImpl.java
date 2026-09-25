package com.medinear.medinear.service.Impl;

import com.medinear.medinear.entity.Bill;
import com.medinear.medinear.entity.BillItem;
import com.medinear.medinear.entity.Medicine;
import com.medinear.medinear.repository.BillItemRepository;
import com.medinear.medinear.service.BillItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillItemServiceImpl implements BillItemService {

    private final BillItemRepository billItemRepository;

    public BillItemServiceImpl(BillItemRepository billItemRepository) {
        this.billItemRepository = billItemRepository;
    }

    // Implement methods here


    @Override
    public BillItem addBillItem(BillItem billItem) {
        return billItemRepository.save(billItem);
    }

    @Override
    public List<BillItem> getBillItemsByBill(Bill bill) {
        return billItemRepository.findByBill(bill);
    }

    @Override
    public List<BillItem> getBillItemsByMedicine(Medicine medicine) {
        return billItemRepository.findByMedicine(medicine);
    }
}