package com.example.reservation;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceTest {

    ReservationService reservationService = new ReservationService();

    @Test
    void testAddReservation_Success() {
        boolean result = reservationService.addReservation("Ayberk", LocalDateTime.of(2026, 5, 20, 14, 0), "Room101");
        assertTrue(result, "Rezervasyon başarılı olmalı.");
    }

    @Test
    void testAddReservation_Fail_PastDate() {
        boolean result = reservationService.addReservation("Ayberk", LocalDateTime.of(2023, 3, 10, 14, 0), "Room101");
        assertFalse(result, "Geçmiş tarihe rezervasyon yapılamamalı.");
    }

    @Test
    void testAddReservation_Duplicate() {
        reservationService.addReservation("Ali", LocalDateTime.of(2026, 5, 20, 14, 0), "Room101");
        boolean result = reservationService.addReservation("Veli", LocalDateTime.of(2026, 5, 20, 14, 0), "Room101");
        assertFalse(result, "Aynı tarih ve saatte oda başkasına rezerve edilememeli.");
    }

    @Test
    void testCancelReservation_Success() {
        reservationService.addReservation("Ayberk", LocalDateTime.of(2026, 5, 20, 14, 0), "Room101");
        boolean result = reservationService.cancelReservation("Ayberk", LocalDateTime.of(2026, 5, 20, 14, 0), "Room101");
        assertTrue(result, "Rezervasyon iptal edilebilmeli.");
    }

    @Test
    void testCancelReservation_Fail_NotExists() {
        boolean result = reservationService.cancelReservation("Ali", LocalDateTime.of(2026, 5, 20, 14, 0), "Room101");
        assertFalse(result, "Var olmayan rezervasyon iptal edilememeli.");
    }

    @Test
    void testFindReservation_Success() {
        reservationService.addReservation("Ayberk", LocalDateTime.of(2026, 5, 20, 14, 0), "Room101");
        Reservation reservation = reservationService.findReservation("Ayberk", "Room101");
        assertNotNull(reservation, "Rezervasyon bulunmalı.");
    }

    @Test
    void testFindReservation_Fail_NotExists() {
        Reservation reservation = reservationService.findReservation("Ali", "Room102");
        assertNull(reservation, "Rezervasyon bulunmamalı.");
    }

    @Test
    void testMaxReservationsPerRoom() {
        reservationService.addReservation("Ali", LocalDateTime.of(2026, 5, 20, 10, 0), "Room101");
        reservationService.addReservation("Veli", LocalDateTime.of(2026, 5, 20, 12, 0), "Room101");
        reservationService.addReservation("Ayşe", LocalDateTime.of(2026, 5, 20, 14, 0), "Room101");

        boolean result = reservationService.addReservation("Fatma", LocalDateTime.of(2026, 5, 20, 16, 0), "Room101");
        assertFalse(result, "Oda kapasitesi aşılamamalı.");
    }

    @Test
    void testDifferentUsers_SameRoom_DifferentTimes() {
        boolean result1 = reservationService.addReservation("Ali", LocalDateTime.of(2026, 5, 20, 10, 0), "Room101");
        boolean result2 = reservationService.addReservation("Veli", LocalDateTime.of(2026, 5, 20, 12, 0), "Room101");

        assertTrue(result1, "İlk rezervasyon başarılı olmalı.");
        assertTrue(result2, "Farklı saatlerde rezervasyon yapılabilmeli.");
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
        assertFalse(reservationService.getAllReservations().isEmpty(), "Rezervasyon listesi boş olmamalı.");
    }

    @Test
    void testReservationListEmptyInitially() {
        assertTrue(reservationService.getAllReservations().isEmpty(), "İlk başta rezervasyon listesi boş olmalı.");
    }

    @Test
    void testReservationCount_AfterMultipleBookings() {
        reservationService.addReservation("Ali", LocalDateTime.of(2026, 5, 20, 10, 0), "Room101");
        reservationService.addReservation("Veli", LocalDateTime.of(2026, 5, 20, 12, 0), "Room101");

        assertEquals(2, reservationService.getAllReservations().size(), "Toplam rezervasyon sayısı 2 olmalı.");
    }

    @Test
    void testReservationContainsCorrectUser() {
        reservationService.addReservation("Ayberk", LocalDateTime.of(2026, 5, 20, 14, 0), "Room101");
        Reservation reservation = reservationService.findReservation("Ayberk", "Room101");

        assertEquals("Ayberk", reservation.getCustomerName(), "Rezervasyonu yapan kullanıcı doğru olmalı.");
    }

    @Test
    void testReservationContainsCorrectRoom() {
        reservationService.addReservation("Ayberk", LocalDateTime.of(2026, 5, 20, 14, 0), "Room101");
        Reservation reservation = reservationService.findReservation("Ayberk", "Room101");

        assertEquals("Room101", reservation.getRoomNumber(), "Rezervasyonun yapıldığı oda doğru olmalı.");
    }
}
