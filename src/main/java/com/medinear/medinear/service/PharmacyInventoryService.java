package com.medinear.medinear.service;

import com.medinear.medinear.entity.Medicine;
import com.medinear.medinear.entity.Pharmacy;
import com.medinear.medinear.entity.PharmacyInventory;

import java.util.List;
import java.util.Optional;

public interface PharmacyInventoryService {

    PharmacyInventory addInventory(PharmacyInventory inventory);

    PharmacyInventory updateInventory(Long id, PharmacyInventory inventory);

    void deleteInventory(Long id);

    Optional<PharmacyInventory> getInventoryById(Long id);

    List<PharmacyInventory> getInventoryByPharmacy(Long pharmacyId);

    List<PharmacyInventory> getInventoryByMedicine(Long medicineId);

    List<PharmacyInventory> getInventoryByPharmacyAndMedicine(
            Long pharmacy,
            Long medicine
    );

    List<PharmacyInventory> searchInventoryByMedicineName(
            String medicineName);
}
