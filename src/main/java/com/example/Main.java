package com.example;

import com.example.reservation.ReservationService;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        ReservationService reservationService = new ReservationService();

        // Yeni rezervasyon ekleme
        boolean added = reservationService.addReservation("Ayberk", LocalDateTime.of(2025, 3, 15, 19, 0), "Table1");
        System.out.println("Rezervasyon eklendi mi? " + added);

        // Aynı zaman ve masaya çakışan rezervasyon ekleme
        boolean conflict = reservationService.addReservation("Ali", LocalDateTime.of(2025, 3, 15, 19, 0), "Table1");
        System.out.println("Çakışan rezervasyon eklendi mi? " + conflict);

        // Rezervasyonları listeleme
        System.out.println("Mevcut rezervasyonlar: " + reservationService.getAllReservations());

        // Rezervasyon iptali
        boolean cancelled = reservationService.cancelReservation("Ayberk", LocalDateTime.of(2025, 3, 15, 19, 0), "Table1");
        System.out.println("Rezervasyon iptal edildi mi? " + cancelled);

        // Son durumda rezervasyonları listeleme
        System.out.println("Son rezervasyon listesi: " + reservationService.getAllReservations());
    }
}
