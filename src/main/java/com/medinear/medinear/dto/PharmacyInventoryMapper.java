package com.medinear.medinear.dto;

import com.medinear.medinear.entity.PharmacyInventory;

public class PharmacyInventoryMapper {

    public static PharmacyInventoryResponseDto toResponseDto(
            PharmacyInventory inventory) {

        PharmacyInventoryResponseDto response =
                new PharmacyInventoryResponseDto();

        response.setInventoryId(inventory.getId());

        response.setPharmacyId(
                inventory.getPharmacy().getId());

        response.setPharmacyName(
                inventory.getPharmacy().getPharmacyName());

        response.setMedicineId(
                inventory.getMedicine().getId());

        response.setMedicineName(
                inventory.getMedicine().getMedicineName());

        response.setManufacturer(
                inventory.getMedicine().getManufacturer());

        response.setStrength(
                inventory.getMedicine().getStrength());

        response.setCategory(
                inventory.getMedicine().getCategory());

        response.setAvailableQuantity(
                inventory.getAvailableQuantity());

        response.setUnit(inventory.getUnit());

        response.setPrice(inventory.getPrice());

        response.setBatchNumber(
                inventory.getBatchNumber());

        response.setExpiryDate(
                inventory.getExpiryDate());

        response.setMinimumStock(
                inventory.getMinimumStock());

        return response;
    }
}