package com.example.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTableTests {

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService();
    }

    @Test
    @DisplayName("TC1: Null Fields Test")
    void testTC1_NullFields() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.addReservation(null, LocalDate.now().plusDays(30), 150, 2);
        });

        assertEquals("Name, Date-Time, Room Number and/or Guest Count fields must be filled.",
                exception.getMessage(), "Exception message should match");
    }

    @Test
    @DisplayName("TC2: Past Date Test")
    void testTC2_PastDate() {
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
    @DisplayName("TC3: Future Date > 1 Year Test")
    void testTC3_FutureDateMoreThanOneYear() {
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
    @DisplayName("TC4: Invalid Room Number Test")
    void testTC4_InvalidRoomNumber() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            reservationService.addReservation(
                    "John Smith",
                    LocalDate.now().plusDays(30),
                    100, // Below valid range
                    2
            );
        });

        assertEquals("Room number must be selected between 101-199 (101 and 199 included).",
                exception.getMessage(), "Exception message should match for invalid room number");
    }

    @Test
    @DisplayName("TC5: Invalid Guest Count Test")
    void testTC5_InvalidGuestCount() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            reservationService.addReservation(
                    "John Smith",
                    LocalDate.now().plusDays(30),
                    150,
                    0 // Below valid range
            );
        });

        assertEquals("Guest count must be between 1-4. (1 and 4 included)",
                exception.getMessage(), "Exception message should match for invalid guest count");
    }

    @Test
    @DisplayName("TC6: Reservation Conflict Test")
    void testTC6_ReservationConflict() {
        // First add a reservation
        reservationService.addReservation(
                "John Smith",
                LocalDate.of(2025, 6, 15),
                150,
                2
        );

        // Try to add another reservation for the same room on the same date
        boolean result = reservationService.addReservation(
                "Jane Doe",
                LocalDate.of(2025, 6, 15),
                150,
                3
        );

        assertFalse(result, "Second reservation should be rejected due to conflict");
        assertEquals(1, reservationService.getAllReservations().size(), "Only one reservation should be added");
    }

    @Test
    @DisplayName("TC7: Happy Path Test (Successful Reservation)")
    void testTC7_HappyPath() {
        // Valid inputs: future date within 1 year, valid room, valid guest count, no conflicts
        boolean result = reservationService.addReservation(
                "John Smith",
                LocalDate.now().plusDays(30),
                150,
                2
        );

        assertTrue(result, "The reservation should be successfully added");
        assertEquals(1, reservationService.getAllReservations().size(), "There should be one reservation");

        // Verify the reservation details
        Reservation found = reservationService.findReservation("John Smith", 150);
        assertNotNull(found, "Should be able to find the reservation");
        assertEquals("John Smith", found.getCustomerName(), "Customer name should match");
        assertEquals(150, found.getRoomNumber(), "Room number should match");
        assertEquals(2, found.getGuestCount(), "Guest count should match");
    }
}