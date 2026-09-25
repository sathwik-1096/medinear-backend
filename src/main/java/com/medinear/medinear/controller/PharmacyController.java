package com.medinear.medinear.controller;

import com.medinear.medinear.dto.NearbyPharmacyResponseDto;
import com.medinear.medinear.entity.Pharmacy;
import com.medinear.medinear.entity.User;
import com.medinear.medinear.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacies")
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @Autowired
    public PharmacyController(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    @PostMapping
    public Pharmacy addPharmacy(@RequestBody Pharmacy pharmacy) {
        return pharmacyService.addPharmacy(pharmacy);
    }

    @GetMapping("/{id}")
    public Pharmacy getPharmacyById(@PathVariable Long id) {
        return pharmacyService.getPharmacyById(id);
    }

    @GetMapping
    public List<Pharmacy> getAllPharmacies() {
        return pharmacyService.getAllPharmacies();
    }

    @GetMapping("/search/{name}")
    public List<Pharmacy> searchPharmacyByName(@PathVariable String name) {
        return pharmacyService.searchPharmacyByName(name);
    }

    @PutMapping("/{id}")
    public Pharmacy updatePharmacy(@PathVariable Long id,
                                   @RequestBody Pharmacy pharmacy) {
        return pharmacyService.updatePharmacy(id, pharmacy);
    }

    @DeleteMapping("/{id}")
    public void deletePharmacy(@PathVariable Long id) {
        pharmacyService.deletePharmacy(id);
    }

    @PutMapping("/{id}/verify-license")
    public Pharmacy verifyLicense(@PathVariable Long id) {
        return pharmacyService.verifyLicense(id);
    }

    @GetMapping("/pending")
    public List<Pharmacy> getPendingPharmacies() {
        return pharmacyService.getPendingPharmacies();
    }

    @PutMapping("/{id}/reject-license")
    public Pharmacy rejectLicense(@PathVariable Long id) {
        return pharmacyService.rejectLicense(id);
    }

    @GetMapping("/nearby")
    public List<NearbyPharmacyResponseDto> getNearbyPharmacies(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5") double radius) {

        return pharmacyService.findNearbyPharmacies(
                latitude,
                longitude,
                radius
        );
    }
}