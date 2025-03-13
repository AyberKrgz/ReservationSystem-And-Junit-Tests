package com.example.reservation;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reservation {
    private String customerName;
    private LocalDateTime dateTime;
    private String roomNumber;

    public Reservation(String customerName, LocalDateTime dateTime, String roomNumber) {
        this.customerName = customerName;
        this.dateTime = dateTime;
        this.roomNumber = roomNumber;
    }

    //Return functions
    public String getCustomerName() { return customerName; }
    public LocalDateTime getDateTime() { return dateTime; }
    public String getroomNumber() { return roomNumber; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(customerName, that.customerName) &&
                Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(roomNumber, that.roomNumber);
    }

    //Not used yet. Might be useful in the future.
    @Override
    public int hashCode() {
        return Objects.hash(customerName, dateTime, roomNumber);
    }

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
