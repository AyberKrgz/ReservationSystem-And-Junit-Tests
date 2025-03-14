package com.example.reservation;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reservation {
    private String customerName;
    private LocalDateTime dateTime;
    private Integer roomNumber;

    public Reservation(String customerName, LocalDateTime dateTime, Integer roomNumber) {
        this.customerName = customerName;
        this.dateTime = dateTime;
        this.roomNumber = roomNumber;
    }

    //Return functions
    public String getCustomerName() { return customerName; }
    public LocalDateTime getDateTime() { return dateTime; }
    public Integer getRoomNumber() { return roomNumber; }

    //toString func to print reservations as we want.
    @Override
    public String toString() {
        return ("Reservation {" +
                "customerName='" + customerName + '\'' +
                ", dateTime=" + dateTime +
                ", roomNumber='" + roomNumber + '\'' +
                "}");
    }
}
