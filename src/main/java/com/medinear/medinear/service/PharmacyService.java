package com.medinear.medinear.service;

import com.medinear.medinear.dto.NearbyPharmacyResponseDto;
import com.medinear.medinear.entity.Pharmacy;
import com.medinear.medinear.entity.User;

import java.util.List;

public interface PharmacyService {

    Pharmacy addPharmacy(Pharmacy pharmacy);

    Pharmacy updatePharmacy(Long id, Pharmacy pharmacy);

    void deletePharmacy(Long id);

    Pharmacy getPharmacyById(Long id);

    Pharmacy verifyLicense(Long pharmacyId);
    Pharmacy rejectLicense(Long pharmacyId);

    List<Pharmacy> getAllPharmacies();

    List<Pharmacy> getPharmaciesByOwner(User owner);

    List<Pharmacy> searchPharmacyByName(String pharmacyName);

    List<NearbyPharmacyResponseDto> findNearbyPharmacies(
            double latitude,
            double longitude,
            double radiusInKm);

    List<Pharmacy> getPendingPharmacies();
}