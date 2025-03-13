package com.example.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService();
    }

    @Test
    void testAddReservation_Successful() {
        boolean result = reservationService.addReservation("Ayberk", LocalDateTime.of(2025, 3, 15, 19, 0), "Table1");
        assertTrue(result, "Rezervasyon başarıyla eklenmeli.");
    }

    @Test
    void testAddReservation_Fail_DueToConflict() {
        reservationService.addReservation("Ayberk", LocalDateTime.of(2025, 3, 15, 19, 0), "Table1");
        boolean result = reservationService.addReservation("Ali", LocalDateTime.of(2025, 3, 15, 19, 0), "Table1");
        assertFalse(result, "Çakışan rezervasyon eklenmemeli.");
    }

    @Test
    void testCancelReservation_Successful() {
        reservationService.addReservation("Ayberk", LocalDateTime.of(2025, 3, 15, 19, 0), "Table1");
        boolean result = reservationService.cancelReservation("Ayberk", LocalDateTime.of(2025, 3, 15, 19, 0), "Table1");
        assertTrue(result, "Rezervasyon başarıyla iptal edilmeli.");
    }

    @Test
    void testCancelReservation_Fail_NotFound() {
        boolean result = reservationService.cancelReservation("Ali", LocalDateTime.of(2025, 3, 15, 19, 0), "Table1");
        assertFalse(result, "Bulunamayan rezervasyon iptal edilememeli.");
    }

    @Test
    void testGetAllReservations() {
        reservationService.addReservation("Ayberk", LocalDateTime.of(2025, 3, 15, 19, 0), "Table1");
        reservationService.addReservation("Ali", LocalDateTime.of(2025, 3, 15, 20, 0), "Table2");

        List<Reservation> reservations = reservationService.getAllReservations();
        assertEquals(2, reservations.size(), "Toplam 2 rezervasyon olmalı.");
    }
}
