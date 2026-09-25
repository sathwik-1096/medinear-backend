package com.medinear.medinear.dto;

import com.medinear.medinear.enums.PaymentMethod;

import java.time.LocalDateTime;
import java.util.List;

public class BillResponseDto {

    private Long id;
    private String billNumber;

    private Long customerId;
    private String customerEmail;

    private Long pharmacyId;
    private String pharmacyName;

    private LocalDateTime billDate;

    private Double totalAmount;
    private Double discount;
    private Double tax;
    private Double finalAmount;

    private PaymentMethod paymentMethod;

    private com.medinear.medinear.enums.OrderStatus status;
    private com.medinear.medinear.enums.BillStatus billStatus;

    private List<BillItemResponseDto> items;

    public BillResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public LocalDateTime getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDateTime billDate) {
        this.billDate = billDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
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

    public Double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(Double finalAmount) {
        this.finalAmount = finalAmount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public com.medinear.medinear.enums.OrderStatus getStatus() {
        return status;
    }

    public void setStatus(com.medinear.medinear.enums.OrderStatus status) {
        this.status = status;
    }

    public com.medinear.medinear.enums.BillStatus getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(com.medinear.medinear.enums.BillStatus billStatus) {
        this.billStatus = billStatus;
    }

    public List<BillItemResponseDto> getItems() {
        return items;
    }

    public void setItems(List<BillItemResponseDto> items) {
        this.items = items;
    }
}