package com.medinear.medinear.service;

import com.medinear.medinear.dto.AdminOrderStatsDto;

import java.time.LocalDate;

public interface AdminOrderService {

    AdminOrderStatsDto getOrderStats(
            LocalDate from,
            LocalDate to
    );
}