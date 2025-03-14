package com.example.reservation;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {
    private String customerName;
    private LocalDate date;
    private Integer roomNumber;

    public Reservation(String customerName, LocalDate date, Integer roomNumber) {
        this.customerName = customerName;
        this.date = date;
        this.roomNumber = roomNumber;
    }

    //Return functions
    public String getCustomerName() { return customerName; }
    public LocalDate getDateTime() { return date; }
    public Integer getRoomNumber() { return roomNumber; }

    //toString func to print reservations as we want.
    @Override
    public String toString() {
        return ("Reservation {" +
                "customerName='" + customerName + '\'' +
                ", dateTime=" + date +
                ", roomNumber='" + roomNumber + '\'' +
                "}");
    }
}
