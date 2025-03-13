package com.example.reservation;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reservation {
    private String customerName;
    private LocalDateTime dateTime;
    private String tableId; // veya oda numarası

    public Reservation(String customerName, LocalDateTime dateTime, String tableId) {
        this.customerName = customerName;
        this.dateTime = dateTime;
        this.tableId = tableId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getTableId() {
        return tableId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(customerName, that.customerName) &&
                Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(tableId, that.tableId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerName, dateTime, tableId);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "customerName='" + customerName + '\'' +
                ", dateTime=" + dateTime +
                ", tableId='" + tableId + '\'' +
                '}';
    }
}
