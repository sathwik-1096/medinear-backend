package com.medinear.medinear.repository;

import com.medinear.medinear.entity.Pharmacy;
import com.medinear.medinear.entity.User;
import com.medinear.medinear.enums.PharmacyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

    List<Pharmacy> findByOwner(User owner);

    List<Pharmacy> findByPharmacyNameContainingIgnoreCase(String pharmacyName);

    boolean existsByLicenseNumber(String licenseNumber);

    List<Pharmacy> findByStatus(PharmacyStatus status);
}