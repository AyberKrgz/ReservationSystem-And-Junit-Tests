package com.example.reservation;

import java.time.LocalDate;
import java.util.List;

public class ReservationConflictChecker {

    public boolean hasConflict(List<Reservation> reservations, int roomNumber, LocalDate date) {
        return reservations.stream()
                .anyMatch(res -> res.getRoomNumber().equals(roomNumber) &&
                        res.getDateTime().equals(date));
    }
}
