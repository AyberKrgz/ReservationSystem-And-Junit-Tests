package com.example.reservation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private final List<Reservation> reservations = new ArrayList<>();       //List of reservations

    public boolean addReservation(String customerName, LocalDateTime dateTime, String roomNumber) {
        Reservation newReservation = new Reservation(customerName, dateTime, roomNumber);

        // Çakışma kontrolü
        for (Reservation res : reservations) {
            if (res.getroomNumber().equals(roomNumber) && res.getDateTime().equals(dateTime)) {
                return false; // Çakışan rezervasyon var
            }
        }
        reservations.add(newReservation);   // Çakışma yok. rezervasyon ekle.
        return true;
    }

    public boolean cancelReservation(String customerName, LocalDateTime dateTime, String roomNumber) {
        return reservations.removeIf(res -> res.getCustomerName().equals(customerName) &&
                res.getDateTime().equals(dateTime) &&
                res.getroomNumber().equals(roomNumber));
    }

    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }
}
