package com.medinear.medinear.repository;

import com.medinear.medinear.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    List<Medicine> findByMedicineNameIgnoreCase(String medicineName);

    List<Medicine> findByMedicineNameContainingIgnoreCase(String medicineName);

    boolean existsByMedicineNameAndManufacturerAndStrength(
            String medicineName,
            String manufacturer,
            String strength);

}