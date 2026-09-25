package com.medinear.medinear.service.Impl;

import com.medinear.medinear.entity.Medicine;
import com.medinear.medinear.exception.ResourceNotFoundException;
import com.medinear.medinear.repository.MedicineRepository;
import com.medinear.medinear.service.MedicineService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;

    public MedicineServiceImpl(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    // Implement methods here

    @Override
    public Medicine addMedicine(Medicine medicine) {

        boolean exists =
                medicineRepository
                        .existsByMedicineNameAndManufacturerAndStrength(
                                medicine.getMedicineName(),
                                medicine.getManufacturer(),
                                medicine.getStrength()
                        );

        if (exists) {
            throw new RuntimeException(
                    "Medicine with same manufacturer and strength already exists");
        }

        return medicineRepository.save(medicine);
    }

    @Override
    public Medicine updateMedicine(
            Long id,
            Medicine medicine) {

        Medicine existingMedicine =
                medicineRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Medicine not found"));

        boolean duplicate =
                medicineRepository
                        .existsByMedicineNameAndManufacturerAndStrength(
                                medicine.getMedicineName(),
                                medicine.getManufacturer(),
                                medicine.getStrength()
                        );

        if (duplicate &&
                !(existingMedicine.getMedicineName()
                        .equalsIgnoreCase(
                                medicine.getMedicineName())
                        &&
                        existingMedicine.getManufacturer()
                                .equalsIgnoreCase(
                                        medicine.getManufacturer())
                        &&
                        java.util.Objects.equals(
                                existingMedicine.getStrength(),
                                medicine.getStrength()))) {

            throw new RuntimeException(
                    "Medicine with same manufacturer and strength already exists");
        }

        existingMedicine.setMedicineName(
                medicine.getMedicineName());

        existingMedicine.setManufacturer(
                medicine.getManufacturer());

        existingMedicine.setStrength(
                medicine.getStrength());

        existingMedicine.setCategory(
                medicine.getCategory());

        return medicineRepository.save(existingMedicine);
    }

    @Override
    public void deleteMedicine(Long id) {

        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() ->
                                new ResourceNotFoundException("Medicine not found"));

        medicineRepository.delete(medicine);
    }

    @Override
    public Medicine getMedicineById(Long id) {
        return medicineRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Medicine not found"));
    }

    @Override
    public List<Medicine> getMedicineByName(String medicineName) {
        return medicineRepository.findByMedicineNameIgnoreCase(medicineName);
    }

    @Override
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    @Override
    public List<Medicine> searchMedicines(String medicineName) {
        return medicineRepository.findByMedicineNameContainingIgnoreCase(medicineName);
    }
}