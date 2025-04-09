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
    private MutatedReservation mutationTests;

    @BeforeEach
    void setUp() {
        originalService = new ReservationService();
        mutationTests = new MutatedReservation();
    }

    @Test
    @DisplayName("Test to detect Mutant 1: Condition Boundary (roomNumber<=100)")
    void testDetectMutant1() {
        // This test should fail with mutant1 because room 101 would be rejected
        try {
            boolean originalResult = originalService.addReservation("John", LocalDate.now().plusDays(30), 101, 2);
            boolean mutantResult = mutationTests.mutant1_RoomNumberBoundary("John", LocalDate.now().plusDays(30), 101, 2);

            assertTrue(originalResult, "Original service should accept room 101");
            // The following assertion should fail with the mutant
            assertTrue(mutantResult, "Mutant should also accept room 101");

            // Additional verification
            assertEquals(originalResult, mutantResult, "Results should be the same");
        } catch (Exception e) {
            // The mutation should cause an exception with room 101
            fail("Mutation detected: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test to detect Mutant 2: Upper Boundary (roomNumber>=199)")
    void testDetectMutant2() {
        // This test should fail with mutant2 because room 199 would be rejected
        try {
            boolean originalResult = originalService.addReservation("John", LocalDate.now().plusDays(30), 199, 2);
            boolean mutantResult = mutationTests.mutant2_RoomNumberUpperBoundary("John", LocalDate.now().plusDays(30), 199, 2);

            assertTrue(originalResult, "Original service should accept room 199");
            // The following assertion should fail with the mutant
            assertTrue(mutantResult, "Mutant should also accept room 199");

            // Additional verification
            assertEquals(originalResult, mutantResult, "Results should be the same");
        } catch (Exception e) {
            // The mutation should cause an exception with room 199
            fail("Mutation detected: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test to detect Mutant 3: Guest Count Operator (guestCount<=1)")
    void testDetectMutant3() {
        // This test should fail with mutant3 because guest count 1 would be rejected
        try {
            boolean originalResult = originalService.addReservation("John", LocalDate.now().plusDays(30), 150, 1);
            boolean mutantResult = mutationTests.mutant3_GuestCountOperator("John", LocalDate.now().plusDays(30), 150, 1);

            assertTrue(originalResult, "Original service should accept guest count 1");
            // The following assertion should fail with the mutant
            assertTrue(mutantResult, "Mutant should also accept guest count 1");

            // Additional verification
            assertEquals(originalResult, mutantResult, "Results should be the same");
        } catch (Exception e) {
            // The mutation should cause an exception with guest count 1
            fail("Mutation detected: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test to detect Mutant 4: Return Value (true -> false)")
    void testDetectMutant4() {
        // This test should fail with mutant4 because it always returns false
        boolean originalResult = originalService.addReservation("John", LocalDate.now().plusDays(30), 150, 2);
        boolean mutantResult = mutationTests.mutant4_ReturnValueMutation("John", LocalDate.now().plusDays(30), 150, 2);

        assertTrue(originalResult, "Original service should return true");
        // The following assertion should fail with the mutant
        assertTrue(mutantResult, "Mutant should also return true");
    }

    @Test
    @DisplayName("Test to detect Mutant 5: Method Call Removal (reservations.add)")
    void testDetectMutant5() {
        // This test should detect mutant5 by checking if reservation was actually added
        originalService.addReservation("John", LocalDate.now().plusDays(30), 150, 2);
        List<Reservation> originalReservations = originalService.getAllReservations();

        assertEquals(1, originalReservations.size(), "Original service should add the reservation");

        boolean mutantResult = mutationTests.mutant5_RemoveMethodCall("Jane", LocalDate.now().plusDays(30), 151, 2);
        assertTrue(mutantResult, "Mutant should return true");

        Reservation found = originalService.findReservation("Jane", 151);
        assertNotNull(found, "Should be able to find the added reservation");
    }

    @Test
    @DisplayName("Test to detect Mutant 6: Negated Conditional (date.isBefore)")
    void testDetectMutant6() {
        // This test should fail with mutant6 because future dates would be rejected
        LocalDate futureDate = LocalDate.now().plusDays(30);

        boolean originalResult = originalService.addReservation("John", futureDate, 150, 2);
        boolean mutantResult = mutationTests.mutant6_NegatedConditional("John", futureDate, 150, 2);

        assertTrue(originalResult, "Original service should accept future date");
        // The following assertion should fail with the mutant
        assertTrue(mutantResult, "Mutant should also accept future date");
    }

    @Test
    @DisplayName("Test to detect Mutant 7: Removed Conditional (null check)")
    void testDetectMutant7() {
        // This test should detect mutant7 by trying to add with null parameters
        assertThrows(IllegalArgumentException.class, () -> {
            originalService.addReservation(null, LocalDate.now().plusDays(30), 150, 2);
        }, "Original service should throw exception for null name");

        // The mutant should not throw an exception (but might throw NullPointerException later)
        try {
            mutationTests.mutant7_RemovedConditional(null, LocalDate.now().plusDays(30), 150, 2);
            // If we get here, the mutation was detected (no IllegalArgumentException)
        } catch (IllegalArgumentException e) {
            fail("Mutation not detected: still throws IllegalArgumentException");
        } catch (Exception e) {
            // Other exceptions might occur due to null handling, which is expected
        }
    }

    @Test
    @DisplayName("Test to detect Mutant 8: Constructor Call (null)")
    void testDetectMutant8() {
        // This test should detect mutant8 which will likely cause NullPointerException
        boolean originalResult = originalService.addReservation("John", LocalDate.now().plusDays(30), 150, 2);
        assertTrue(originalResult, "Original service should successfully add reservation");

        // The mutant should throw a NullPointerException when trying to add null to the list
        assertThrows(NullPointerException.class, () -> {
            mutationTests.mutant8_ConstructorCallMutation("John", LocalDate.now().plusDays(30), 150, 2);
        }, "Mutant should throw NullPointerException");
    }

    @Test
    @DisplayName("Test to detect Mutant 9: Method Call (true && date.equals)")
    void testDetectMutant9() {
        // This test should detect mutant9 by testing conflicting reservations

        // First, add a reservation for a different room but same date
        LocalDate date = LocalDate.now().plusDays(30);
        originalService.addReservation("John", date, 150, 2);
        mutationTests.mutant9_MethodCallRemoval("John", date, 150, 2);

        // In the original service, we should be able to add a reservation for a different room
        boolean originalResult = originalService.addReservation("Jane", date, 151, 2);
        assertTrue(originalResult, "Original service should allow different room on same date");

        // In the mutant, this should fail because it always considers room conflicts
        boolean mutantResult = mutationTests.mutant9_MethodCallRemoval("Jane", date, 151, 2);
        // The following assertion should fail with the mutant
        assertTrue(mutantResult, "Mutant should also allow different room on same date");
    }

    @Test
    @DisplayName("Test to detect Mutant 10: Math Operation (plusYears instead of minusYears)")
    void testDetectMutant10() {
        // This test should detect mutant10 which would reject valid dates
        LocalDate validDate = LocalDate.now().plusMonths(6); // A valid date within 1 year

        boolean originalResult = originalService.addReservation("John", validDate, 150, 2);
        boolean mutantResult = mutationTests.mutant10_MathOperatorReplacement("John", validDate, 150, 2);

        assertTrue(originalResult, "Original service should accept valid date");
        // The following assertion should fail with the mutant
        assertTrue(mutantResult, "Mutant should also accept valid date");
    }
}