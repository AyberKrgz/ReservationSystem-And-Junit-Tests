package com.example;

import com.example.reservation.ReservationService;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        ReservationService reservationService = new ReservationService();

        //Adding new reservations
        boolean added = reservationService.addReservation("Ayberk", LocalDateTime.of(2025, 3, 15, 19, 0), "07");
        System.out.println("Rezervasyon eklendi mi? ");
        System.out.println(added);

        //Trying to add a conflicting reservation
        boolean conflict = reservationService.addReservation("Ali", LocalDateTime.of(2025, 3, 15, 19, 0), "07");
        System.out.println("Çakışan rezervasyon eklendi mi? ");
        System.out.println(conflict);

        //Listing reservations
        System.out.println("Mevcut rezervasyonlar: ");
        System.out.println(reservationService.getAllReservations());

        //Canceling reservation
        boolean cancelled = reservationService.cancelReservation("Ayberk", LocalDateTime.of(2025, 3, 15, 19, 0), "07");
        System.out.println("Rezervasyon iptal edildi mi? ");
        System.out.println(cancelled);

        //Listing reservations again
        System.out.println("Son rezervasyon listesi: ");
        System.out.println(reservationService.getAllReservations());
    }
}
