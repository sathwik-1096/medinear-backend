package com.medinear.medinear.service;

import com.medinear.medinear.entity.Medicine;

import java.util.List;
import java.util.Map;

public interface MedicineService {

    Medicine addMedicine(Medicine medicine);

    Medicine updateMedicine(Long id, Medicine medicine);

    void deleteMedicine(Long id);

    Medicine getMedicineById(Long id);

    List<Medicine> getMedicineByName(String medicineName);

    List<Medicine> getAllMedicines();

    List<Medicine> searchMedicines(String medicineName);
}
