package com.medinear.medinear.dto;

public class AdminOrderStatsDto {

    private long totalOrders;
    private long successfulOrders;
    private long cancelledOrders;
    private long pendingOrders;

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public long getSuccessfulOrders() {
        return successfulOrders;
    }

    public void setSuccessfulOrders(long successfulOrders) {
        this.successfulOrders = successfulOrders;
    }

    public long getCancelledOrders() {
        return cancelledOrders;
    }

    public void setCancelledOrders(long cancelledOrders) {
        this.cancelledOrders = cancelledOrders;
    }

    public long getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(long pendingOrders) {
        this.pendingOrders = pendingOrders;
    }
}