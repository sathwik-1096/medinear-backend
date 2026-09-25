package com.medinear.medinear.controller;

import com.medinear.medinear.dto.PharmacyInventoryMapper;
import com.medinear.medinear.dto.PharmacyInventoryResponseDto;
import com.medinear.medinear.entity.PharmacyInventory;
import com.medinear.medinear.service.PharmacyInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventory")
public class PharmacyInventoryController {

    private final PharmacyInventoryService inventoryService;

    @Autowired
    public PharmacyInventoryController(
            PharmacyInventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public PharmacyInventory addInventory(
            @RequestBody PharmacyInventory inventory) {

        return inventoryService.addInventory(inventory);
    }

    @GetMapping("/{id}")
    public Optional<PharmacyInventoryResponseDto> getInventoryById(
            @PathVariable Long id) {

        return inventoryService.getInventoryById(id)
                .map(PharmacyInventoryMapper::toResponseDto);
    }

    @GetMapping("/pharmacy/{pharmacyId}")
    public List<PharmacyInventoryResponseDto> getInventoryByPharmacy(
            @PathVariable Long pharmacyId) {

        return inventoryService
                .getInventoryByPharmacy(pharmacyId)
                .stream()
                .map(PharmacyInventoryMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/medicine/{medicineId}")
    public List<PharmacyInventoryResponseDto> getInventoryByMedicine(
            @PathVariable Long medicineId) {

        return inventoryService
                .getInventoryByMedicine(medicineId)
                .stream()
                .map(PharmacyInventoryMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/pharmacy/{pharmacyId}/medicine/{medicineId}")
    public List<PharmacyInventoryResponseDto>
    getInventoryByPharmacyAndMedicine(
            @PathVariable Long pharmacyId,
            @PathVariable Long medicineId) {

        return inventoryService
                .getInventoryByPharmacyAndMedicine(
                        pharmacyId,
                        medicineId)
                .stream()
                .map(PharmacyInventoryMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public PharmacyInventory updateInventory(
            @PathVariable Long id,
            @RequestBody PharmacyInventory inventory) {

        return inventoryService.updateInventory(id, inventory);
    }

    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
    }

    @GetMapping("/search/{keyword}")
    public List<PharmacyInventoryResponseDto> searchInventory(
            @PathVariable String keyword) {

        return inventoryService
                .searchInventoryByMedicineName(keyword)
                .stream()
                .map(PharmacyInventoryMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}