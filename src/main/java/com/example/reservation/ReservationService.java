package com.example.reservation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private final List<Reservation> reservations = new ArrayList<>();       //List of reservations

    public boolean addReservation(String customerName, LocalDateTime dateTime, Integer roomNumber) {

        Reservation newReservation = new Reservation(customerName, dateTime, roomNumber);

        //Checking if all fields are filled.
        if (customerName == null || dateTime == null || roomNumber == null) {
            throw new IllegalArgumentException("Name, Date-Time and/or Room Number fields must be filled.");
        }

        //Reservations for past dates cannot be made.
        if (dateTime.isBefore(LocalDateTime.now())) {
            return false;
        }

        //Customers can only select room numbers between 101-199 (101 and 199 included).
        if (roomNumber<100 || roomNumber>199){
            throw new IndexOutOfBoundsException("Room number must be selected between 101-199 (101 and 199 included).");
        }

        //Checking conflict
        for (Reservation res : reservations) {
            if (res.getRoomNumber().equals(roomNumber) && res.getDateTime().equals(dateTime)) {     //Checking the conflict
                return false; //Conflict. Not added to the list.
            }
        }

        reservations.add(newReservation);   //No conflict. Add the reservation
        return true;

    }

    //Remove the reservation from the list.
    public boolean cancelReservation(String customerName, LocalDateTime dateTime, Integer roomNumber) {
        return reservations.removeIf(res -> res.getCustomerName().equals(customerName) &&
                                            res.getDateTime().equals(dateTime) &&
                                            res.getRoomNumber().equals(roomNumber));

    }

    public Reservation findReservation(String name, Integer room) {
        for (Reservation reservation : reservations) {
            if (reservation.getCustomerName().equals(name) && reservation.getRoomNumber().equals(room)) {
                return reservation;
            }
        }
        return null; //If no reservations found return null.
    }


    public List<Reservation> getAllReservations() { return new ArrayList<>(reservations); }

}
