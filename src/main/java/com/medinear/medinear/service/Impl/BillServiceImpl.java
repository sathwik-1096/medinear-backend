package com.medinear.medinear.service.Impl;

import com.medinear.medinear.dto.BillItemRequestDto;
import com.medinear.medinear.dto.BillItemResponseDto;
import com.medinear.medinear.dto.BillRequestDto;
import com.medinear.medinear.dto.BillResponseDto;
import com.medinear.medinear.entity.*;
import com.medinear.medinear.enums.BillStatus;
import com.medinear.medinear.exception.BadRequestException;
import com.medinear.medinear.exception.ResourceNotFoundException;
import com.medinear.medinear.mapper.BillMapper;
import com.medinear.medinear.repository.*;
import com.medinear.medinear.service.BillService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final PharmacyRepository pharmacyRepository;
    private final MedicineRepository medicineRepository;
    private final PharmacyInventoryRepository pharmacyInventoryRepository;

    public BillServiceImpl(BillRepository billRepository,
                           UserRepository userRepository,
                           PharmacyRepository pharmacyRepository,
                           MedicineRepository medicineRepository,
                           PharmacyInventoryRepository pharmacyInventoryRepository) {

        this.billRepository = billRepository;
        this.userRepository = userRepository;
        this.pharmacyRepository = pharmacyRepository;
        this.medicineRepository = medicineRepository;
        this.pharmacyInventoryRepository = pharmacyInventoryRepository;
    }
    // Implement methods here

    @Override
    public Bill createBill(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    public BillResponseDto getBillById(Long id) {

        Bill bill = billRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bill not found"));
        // Convert Bill -> BillResponseDto
        return BillMapper.toResponseDto(bill);
    }

    @Override
    public BillResponseDto getBillByBillNumber(String billNumber) {

        Bill bill = billRepository.findByBillNumber(billNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bill not found"));

        return BillMapper.toResponseDto(bill);
    }

    @Override
    public List<BillResponseDto> getBillsByUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        List<Bill> bills = billRepository.findByUser(user);

        List<BillResponseDto> responses = new ArrayList<>();

        for (Bill bill : bills) {
            responses.add(BillMapper.toResponseDto(bill));
        }

        return responses;
    }

    @Override
    public List<BillResponseDto> getBillsByPharmacy(Long pharmacyId) {

        Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pharmacy not found"));

        List<Bill> bills = billRepository.findByPharmacy(pharmacy);

        List<BillResponseDto> responses = new ArrayList<>();

        for (Bill bill : bills) {
            responses.add(BillMapper.toResponseDto(bill));
        }

        return responses;
    }

    @Override
    public List<BillResponseDto> getAllBills() {

        List<Bill> bills = billRepository.findAll();

        List<BillResponseDto> responses = new ArrayList<>();

        for (Bill bill : bills) {
            responses.add(BillMapper.toResponseDto(bill));
        }

        return responses;
    }

    @Override
    @Transactional
    public BillResponseDto generateBill(BillRequestDto request) {

        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Pharmacy pharmacy = pharmacyRepository.findById(request.getPharmacyId())
                .orElseThrow(() -> new ResourceNotFoundException("Pharmacy not found"));

        Bill bill = new Bill();

        bill.setUser(customer);
        bill.setPharmacy(pharmacy);
        bill.setPaymentMethod(request.getPaymentMethod());
        bill.setBillStatus(BillStatus.PAID);

        bill.setBillItems(new ArrayList<>());
        // ↓↓↓ Write it here ↓↓↓

        double totalAmount = 0.0;

        for (BillItemRequestDto itemRequest : request.getItems()) {

            // Find medicine
            Medicine medicine = medicineRepository.findById(itemRequest.getMedicineId())
                    .orElseThrow(() -> new ResourceNotFoundException("Medicine not found"));

            // Find medicine in pharmacy inventory
            PharmacyInventory inventory = pharmacyInventoryRepository
                    .findByPharmacyAndMedicine(pharmacy, medicine)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Medicine not available in this pharmacy"));

            // Check stock
            if (itemRequest.getQuantity() == null ||
                    itemRequest.getQuantity() <= 0) {

                throw new BadRequestException(
                        "Quantity must be greater than zero");
            }

            if (inventory.getAvailableQuantity() < itemRequest.getQuantity()) {
                throw new BadRequestException(
                        medicine.getMedicineName() + " is out of stock or insufficient quantity."
                );
            }

            // Create BillItem
            BillItem billItem = new BillItem();

            billItem.setBill(bill);
            billItem.setMedicine(medicine);
            billItem.setInventory(inventory);
            billItem.setQuantity(itemRequest.getQuantity());
            billItem.setUnit(inventory.getUnit());
            billItem.setUnitPrice(inventory.getPrice());

            // subtotal will be calculated automatically by @PrePersist
            bill.getBillItems().add(billItem);

            // Add to total
            totalAmount += inventory.getPrice() * itemRequest.getQuantity();

            // Reduce inventory
            inventory.setAvailableQuantity(
                    inventory.getAvailableQuantity() - itemRequest.getQuantity()
            );

            pharmacyInventoryRepository.save(inventory);
        }

        // More code will come here...
        bill.setTotalAmount(totalAmount);

        double discount = request.getDiscount() != null
                ? request.getDiscount()
                : 0.0;

        double tax = request.getTax() != null
                ? request.getTax()
                : 0.0;

        bill.setDiscount(discount);
        bill.setTax(tax);

        double finalAmount = totalAmount - discount + tax;

        if (finalAmount < 0) {
            throw new BadRequestException(
                    "Final amount cannot be negative"
            );
        }

        bill.setFinalAmount(finalAmount);

        bill.setBillNumber("MED-" + System.currentTimeMillis());

        Bill savedBill = billRepository.save(bill);

        return BillMapper.toResponseDto(savedBill);
    }
    @Override
    public BillResponseDto updateOrderStatus(
            Long id,
            com.medinear.medinear.enums.OrderStatus status) {

        Bill bill = billRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bill not found"));

        bill.setStatus(status);
        return BillMapper.toResponseDto(billRepository.save(bill));
    }

    @Override
    public void deleteBill(Long id) {


        Bill bill = billRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bill not found"));

        billRepository.delete(bill);


    }

}