package com.medinear.medinear.service.Impl;

import com.medinear.medinear.dto.AdminOrderStatsDto;
import com.medinear.medinear.enums.OrderStatus;
import com.medinear.medinear.repository.BillRepository;
import com.medinear.medinear.service.AdminOrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {

    private final BillRepository billRepository;

    public AdminOrderServiceImpl(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public AdminOrderStatsDto getOrderStats(
            LocalDate from,
            LocalDate to) {

        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.plusDays(1).atStartOfDay().minusNanos(1);

        long totalOrders =
                billRepository.countByBillDateBetween(start, end);

        long successfulOrders =
                billRepository.countByBillDateBetweenAndStatus(
                        start,
                        end,
                        OrderStatus.SUCCESSFUL
                );

        long cancelledOrders =
                billRepository.countByBillDateBetweenAndStatus(
                        start,
                        end,
                        OrderStatus.CANCELLED
                );

        long pendingOrders =
                billRepository.countByBillDateBetweenAndStatus(
                        start,
                        end,
                        OrderStatus.PENDING
                );

        AdminOrderStatsDto dto = new AdminOrderStatsDto();

        dto.setTotalOrders(totalOrders);
        dto.setSuccessfulOrders(successfulOrders);
        dto.setCancelledOrders(cancelledOrders);
        dto.setPendingOrders(pendingOrders);

        return dto;
    }
}