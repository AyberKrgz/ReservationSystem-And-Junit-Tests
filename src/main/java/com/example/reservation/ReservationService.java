package com.example.reservation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private final List<Reservation> reservations = new ArrayList<>();

    public boolean addReservation(String customerName, LocalDateTime dateTime, String tableId) {
        Reservation newReservation = new Reservation(customerName, dateTime, tableId);

        // Çakışma kontrolü
        for (Reservation res : reservations) {
            if (res.getTableId().equals(tableId) && res.getDateTime().equals(dateTime)) {
                return false; // Çakışan rezervasyon var
            }
        }
        reservations.add(newReservation);
        return true;
    }

    public boolean cancelReservation(String customerName, LocalDateTime dateTime, String tableId) {
        return reservations.removeIf(res -> res.getCustomerName().equals(customerName) &&
                res.getDateTime().equals(dateTime) &&
                res.getTableId().equals(tableId));
    }

    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }
}
