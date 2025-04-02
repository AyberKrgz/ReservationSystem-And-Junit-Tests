package com.example.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceBasisPathTests {

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService();
    }

    @Test
    @DisplayName("Path 1: Happy Path - All valid inputs, no conflicts")
    void testAddReservation_HappyPath() {
        // Valid inputs: future date within 1 year, valid room, valid guest count
        boolean result = reservationService.addReservation(
                "John Smith",
                LocalDate.now().plusDays(30),
                150,
                2
        );

        assertTrue(result, "The reservation should be successfully added");
        assertEquals(1, reservationService.getAllReservations().size(), "There should be one reservation");
    }

    @Test
    @DisplayName("Path 2: Null Fields Path")
    void testAddReservation_NullFields() {
        // Testing all null field combinations
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.addReservation(
                    null,
                    LocalDate.now().plusDays(30),
                    150,
                    2
            );
        });

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.addReservation(
                    "John Smith",
                    null,
                    150,
                    2
            );
        });

        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.addReservation(
                    "John Smith",
                    LocalDate.now().plusDays(30),
                    null,
                    2
            );
        });

        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.addReservation(
                    "John Smith",
                    LocalDate.now().plusDays(30),
                    150,
                    null
            );
        });

        assertEquals("Name, Date-Time, Room Number and/or Guest Count fields must be filled.",
                exception1.getMessage(), "Exception message should match");
    }

    @Test
    @DisplayName("Path 3: Past Date Path")
    void testAddReservation_PastDate() {
        // Testing with a date in the past
        boolean result = reservationService.addReservation(
                "John Smith",
                LocalDate.now().minusDays(1),
                150,
                2
        );

        assertFalse(result, "Reservation with past date should be rejected");
        assertEquals(0, reservationService.getAllReservations().size(), "No reservation should be added");
    }

    @Test
    @DisplayName("Path 4: Date > 1 Year in Future Path")
    void testAddReservation_FutureDateMoreThanOneYear() {
        // Testing with a date more than one year in the future
        boolean result = reservationService.addReservation(
                "John Smith",
                LocalDate.now().plusYears(2),
                150,
                2
        );

        assertFalse(result, "Reservation more than 1 year in future should be rejected");
        assertEquals(0, reservationService.getAllReservations().size(), "No reservation should be added");
    }

    @Test
    @DisplayName("Path 5: Invalid Room Number Path")
    void testAddReservation_InvalidRoomNumber() {
        // Testing with invalid room numbers
        Exception exceptionLow = assertThrows(IndexOutOfBoundsException.class, () -> {
            reservationService.addReservation(
                    "John Smith",
                    LocalDate.now().plusDays(30),
                    100, // Below valid range
                    2
            );
        });

        Exception exceptionHigh = assertThrows(IndexOutOfBoundsException.class, () -> {
            reservationService.addReservation(
                    "John Smith",
                    LocalDate.now().plusDays(30),
                    200, // Above valid range
                    2
            );
        });

        assertEquals("Room number must be selected between 101-199 (101 and 199 included).",
                exceptionLow.getMessage(), "Exception message should match for low room number");
        assertEquals("Room number must be selected between 101-199 (101 and 199 included).",
                exceptionHigh.getMessage(), "Exception message should match for high room number");
    }

    @Test
    @DisplayName("Path 6: Invalid Guest Count Path")
    void testAddReservation_InvalidGuestCount() {
        // Testing with invalid guest counts
        Exception exceptionLow = assertThrows(IndexOutOfBoundsException.class, () -> {
            reservationService.addReservation(
                    "John Smith",
                    LocalDate.now().plusDays(30),
                    150,
                    0 // Below valid range
            );
        });

        Exception exceptionHigh = assertThrows(IndexOutOfBoundsException.class, () -> {
            reservationService.addReservation(
                    "John Smith",
                    LocalDate.now().plusDays(30),
                    150,
                    5 // Above valid range
            );
        });

        assertEquals("Guest count must be between 1-4. (1 and 4 included)",
                exceptionLow.getMessage(), "Exception message should match for low guest count");
        assertEquals("Guest count must be between 1-4. (1 and 4 included)",
                exceptionHigh.getMessage(), "Exception message should match for high guest count");
    }

    @Test
    @DisplayName("Path 7: Reservation Conflict Path")
    void testAddReservation_ReservationConflict() {
        // First add a reservation
        boolean result1 = reservationService.addReservation(
                "John Smith",
                LocalDate.of(2025, 6, 15),
                150,
                2
        );

        // Try to add another reservation for the same room on the same date
        boolean result2 = reservationService.addReservation(
                "Jane Doe",
                LocalDate.of(2025, 6, 15),
                150,
                3
        );

        assertTrue(result1, "First reservation should be added successfully");
        assertFalse(result2, "Second reservation should be rejected due to conflict");
        assertEquals(1, reservationService.getAllReservations().size(), "Only one reservation should be added");
    }
}