package com.medinear.medinear.repository;

import com.medinear.medinear.entity.Medicine;
import com.medinear.medinear.entity.Pharmacy;
import com.medinear.medinear.entity.PharmacyInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PharmacyInventoryRepository extends JpaRepository<PharmacyInventory, Long> {


    List<PharmacyInventory> findByPharmacy(Pharmacy pharmacy);

    List<PharmacyInventory> findByMedicine(Medicine medicine);

    List<PharmacyInventory>
    findByMedicine_MedicineNameContainingIgnoreCase(String medicineName);

    List<PharmacyInventory> findAllByPharmacyAndMedicine(
            Pharmacy pharmacy,
            Medicine medicine);

    Optional<PharmacyInventory>
    findByPharmacyAndMedicineAndManufacturer(
            Pharmacy pharmacy,
            Medicine medicine,
            String manufacturer
    );

    Optional<PharmacyInventory> findByPharmacyAndMedicine(
            Pharmacy pharmacy,
            Medicine medicine
    );
}
