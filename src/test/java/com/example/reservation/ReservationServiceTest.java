package com.example.reservation;

import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceTest {

    ReservationService reservationService = new ReservationService();

    @Test
    void testAddReservation_Success() {
        boolean result = reservationService.addReservation("Ayberk", LocalDateTime.of(2026, 5, 20, 14, 0), "Room101");
        assertTrue(result, "The reservation must be successful.");
    }

    @Test
    void testAddReservation_Fail_PastDate() {
        boolean result = reservationService.addReservation("Ayberk", LocalDateTime.of(2023, 3, 10, 14, 0), "Room101");
        assertFalse(result, "Reservations cannot be made for past dates.");
    }

    @Test
    void testAddReservation_Conflict() {
        reservationService.addReservation("Ali", LocalDateTime.of(2026, 5, 20, 14, 0), "Room101");
        boolean result = reservationService.addReservation("Veli", LocalDateTime.of(2026, 5, 20, 14, 0), "Room101");
        assertFalse(result, "The room cannot be reserved for someone else on the same date and time.");
    }

    @Test
    void testCancelReservation_Success() {
        reservationService.addReservation("Ayberk", LocalDateTime.of(2026, 5, 20, 14, 0), "Room101");
        boolean result = reservationService.cancelReservation("Ayberk", LocalDateTime.of(2026, 5, 20, 14, 0), "Room101");
        assertTrue(result, "The reservation must be canceled.");
    }

    @Test
    void testCancelReservation_Fail_NotExists() {
        boolean result = reservationService.cancelReservation("Ali", LocalDateTime.of(2026, 5, 20, 14, 0), "Room101");
        assertFalse(result, "A reservation that does not exist should not be canceled.");
    }

    @Test
    void testFindReservation_Success() {
        reservationService.addReservation("Ayberk", LocalDateTime.of(2026, 5, 20, 14, 0), "Room101");
        Reservation reservation = reservationService.findReservation("Ayberk", "Room101");
        assertNotNull(reservation, "Reservation must be found.");
    }

    @Test
    void testFindReservation_Fail_NotExists() {
        Reservation reservation = reservationService.findReservation("Ali", "Room102");
        assertNull(reservation, "There should be no reservation.");
    }

    @Test
    void testDifferentUsers_SameRoom_DifferentTimes() {
        boolean result1 = reservationService.addReservation("Ali", LocalDateTime.of(2026, 5, 20, 10, 0), "Room101");
        boolean result2 = reservationService.addReservation("Veli", LocalDateTime.of(2026, 5, 20, 12, 0), "Room101");

        assertTrue(result1, "First reservation should be successful.");
        assertTrue(result2, "Reservations should be made at different times.");
    }

    @Test
    void testAddReservation_ThrowsException_WhenDateNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.addReservation("Ayberk", null, "Room101");
        });
        assertEquals("Name, Date-Time and/or Room Number fields must be filled.", exception.getMessage());
    }

    @Test
    void testAddReservation_ThrowsException_WhenRoomNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.addReservation("Ayberk", LocalDateTime.of(2026, 5, 20, 14, 0), null);
        });
        assertEquals("Name, Date-Time and/or Room Number fields must be filled.", exception.getMessage());
    }

    @Test
    void testCancelReservation_ThrowsException_WhenUserNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.addReservation(null, LocalDateTime.of(2026, 5, 20, 14, 0),"Room101");
        });
        assertEquals("Name, Date-Time and/or Room Number fields must be filled.", exception.getMessage());
    }

    @Test
    void testReservationListNotEmptyAfterAdding() {
        reservationService.addReservation("Ayberk", LocalDateTime.of(2026, 5, 20, 14, 0), "Room101");
        assertFalse(reservationService.getAllReservations().isEmpty(), "Reservation list must not be empty");
    }

    @Test
    void testReservationListEmptyInitially() {
        assertTrue(reservationService.getAllReservations().isEmpty(), "Initially the reservation list must be empty.");
    }

    @Test
    void testReservationCount_AfterMultipleBookings() {
        reservationService.addReservation("Ali", LocalDateTime.of(2026, 5, 20, 10, 0), "Room101");
        reservationService.addReservation("Veli", LocalDateTime.of(2026, 5, 20, 12, 0), "Room101");

        assertEquals(2, reservationService.getAllReservations().size(), "The reservation count must be 2.");
    }

    @Test
    void testReservationContainsCorrectUser() {
        reservationService.addReservation("Ayberk", LocalDateTime.of(2026, 5, 20, 14, 0), "Room101");
        Reservation reservation = reservationService.findReservation("Ayberk", "Room101");

        assertEquals("Ayberk", reservation.getCustomerName(), "The user who made the reservation must be correct.");
    }

    @Test
    void testReservationContainsCorrectRoom() {
        reservationService.addReservation("Ayberk", LocalDateTime.of(2026, 5, 20, 14, 0), "Room101");
        Reservation reservation = reservationService.findReservation("Ayberk", "Room101");

        assertEquals("Room101", reservation.getRoomNumber(), "The room of the reservation must be correct.");
    }

    @Test
    void testInvalidDateFormatThrowsException() {
        Exception exception = assertThrows(DateTimeException.class, () -> {
            reservationService.addReservation("Ayberk", LocalDateTime.of(2025, 2, 30, 15, 0), "Room101");
        });
        assertEquals("Invalid date 'FEBRUARY 30'", exception.getMessage());
    }

}
