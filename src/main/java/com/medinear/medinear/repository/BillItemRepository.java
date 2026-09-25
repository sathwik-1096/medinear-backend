package com.medinear.medinear.repository;

import com.medinear.medinear.entity.Bill;
import com.medinear.medinear.entity.BillItem;
import com.medinear.medinear.entity.Medicine;
import com.medinear.medinear.entity.PharmacyInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillItemRepository extends JpaRepository<BillItem, Long> {

    List<BillItem> findByBill(Bill bill);

    List<BillItem> findByMedicine(Medicine medicine);

    List<BillItem> findByInventory(PharmacyInventory inventory);
}