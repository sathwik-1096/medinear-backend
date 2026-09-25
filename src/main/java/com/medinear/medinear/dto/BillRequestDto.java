package com.medinear.medinear.dto;

import com.medinear.medinear.enums.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public class BillRequestDto {

    @NotNull(message = "Customer id is required")
    private Long customerId;

    @NotNull(message = "Pharmacy id is required")
    private Long pharmacyId;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @PositiveOrZero(message = "Discount cannot be negative")
    private Double discount;

    @PositiveOrZero(message = "Tax cannot be negative")
    private Double tax;

    @Valid
    @NotEmpty(message = "Bill must contain at least one medicine")
    private List<BillItemRequestDto> items;

    public BillRequestDto() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public List<BillItemRequestDto> getItems() {
        return items;
    }

    public void setItems(List<BillItemRequestDto> items) {
        this.items = items;
    }
}
