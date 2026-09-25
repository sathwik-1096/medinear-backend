package com.medinear.medinear.service;

import com.medinear.medinear.entity.Bill;
import com.medinear.medinear.entity.BillItem;
import com.medinear.medinear.entity.Medicine;

import java.util.List;

public interface BillItemService {

    BillItem addBillItem(BillItem billItem);

    List<BillItem> getBillItemsByBill(Bill bill);

    List<BillItem> getBillItemsByMedicine(Medicine medicine);
}