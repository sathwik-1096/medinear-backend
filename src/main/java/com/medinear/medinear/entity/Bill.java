package com.medinear.medinear.entity;
import com.medinear.medinear.entity.User;
import com.medinear.medinear.entity.BillItem;
import com.medinear.medinear.entity.Pharmacy;
import com.medinear.medinear.enums.BillStatus;
import com.medinear.medinear.enums.OrderStatus;
import com.medinear.medinear.enums.PaymentMethod;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Bill Number (e.g., BILL-10001)
    @Column(name = "bill_number", nullable = false, unique = true, length = 30)
    private String billNumber;

    // Customer (nullable for walk-in customers)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Pharmacy that generated the bill
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pharmacy_id", nullable = false)
    private Pharmacy pharmacy;

    @Column(name = "bill_date", nullable = false)
    private LocalDateTime billDate;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "tax")
    private Double tax;

    @Column(name = "final_amount", nullable = false)
    private Double finalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode", nullable = false)
    private PaymentMethod paymentMethod;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    // Medicines in this bill
    @OneToMany(mappedBy = "bill",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<BillItem> billItems = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.billDate = LocalDateTime.now();

        if (status == null) {
            status = OrderStatus.PENDING;
        }
    }


    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private BillStatus billStatus;


    public Bill() {
    }

    // Getters and Setters

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<BillItem> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItem> billItems) {
        this.billItems = billItems;
    }

    public BillStatus getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(BillStatus billStatus) {
        this.billStatus = billStatus;
    }

}
