package com.example.reservation;

import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceTest {

    ReservationService reservationService = new ReservationService();

    @Test
    void testAddReservation_Success() {
        boolean result = reservationService.addReservation("Jaddy", LocalDate.of(2025, 5, 20), 101);
        assertTrue(result, "The reservation must be successful.");
    }

    @Test
    void testAddReservation_Fail_PastDate() {
        boolean result = reservationService.addReservation("Ayberk", LocalDate.of(2023, 3, 10), 101);
        assertFalse(result, "Reservations cannot be made for past dates.");
    }

    @Test
    void testAddReservation_Conflict() {
        reservationService.addReservation("Ali", LocalDate.of(2025, 5, 20), 101);
        boolean result = reservationService.addReservation("Veli", LocalDate.of(2025, 5, 20), 101);
        assertFalse(result, "The room cannot be reserved for someone else on the same date and time.");
    }

    @Test
    void testCancelReservation_Success() {
        reservationService.addReservation("Ayberk", LocalDate.of(2025, 5, 20), 101);
        boolean result = reservationService.cancelReservation("Ayberk", LocalDate.of(2025, 5, 20), 101);
        assertTrue(result, "The reservation must be canceled.");
    }

    @Test
    void testCancelReservation_Fail_NotExists() {
        boolean result = reservationService.cancelReservation("Ali", LocalDate.of(2025, 5, 20), 101);
        assertFalse(result, "A reservation that does not exist should not be canceled.");
    }

    @Test
    void testFindReservation_Success() {
        reservationService.addReservation("Ayberk", LocalDate.of(2025, 5, 20), 101);
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
        boolean result1 = reservationService.addReservation("Ali", LocalDate.of(2025, 5, 20), 101);
        boolean result2 = reservationService.addReservation("Veli", LocalDate.of(2025, 5, 29), 101);

        assertTrue(result1, "First reservation should be successful.");
        assertTrue(result2, "Reservations should be made at different times.");
    }

    @Test
    void testAddReservation_ThrowsException_WhenDateNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.addReservation("Ayberk", null, 101);
        });
        assertEquals("Name, Date-Time and/or Room Number fields must be filled.", exception.getMessage());
    }

    @Test
    void testAddReservation_ThrowsException_WhenRoomNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.addReservation("Ayberk", LocalDate.of(2025, 5, 20), null);
        });
        assertEquals("Name, Date-Time and/or Room Number fields must be filled.", exception.getMessage());
    }

    @Test
    void testCancelReservation_ThrowsException_WhenUserNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.addReservation(null, LocalDate.of(2025, 5, 20),101);
        });
        assertEquals("Name, Date-Time and/or Room Number fields must be filled.", exception.getMessage());
    }

    @Test
    void testReservationListNotEmptyAfterAdding() {
        reservationService.addReservation("Ayberk", LocalDate.of(2025, 5, 20), 101);
        assertFalse(reservationService.getAllReservations().isEmpty(), "Reservation list must not be empty");
    }

    @Test
    void testReservationListEmptyInitially() {
        assertTrue(reservationService.getAllReservations().isEmpty(), "Initially the reservation list must be empty.");
    }

    @Test
    void testReservationCount_AfterMultipleBookings() {
        reservationService.addReservation("Ali", LocalDate.of(2025, 5, 20), 101);
        reservationService.addReservation("Mehmet", LocalDate.of(2025, 5, 20), 155);
        reservationService.addReservation("Veli", LocalDate.of(2025, 5, 20), 151);
        reservationService.addReservation("Ayberk", LocalDate.of(2025, 5, 20), 107);

        assertEquals(4, reservationService.getAllReservations().size(), "The reservation count must be 4.");
    }

    @Test
    void testReservationContainsCorrectUser() {
        reservationService.addReservation("Ayberk", LocalDate.of(2025, 5, 20), 101);
        Reservation reservation = reservationService.findReservation("Ayberk", 101);

        assertEquals("Ayberk", reservation.getCustomerName(), "The user who made the reservation must be correct.");
    }

    @Test
    void testReservationContainsCorrectRoom() {
        reservationService.addReservation("Ayberk", LocalDate.of(2025, 5, 20), 101);
        Reservation reservation = reservationService.findReservation("Ayberk", 101);

        assertEquals(101, reservation.getRoomNumber(), "The room of the reservation must be correct.");
    }

    @Test
    void testInvalidDateFormatThrowsException() {
        Exception exception = assertThrows(DateTimeException.class, () -> {
            reservationService.addReservation("Ayberk", LocalDate.of(2025, 2, 30), 101);
        });
        assertEquals("Invalid date 'FEBRUARY 30'", exception.getMessage());
    }

    @Test
    void testInvalidRoomNumberThrowsException(){
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            reservationService.addReservation("Ayberk", LocalDate.of(2025, 5, 20), 200);
        });
        assertEquals("Room number must be selected between 101-199 (101 and 199 included).", exception.getMessage());
    }


    //cok ileri tarihli rezervasyon alınmasını engelle.
    //max 1 yil sonrasina rez alinabilir.

    //odaya kisi sayisi ekle ve onu test et!
    //kisi sayisi 0 olamaz. max 4 olabilir.

    //Boolean cift kisilik yatak parametresi eklenebilir.

}
