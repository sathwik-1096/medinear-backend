package com.medinear.medinear.controller;

import com.medinear.medinear.entity.Medicine;
import com.medinear.medinear.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    @Autowired
    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @PostMapping
    public Medicine addMedicine(@RequestBody Medicine medicine) {
        return medicineService.addMedicine(medicine);
    }

    @GetMapping("/{id}")
    public Medicine getMedicineById(@PathVariable Long id) {
        return medicineService.getMedicineById(id);
    }

    @GetMapping("/name/{name}")
    public List<Medicine> getMedicineByName(@PathVariable String name) {
        return medicineService.getMedicineByName(name);
    }

    @GetMapping
    public List<Medicine> getAllMedicines() {
        return medicineService.getAllMedicines();
    }

    @GetMapping("/search/{keyword}")
    public List<Medicine> searchMedicines(@PathVariable String keyword) {
        return medicineService.searchMedicines(keyword);
    }

    @PutMapping("/{id}")
    public Medicine updateMedicine(@PathVariable Long id,
                                   @RequestBody Medicine medicine) {
        return medicineService.updateMedicine(id, medicine);
    }

    @DeleteMapping("/{id}")
    public void deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
    }
}