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
        boolean result = reservationService.addReservation("Ayberk", LocalDateTime.of(2025, 3, 15, 19, 0), "Room01");
        assertTrue(result, "Reservation must be added successfully.");
    }

    @Test
    void testAddReservation_Fail_DueToConflict() {
        reservationService.addReservation("Ayberk", LocalDateTime.of(2025, 3, 15, 19, 0), "Room101");
        boolean result = reservationService.addReservation("Ali", LocalDateTime.of(2025, 3, 15, 19, 0), "Room101");
        assertFalse(result, "Conflicting reservation must not added.");
    }

    @Test
    void testAddReservation_DifferentRooms_Successful() {
        boolean result1 = reservationService.addReservation("Ayberk", LocalDateTime.of(2025, 3, 15, 19, 0), "Room101");
        boolean result2 = reservationService.addReservation("Ali", LocalDateTime.of(2025, 3, 15, 19, 0), "Room102");

        assertTrue(result1, "First reservation must be added.");
        assertTrue(result2, "Second reservation must be added as well as it's room number is different");
    }

    @Test
    void testCancelReservation_Successful() {
        reservationService.addReservation("Ayberk", LocalDateTime.of(2025, 3, 15, 19, 0), "Room101");
        boolean result = reservationService.cancelReservation("Ayberk", LocalDateTime.of(2025, 3, 15, 19, 0), "Room101");
        assertTrue(result, "Reservation must be canceled successfully.");
    }

    @Test
    void testCancelReservation_Fail_NotFound() {
        boolean result = reservationService.cancelReservation("Ali", LocalDateTime.of(2025, 3, 15, 19, 0), "Room101");
        assertFalse(result, "A reservation that cannot be found cannot be cancelled.");
    }

    @Test
    void testGetAllReservations() {
        reservationService.addReservation("Ayberk", LocalDateTime.of(2025, 3, 15, 19, 0), "Room101");
        reservationService.addReservation("Ali", LocalDateTime.of(2025, 3, 15, 20, 0), "Room102");
        List<Reservation> reservations = reservationService.getAllReservations();
        assertEquals(2, reservations.size(), "There should be 2 reservations at total");
    }
}
