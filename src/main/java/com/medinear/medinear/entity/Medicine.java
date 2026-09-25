package com.medinear.medinear.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.medinear.medinear.entity.PharmacyInventory;
import com.medinear.medinear.entity.BillItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(
        name = "medicines",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_medicine_manufacturer_strength",
                        columnNames = {
                                "medicine_name",
                                "manufacturer",
                                "strength"
                        }
                )
        }
)
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String medicineName;

    @Column(nullable = false, length = 100)
    private String manufacturer;

    @Column(length = 50)
    private String strength;

    @Column(length = 100)
    private String category;

    // Available in different pharmacies
    @OneToMany(mappedBy = "medicine",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PharmacyInventory> inventory = new ArrayList<>();

    // Sold in different bills
    @OneToMany(mappedBy = "medicine",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JsonIgnore
    private List<BillItem> billItems = new ArrayList<>();

    public Medicine() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<PharmacyInventory> getInventory() {
        return inventory;
    }

    public void setInventory(List<PharmacyInventory> inventory) {
        this.inventory = inventory;
    }

    public List<BillItem> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItem> billItems) {
        this.billItems = billItems;
    }
}