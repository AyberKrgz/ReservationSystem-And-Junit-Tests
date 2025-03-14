package com.example.reservation;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {
    private String customerName;
    private LocalDate date;
    private Integer roomNumber;
    private Integer guestCount;

    public Reservation(String customerName, LocalDate date, Integer roomNumber, Integer guestCount) {
        this.customerName = customerName;
        this.date = date;
        this.roomNumber = roomNumber;
        this.guestCount = guestCount;
    }

    //Return functions
    public String getCustomerName() { return customerName; }
    public LocalDate getDateTime() { return date; }
    public Integer getRoomNumber() { return roomNumber; }
    public Integer getGuestCount() { return guestCount; }

    //toString func to print reservations as we want.
    @Override
    public String toString() {
        return ("Reservation {" +
                "customerName='" + customerName + '\'' +
                ", date="        + date +
                ", roomNumber='" + roomNumber + '\'' +
                ", guestCount='" + guestCount + '\'' +
                "}");
    }
}
