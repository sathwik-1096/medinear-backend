package com.medinear.medinear.entity;
import com.medinear.medinear.entity.Bill;
import com.medinear.medinear.entity.Medicine;
import com.medinear.medinear.enums.UnitType;
import jakarta.persistence.*;

@Entity
@Table(name = "bill_items")
public class BillItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Bill to which this item belongs
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    // Medicine sold
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    private PharmacyInventory inventory;

    // Quantity sold
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    // Unit (TABLET, SHEET, BOTTLE, etc.)
    @Enumerated(EnumType.STRING)
    @Column(name = "unit", nullable = false)
    private UnitType unit;

    // Price of one unit at the time of billing
    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    // Total price for this medicine
    @Column(name = "subtotal", nullable = false)
    private Double subtotal;

    public BillItem() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public UnitType getUnit() {
        return unit;
    }

    public void setUnit(UnitType unit) {
        this.unit = unit;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @PrePersist
    @PreUpdate
    public void calculateSubtotal() {
        if (quantity != null && unitPrice != null) {
            subtotal = quantity * unitPrice;
        }
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public PharmacyInventory getInventory() {
        return inventory;
    }

    public void setInventory(PharmacyInventory inventory) {
        this.inventory = inventory;
    }

}
