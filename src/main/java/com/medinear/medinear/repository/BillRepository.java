package com.medinear.medinear.repository;

import com.medinear.medinear.entity.Bill;
import com.medinear.medinear.entity.Pharmacy;
import com.medinear.medinear.entity.User;
import com.medinear.medinear.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findByUser(User user);

    List<Bill> findByPharmacy(Pharmacy pharmacy);

    Optional<Bill> findByBillNumber(String billNumber);

    boolean existsByBillNumber(String billNumber);

    long countByBillDateBetween(
            LocalDateTime start,
            LocalDateTime end
    );

    long countByBillDateBetweenAndStatus(
            LocalDateTime start,
            LocalDateTime end,
            OrderStatus status
    );
}