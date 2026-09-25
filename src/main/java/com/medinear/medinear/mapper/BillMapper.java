package com.medinear.medinear.mapper;

import com.medinear.medinear.dto.BillItemResponseDto;
import com.medinear.medinear.dto.BillResponseDto;
import com.medinear.medinear.entity.Bill;
import com.medinear.medinear.entity.BillItem;

import java.util.ArrayList;
import java.util.List;

public class BillMapper {

    public static BillResponseDto toResponseDto(Bill bill) {

        BillResponseDto response = new BillResponseDto();

        response.setId(bill.getId());
        response.setBillNumber(bill.getBillNumber());

        if (bill.getUser() != null) {
            response.setCustomerId(bill.getUser().getId());
            response.setCustomerEmail(bill.getUser().getEmail());
        }

        if (bill.getPharmacy() != null) {
            response.setPharmacyId(bill.getPharmacy().getId());
            response.setPharmacyName(
                    bill.getPharmacy().getPharmacyName());
        }

        response.setBillDate(bill.getBillDate());
        response.setTotalAmount(bill.getTotalAmount());
        response.setDiscount(bill.getDiscount());
        response.setTax(bill.getTax());
        response.setFinalAmount(bill.getFinalAmount());
        response.setPaymentMethod(bill.getPaymentMethod());
        response.setStatus(bill.getStatus());
        response.setBillStatus(bill.getBillStatus());

        List<BillItemResponseDto> items = new ArrayList<>();

        for (BillItem billItem : bill.getBillItems()) {

            BillItemResponseDto item =
                    new BillItemResponseDto();

            item.setMedicineId(
                    billItem.getMedicine().getId());

            item.setMedicineName(
                    billItem.getMedicine().getMedicineName());

            item.setManufacturer(
                    billItem.getMedicine().getManufacturer());

            item.setQuantity(
                    billItem.getQuantity());

            item.setUnit(
                    billItem.getUnit());

            item.setUnitPrice(
                    billItem.getUnitPrice());

            item.setSubtotal(
                    billItem.getSubtotal());

            items.add(item);
        }

        response.setItems(items);

        return response;
    }
}