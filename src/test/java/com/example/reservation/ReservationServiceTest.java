package com.example.reservation;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.DateTimeException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceTest {

    ReservationService reservationService = new ReservationService();

    @Test
    void testAddReservation_Success() {
        boolean result = reservationService.addReservation("Jaddy", LocalDate.of(2025, 5, 20), 101, 2);
        assertTrue(result, "The reservation must be successful.");
    }

    @RepeatedTest(5)
    void testRepeatableSameUserDifferentDates() {
        int dayOffset = (int) (Math.random() * 30) + 1;
        LocalDate date = LocalDate.now().plusDays(dayOffset);
        boolean result = reservationService.addReservation("Ali", date, 101, 2);
        assertTrue(result, "Same user should be able to book the same room on different dates.");
    }

    @ParameterizedTest
    @CsvSource({
            "Ali, 2025-05-20, 101, 2",
            "Mehmet, 2025-06-15, 102, 3",
            "Ayberk, 2025-03-20, 103, 4"
    })
    void testAddReservationParameterized(String name, String date, int room, int guests) {
        boolean result = reservationService.addReservation(name, LocalDate.parse(date), room, guests);
        assertTrue(result, "Parameterized test should allow reservation creation.");
    }

    @Test
    void testAddReservation_Conflict() {
        reservationService.addReservation("Ali", LocalDate.of(2025, 5, 20), 101,2);
        boolean result = reservationService.addReservation("Veli", LocalDate.of(2025, 5, 20), 101, 2);
        assertFalse(result, "The room cannot be reserved for someone else on the same date and time.");
    }

    @Test
    void testCancelReservation_Success() {
        reservationService.addReservation("Ayberk", LocalDate.of(2025, 5, 20), 101, 2);
        boolean result = reservationService.cancelReservation("Ayberk", LocalDate.of(2025, 5, 20), 101, 2);
        assertTrue(result, "The reservation must be canceled.");
    }

    @Test
    void testCancelReservation_Fail_NotExists() {
        boolean result = reservationService.cancelReservation("Ali", LocalDate.of(2025, 5, 20), 101, 2);
        assertFalse(result, "A reservation that does not exist should not be canceled.");
    }

    @Test
    void testFindReservation_Success() {
        reservationService.addReservation("Ayberk", LocalDate.of(2025, 5, 20), 101, 2);
        Reservation reservation = reservationService.findReservation("Ayberk", 101);
        assertNotNull(reservation, "Reservation must be found.");
    }

    @Test
    void testFindReservation_Fail_NotExists() {
        Reservation reservation = reservationService.findReservation("Ali", 102);
        assertNull(reservation, "There should be no reservation.");
    }

    @Test
    void testDifferentUsers_SameRoom_DifferentTimes() {
        boolean result1 = reservationService.addReservation("Ali", LocalDate.of(2025, 5, 20), 101, 2);
        boolean result2 = reservationService.addReservation("Veli", LocalDate.of(2025, 5, 29), 101, 2);

        assertTrue(result1, "First reservation should be successful.");
        assertTrue(result2, "Reservations should be made at different times.");
    }

    @Test
    void testAddReservation_ThrowsException_WhenDateNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.addReservation("Ayberk", null, 101, 2);
        });
        assertEquals("Name, Date-Time, Room Number and/or Guest Count fields must be filled.", exception.getMessage());
    }

    @Test
    void testAddReservation_ThrowsException_WhenRoomNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.addReservation("Ayberk", LocalDate.of(2025, 5, 20), null, 2);
        });
        assertEquals("Name, Date-Time, Room Number and/or Guest Count fields must be filled.", exception.getMessage());
    }

    @Test
    void testCancelReservation_ThrowsException_WhenUserNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.addReservation(null, LocalDate.of(2025, 5, 20),101, 2);
        });
        assertEquals("Name, Date-Time, Room Number and/or Guest Count fields must be filled.", exception.getMessage());
    }

    @Test
    void testReservationListNotEmptyAfterAdding() {
        reservationService.addReservation("Ayberk", LocalDate.of(2025, 5, 20), 101, 2);
        assertFalse(reservationService.getAllReservations().isEmpty(), "Reservation list must not be empty");
    }

    @Test
    void testReservationListEmptyInitially() {
        assertTrue(reservationService.getAllReservations().isEmpty(), "Initially the reservation list must be empty.");
    }

    @Test
    void testReservationCount_AfterMultipleBookings() {
        reservationService.addReservation("Ali", LocalDate.of(2025, 5, 20), 101, 2);
        reservationService.addReservation("Mehmet", LocalDate.of(2025, 5, 20), 155, 2);
        reservationService.addReservation("Veli", LocalDate.of(2025, 5, 20), 151, 2);
        reservationService.addReservation("Ayberk", LocalDate.of(2025, 5, 20), 107, 2);

        assertEquals(4, reservationService.getAllReservations().size(), "The reservation count must be 4.");
    }

    @Test
    void testReservationContainsCorrectUser() {
        reservationService.addReservation("Ayberk", LocalDate.of(2025, 5, 20), 101, 2);
        Reservation reservation = reservationService.findReservation("Ayberk", 101);

        assertEquals("Ayberk", reservation.getCustomerName(), "The user who made the reservation must be correct.");
    }

    @Test
    void testReservationContainsCorrectRoom() {
        reservationService.addReservation("Ayberk", LocalDate.of(2025, 5, 20), 101, 2);
        Reservation reservation = reservationService.findReservation("Ayberk", 101);

        assertEquals(101, reservation.getRoomNumber(), "The room of the reservation must be correct.");
    }

    @Test
    void testAddReservation_Fail_PastDate() {
        boolean result = reservationService.addReservation("Ayberk", LocalDate.of(2023, 3, 10), 101, 2);
        assertFalse(result, "Reservations cannot be made for past dates.");
    }

    @Test
    void testAddReservation_Fail_FarDate() {
        boolean result = reservationService.addReservation("Ayberk", LocalDate.of(2026, 3, 20), 101, 2);
        boolean result2 = reservationService.addReservation("Ayberk", LocalDate.of(2026, 1, 20), 101, 2);
        assertFalse(result, "Reservations can be made up to 1 year later.");
        assertTrue(result2, "Next year reservations can be made if it's not 1 year later.");
    }

    @Test
    void testInvalidDateFormatThrowsException() {
        Exception exception = assertThrows(DateTimeException.class, () -> {
            reservationService.addReservation("Ayberk", LocalDate.of(2025, 2, 30), 101, 2);
        });
        assertEquals("Invalid date 'FEBRUARY 30'", exception.getMessage());
    }

    @Test
    void testInvalidRoomNumberThrowsException(){
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            reservationService.addReservation("Ayberk", LocalDate.of(2025, 5, 20), 200, 2);
        });
        assertEquals("Room number must be selected between 101-199 (101 and 199 included).", exception.getMessage());
    }

    @Test
    void testInvalidGuestCountThrowsException(){
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
           reservationService.addReservation("Ayberk", LocalDate.of(2025, 5, 20), 101, 0);
        });
        Exception exception2 = assertThrows(IndexOutOfBoundsException.class, () -> {
            reservationService.addReservation("Ayberk", LocalDate.of(2025, 5, 20), 101, 5);
        });
        assertEquals("Guest count must be between 1-4. (1 and 4 included)", exception.getMessage());
        assertEquals("Guest count must be between 1-4. (1 and 4 included)", exception2.getMessage());
    }

}
