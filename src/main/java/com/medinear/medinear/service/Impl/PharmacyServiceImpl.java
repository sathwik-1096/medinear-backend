package com.medinear.medinear.service.Impl;

import com.medinear.medinear.dto.NearbyPharmacyResponseDto;
import com.medinear.medinear.entity.Pharmacy;
import com.medinear.medinear.entity.User;
import com.medinear.medinear.enums.PharmacyStatus;
import com.medinear.medinear.repository.PharmacyRepository;
import com.medinear.medinear.service.PharmacyService;
import org.springframework.stereotype.Service;
import com.medinear.medinear.enums.Role;
import com.medinear.medinear.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;

    private final UserRepository userRepository;

    public PharmacyServiceImpl(
            PharmacyRepository pharmacyRepository,
            UserRepository userRepository) {

        this.pharmacyRepository = pharmacyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Pharmacy addPharmacy(Pharmacy pharmacy) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        System.out.println("AUTHENTICATED USER = " + email);

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Owner not found"));

        System.out.println("OWNER ID = " + owner.getId());
        System.out.println("OWNER ROLE = " + owner.getRole());

        if (owner.getRole() != Role.OWNER) {
            throw new RuntimeException(
                    "Only pharmacy owners can add a pharmacy"
            );
        }

        if (pharmacyRepository.existsByLicenseNumber(
                pharmacy.getLicenseNumber())) {

            throw new RuntimeException(
                    "License number already exists");
        }

        pharmacy.setOwner(owner);
        pharmacy.setStatus(PharmacyStatus.PENDING);

        return pharmacyRepository.save(pharmacy);
    }

    @Override
    public Pharmacy updatePharmacy(Long id, Pharmacy pharmacy) {

        Pharmacy existingPharmacy = pharmacyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Pharmacy not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User loggedInUser = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Check ownership
        if (loggedInUser.getRole() != Role.ADMIN &&
                !existingPharmacy.getOwner().getId()
                        .equals(loggedInUser.getId())) {

            throw new RuntimeException(
                    "You are not allowed to update this pharmacy");
        }

        existingPharmacy.setPharmacyName(pharmacy.getPharmacyName());
        existingPharmacy.setPhoneNumber(pharmacy.getPhoneNumber());
        existingPharmacy.setEmail(pharmacy.getEmail());
        existingPharmacy.setAddress(pharmacy.getAddress());
        existingPharmacy.setCity(pharmacy.getCity());
        existingPharmacy.setState(pharmacy.getState());
        existingPharmacy.setPincode(pharmacy.getPincode());
        existingPharmacy.setLatitude(pharmacy.getLatitude());
        existingPharmacy.setLongitude(pharmacy.getLongitude());
        existingPharmacy.setOpeningTime(pharmacy.getOpeningTime());
        existingPharmacy.setClosingTime(pharmacy.getClosingTime());

        if (loggedInUser.getRole() == Role.ADMIN) {

            if (!existingPharmacy.getLicenseNumber()
                    .equals(pharmacy.getLicenseNumber())) {

                existingPharmacy.setLicenseNumber(
                        pharmacy.getLicenseNumber());

                existingPharmacy.setStatus(PharmacyStatus.PENDING);
            }
        }


        return pharmacyRepository.save(existingPharmacy);
    }

    @Override
    public void deletePharmacy(Long id) {

        Pharmacy pharmacy = pharmacyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Pharmacy not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User loggedInUser = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!pharmacy.getOwner().getId()
                .equals(loggedInUser.getId())) {

            throw new RuntimeException(
                    "You are not allowed to delete this pharmacy");
        }

        pharmacyRepository.delete(pharmacy);
    }

    @Override
    public Pharmacy verifyLicense(Long pharmacyId) {

        Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId)
                .orElseThrow(() ->
                        new RuntimeException("Pharmacy not found"));

        pharmacy.setStatus(PharmacyStatus.APPROVED);

        return pharmacyRepository.save(pharmacy);
    }

    @Override
    public Pharmacy rejectLicense(Long pharmacyId) {
        Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId)
                .orElseThrow(() ->
                        new RuntimeException("Pharmacy not found"));

        pharmacy.setStatus(PharmacyStatus.REJECTED);
        return pharmacyRepository.save(pharmacy);
    }

    @Override
    public Pharmacy getPharmacyById(Long id) {
        return pharmacyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Pharmacy not found"));
    }

    @Override
    public List<Pharmacy> getAllPharmacies() {
        return pharmacyRepository.findAll();
    }

    @Override
    public List<Pharmacy> getPharmaciesByOwner(User owner) {
        return pharmacyRepository.findByOwner(owner);
    }

    @Override
    public List<Pharmacy> searchPharmacyByName(String pharmacyName) {
        return pharmacyRepository
                .findByPharmacyNameContainingIgnoreCase(pharmacyName);
    }

    @Override
    public List<NearbyPharmacyResponseDto> findNearbyPharmacies(
            double latitude,
            double longitude,
            double radiusInKm) {

        List<Pharmacy> pharmacies = pharmacyRepository.findAll();

        List<NearbyPharmacyResponseDto> nearbyPharmacies =
                new ArrayList<>();

        for (Pharmacy pharmacy : pharmacies) {

            if (pharmacy.getStatus() != PharmacyStatus.APPROVED) {
                continue;
            }

            double distance = calculateDistance(
                    latitude,
                    longitude,
                    pharmacy.getLatitude(),
                    pharmacy.getLongitude()
            );

            if (distance <= radiusInKm) {

                NearbyPharmacyResponseDto dto =
                        new NearbyPharmacyResponseDto();

                dto.setId(pharmacy.getId());
                dto.setPharmacyName(pharmacy.getPharmacyName());
                dto.setAddress(pharmacy.getAddress());
                dto.setCity(pharmacy.getCity());
                dto.setState(pharmacy.getState());
                dto.setPincode(pharmacy.getPincode());
                dto.setPhoneNumber(pharmacy.getPhoneNumber());
                dto.setLatitude(pharmacy.getLatitude());
                dto.setLongitude(pharmacy.getLongitude());

                dto.setDistanceInKm(
                        Math.round(distance * 100.0) / 100.0
                );

                nearbyPharmacies.add(dto);
            }
        }

        // Closest pharmacy first
        nearbyPharmacies.sort(
                Comparator.comparing(
                        NearbyPharmacyResponseDto::getDistanceInKm
                )
        );

        return nearbyPharmacies;
    }

    private double calculateDistance(
            double lat1,
            double lon1,
            double lat2,
            double lon2) {

        final double EARTH_RADIUS_KM = 6371.0;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a =
                Math.sin(latDistance / 2)
                        * Math.sin(latDistance / 2)
                        +
                        Math.cos(Math.toRadians(lat1))
                                * Math.cos(Math.toRadians(lat2))
                                * Math.sin(lonDistance / 2)
                                * Math.sin(lonDistance / 2);

        double c =
                2 * Math.atan2(
                        Math.sqrt(a),
                        Math.sqrt(1 - a)
                );

        return EARTH_RADIUS_KM * c;
    }

    @Override
    public List<Pharmacy> getPendingPharmacies() {
        return pharmacyRepository.findByStatus(PharmacyStatus.PENDING);
    }
}