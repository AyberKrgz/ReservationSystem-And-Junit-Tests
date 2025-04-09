package com.example.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to detect mutations in ReservationService's addReservation method
 * These tests are designed to identify (kill) the mutations in MutatedReservation class
 */
public class MutationDetectionTests {

    private ReservationService originalService;
    private MutatedReservation mutatedReservation;

    @BeforeEach
    void setUp() {
        originalService = new ReservationService();
        mutatedReservation = new MutatedReservation();
    }

    // Tests 1-4 remain unchanged except for using mutatedReservation instead of mutationTests

    @Test
    @DisplayName("Test to detect Mutant 5: Method Call Removal (reservations.add)")
    void testDetectMutant5() {
        // Make two separate reservations for the same room and date with the mutant
        // First reservation should succeed
        LocalDate testDate = LocalDate.now().plusDays(30);
        Integer testRoom = 150;

        boolean firstAdd = mutatedReservation.mutant5_RemoveMethodCall("John", testDate, testRoom, 2);
        assertTrue(firstAdd, "First reservation should be successful");

        // Second reservation for same room/date should also succeed if the first wasn't actually added
        boolean secondAdd = mutatedReservation.mutant5_RemoveMethodCall("Jane", testDate, testRoom, 3);

        // In the original code, this would be false (conflict)
        // But in the mutant, it should be true (since nothing was added)
        assertTrue(secondAdd, "Second reservation should succeed since first wasn't added");

        // Now test the original service for comparison
        boolean originalFirstAdd = originalService.addReservation("John", testDate, testRoom, 2);
        assertTrue(originalFirstAdd, "Original first reservation should succeed");

        boolean originalSecondAdd = originalService.addReservation("Jane", testDate, testRoom, 3);
        assertFalse(originalSecondAdd, "Original second reservation should fail (conflict)");

        // This proves the mutation changes behavior - it's detected (killed)
        assertNotEquals(originalSecondAdd, secondAdd,
                "Original and mutant should behave differently for conflict detection");
    }

    // Test 6 remains unchanged except for using mutatedReservation

    @Test
    @DisplayName("Test to detect Mutant 7: Removed Conditional (null check)")
    void testDetectMutant7() {
        // With original service, null parameters should throw IllegalArgumentException
        Exception originalException = assertThrows(IllegalArgumentException.class, () -> {
            originalService.addReservation(null, LocalDate.now().plusDays(30), 150, 2);
        }, "Original service should throw IllegalArgumentException for null name");

        assertEquals("Name, Date-Time, Room Number and/or Guest Count fields must be filled.",
                originalException.getMessage());

        // With mutant, null parameters should not throw IllegalArgumentException
        // Instead, it will either:
        // 1. Execute normally until it hits another problem (e.g., NullPointerException)
        // 2. Complete successfully (unlikely with null parameters)

        // We'll check that the behavior is different from the original
        boolean differentBehavior = false;

        try {
            mutatedReservation.mutant7_RemovedConditional(null, LocalDate.now().plusDays(30), 150, 2);
            // If we get here, the mutation worked - no IllegalArgumentException was thrown
            differentBehavior = true;
        } catch (IllegalArgumentException e) {
            // Check if it's the same exception message
            if (e.getMessage().equals("Name, Date-Time, Room Number and/or Guest Count fields must be filled.")) {
                // Same message means mutation didn't work
                differentBehavior = false;
            } else {
                // Different message means different validation - mutation detected
                differentBehavior = true;
            }
        } catch (Exception e) {
            // Any other exception means the mutation worked - behavior changed
            differentBehavior = true;
        }

        assertTrue(differentBehavior, "Mutation should change behavior for null parameters");
    }

    // Test 8 remains unchanged except for using mutatedReservation

    @Test
    @DisplayName("Test to detect Mutant 9: Method Call (true && date.equals)")
    void testDetectMutant9() {
        // Setup: use two different rooms on the same date
        LocalDate testDate = LocalDate.now().plusDays(30);
        Integer room1 = 150;
        Integer room2 = 151; // Different room

        // With original service, different rooms on same date should both work
        boolean originalRoom1 = originalService.addReservation("John", testDate, room1, 2);
        boolean originalRoom2 = originalService.addReservation("Jane", testDate, room2, 2);

        assertTrue(originalRoom1, "Original service should allow first room");
        assertTrue(originalRoom2, "Original service should allow different room on same date");

        // With mutant, first room should work
        boolean mutantRoom1 = mutatedReservation.mutant9_MethodCallRemoval("John", testDate, room1, 2);
        assertTrue(mutantRoom1, "Mutant should allow first room");

        // But second room should fail because the mutant treats all rooms as the same
        // Since res.getRoomNumber().equals(roomNumber) is replaced with true
        boolean mutantRoom2 = mutatedReservation.mutant9_MethodCallRemoval("Jane", testDate, room2, 2);

        // This is the key test - should be false with the mutation
        assertFalse(mutantRoom2, "Mutant should reject different room on same date");

        // This proves the mutation changes behavior - it's detected (killed)
        assertNotEquals(originalRoom2, mutantRoom2,
                "Original and mutant should behave differently for different rooms");
    }

    // Test 10 remains unchanged except for using mutatedReservation
}