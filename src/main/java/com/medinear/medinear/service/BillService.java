package com.medinear.medinear.service;

import com.medinear.medinear.dto.BillRequestDto;
import com.medinear.medinear.dto.BillResponseDto;
import com.medinear.medinear.entity.Bill;

import java.util.List;

public interface BillService {

    Bill createBill(Bill bill);

    BillResponseDto getBillById(Long id);

    BillResponseDto getBillByBillNumber(String billNumber);

    List<BillResponseDto> getBillsByUser(Long userId);

    List<BillResponseDto> getBillsByPharmacy(Long pharmacyId);

    List<BillResponseDto> getAllBills();

    BillResponseDto generateBill(BillRequestDto request);

    BillResponseDto updateOrderStatus(Long id, com.medinear.medinear.enums.OrderStatus status);

    void deleteBill(Long id);


}