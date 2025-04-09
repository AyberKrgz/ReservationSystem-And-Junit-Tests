package com.example.reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class MutatedReservation {

    // Reference to original class for testing
    private ReservationService originalService = new ReservationService();
    private final List<Reservation> reservations = new ArrayList<>();

    /**
     * MUTANT 1: Condition Boundary Mutation
     * Change: roomNumber<101 -> roomNumber<=101
     */
    public boolean mutant1_RoomNumberBoundary(String customerName, LocalDate date, Integer roomNumber, Integer guestCount) {
        Reservation newReservation = new Reservation(customerName, date, roomNumber, guestCount);

        if (customerName == null || date == null || roomNumber == null || guestCount == null) {
            throw new IllegalArgumentException("Name, Date-Time, Room Number and/or Guest Count fields must be filled.");
        }

        if (date.isBefore(LocalDate.now())) {
            return false;
        }

        if(date.minusYears(1).isAfter(LocalDate.now())){
            return false;
        }

        // MUTATION: Changed roomNumber<101 to roomNumber<=101
        if (roomNumber<=101 || roomNumber>199){
            throw new IndexOutOfBoundsException("Room number must be selected between 101-199 (101 and 199 included).");
        }

        if(guestCount<1 || guestCount>4){
            throw new IndexOutOfBoundsException("Guest count must be between 1-4. (1 and 4 included)");
        }

        for (Reservation res : reservations) {
            if (res.getRoomNumber().equals(roomNumber) && res.getDateTime().equals(date)) {
                return false;
            }
        }

        reservations.add(newReservation);
        return true;
    }

    /**
     * MUTANT 2: Condition Boundary Mutation
     * Change: roomNumber>199 -> roomNumber>=199
     */
    public boolean mutant2_RoomNumberUpperBoundary(String customerName, LocalDate date, Integer roomNumber, Integer guestCount) {
        Reservation newReservation = new Reservation(customerName, date, roomNumber, guestCount);

        if (customerName == null || date == null || roomNumber == null || guestCount == null) {
            throw new IllegalArgumentException("Name, Date-Time, Room Number and/or Guest Count fields must be filled.");
        }

        if (date.isBefore(LocalDate.now())) {
            return false;
        }

        if(date.minusYears(1).isAfter(LocalDate.now())){
            return false;
        }

        // MUTATION: Changed roomNumber>199 to roomNumber>=199
        if (roomNumber<100 || roomNumber>=199){
            throw new IndexOutOfBoundsException("Room number must be selected between 101-199 (101 and 199 included).");
        }

        if(guestCount<1 || guestCount>4){
            throw new IndexOutOfBoundsException("Guest count must be between 1-4. (1 and 4 included)");
        }

        for (Reservation res : reservations) {
            if (res.getRoomNumber().equals(roomNumber) && res.getDateTime().equals(date)) {
                return false;
            }
        }

        reservations.add(newReservation);
        return true;
    }

    /**
     * MUTANT 3: Conditional Operator Replacement
     * Change: guestCount<1 -> guestCount<=1
     */
    public boolean mutant3_GuestCountOperator(String customerName, LocalDate date, Integer roomNumber, Integer guestCount) {
        Reservation newReservation = new Reservation(customerName, date, roomNumber, guestCount);

        if (customerName == null || date == null || roomNumber == null || guestCount == null) {
            throw new IllegalArgumentException("Name, Date-Time, Room Number and/or Guest Count fields must be filled.");
        }

        if (date.isBefore(LocalDate.now())) {
            return false;
        }

        if(date.minusYears(1).isAfter(LocalDate.now())){
            return false;
        }

        if (roomNumber<100 || roomNumber>199){
            throw new IndexOutOfBoundsException("Room number must be selected between 101-199 (101 and 199 included).");
        }

        // MUTATION: Changed guestCount<1 to guestCount<=1
        if(guestCount<=1 || guestCount>4){
            throw new IndexOutOfBoundsException("Guest count must be between 1-4. (1 and 4 included)");
        }

        for (Reservation res : reservations) {
            if (res.getRoomNumber().equals(roomNumber) && res.getDateTime().equals(date)) {
                return false;
            }
        }

        reservations.add(newReservation);
        return true;
    }

    /**
     * MUTANT 4: Return Value Mutation
     * Change: return true; -> return false;
     */
    public boolean mutant4_ReturnValueMutation(String customerName, LocalDate date, Integer roomNumber, Integer guestCount) {
        Reservation newReservation = new Reservation(customerName, date, roomNumber, guestCount);

        if (customerName == null || date == null || roomNumber == null || guestCount == null) {
            throw new IllegalArgumentException("Name, Date-Time, Room Number and/or Guest Count fields must be filled.");
        }

        if (date.isBefore(LocalDate.now())) {
            return false;
        }

        if(date.minusYears(1).isAfter(LocalDate.now())){
            return false;
        }

        if (roomNumber<100 || roomNumber>199){
            throw new IndexOutOfBoundsException("Room number must be selected between 101-199 (101 and 199 included).");
        }

        if(guestCount<1 || guestCount>4){
            throw new IndexOutOfBoundsException("Guest count must be between 1-4. (1 and 4 included)");
        }

        for (Reservation res : reservations) {
            if (res.getRoomNumber().equals(roomNumber) && res.getDateTime().equals(date)) {
                return false;
            }
        }

        reservations.add(newReservation);
        // MUTATION: Changed return true to return false
        return false;
    }

    /**
     * MUTANT 5: Void Method Call Removal
     * Change: reservations.add(newReservation); -> (removed)
     */
    public boolean mutant5_RemoveMethodCall(String customerName, LocalDate date, Integer roomNumber, Integer guestCount) {
        Reservation newReservation = new Reservation(customerName, date, roomNumber, guestCount);

        if (customerName == null || date == null || roomNumber == null || guestCount == null) {
            throw new IllegalArgumentException("Name, Date-Time, Room Number and/or Guest Count fields must be filled.");
        }

        if (date.isBefore(LocalDate.now())) {
            return false;
        }

        if(date.minusYears(1).isAfter(LocalDate.now())){
            return false;
        }

        if (roomNumber<100 || roomNumber>199){
            throw new IndexOutOfBoundsException("Room number must be selected between 101-199 (101 and 199 included).");
        }

        if(guestCount<1 || guestCount>4){
            throw new IndexOutOfBoundsException("Guest count must be between 1-4. (1 and 4 included)");
        }

        for (Reservation res : reservations) {
            if (res.getRoomNumber().equals(roomNumber) && res.getDateTime().equals(date)) {
                return false;
            }
        }

        // MUTATION: Removed the line that adds reservation to the list
        // reservations.add(newReservation);
        return true;
    }

    /**
     * MUTANT 6: Negated Conditional
     * Change: date.isBefore(LocalDate.now()) -> !date.isBefore(LocalDate.now())
     */
    public boolean mutant6_NegatedConditional(String customerName, LocalDate date, Integer roomNumber, Integer guestCount) {
        Reservation newReservation = new Reservation(customerName, date, roomNumber, guestCount);

        if (customerName == null || date == null || roomNumber == null || guestCount == null) {
            throw new IllegalArgumentException("Name, Date-Time, Room Number and/or Guest Count fields must be filled.");
        }

        // MUTATION: Negated the condition from date.isBefore() to !date.isBefore()
        if (!date.isBefore(LocalDate.now())) {
            return false;
        }

        if(date.minusYears(1).isAfter(LocalDate.now())){
            return false;
        }

        if (roomNumber<100 || roomNumber>199){
            throw new IndexOutOfBoundsException("Room number must be selected between 101-199 (101 and 199 included).");
        }

        if(guestCount<1 || guestCount>4){
            throw new IndexOutOfBoundsException("Guest count must be between 1-4. (1 and 4 included)");
        }

        for (Reservation res : reservations) {
            if (res.getRoomNumber().equals(roomNumber) && res.getDateTime().equals(date)) {
                return false;
            }
        }

        reservations.add(newReservation);
        return true;
    }

    /**
     * MUTANT 7: Removed Conditional
     * Change: if (customerName == null || date == null || roomNumber == null || guestCount == null) -> removed
     */
    public boolean mutant7_RemovedConditional(String customerName, LocalDate date, Integer roomNumber, Integer guestCount) {
        Reservation newReservation = new Reservation(customerName, date, roomNumber, guestCount);

        // MUTATION: Removed the null check conditional
        // if (customerName == null || date == null || roomNumber == null || guestCount == null) {
        //     throw new IllegalArgumentException("Name, Date-Time, Room Number and/or Guest Count fields must be filled.");
        // }

        if (date.isBefore(LocalDate.now())) {
            return false;
        }

        if(date.minusYears(1).isAfter(LocalDate.now())){
            return false;
        }

        if (roomNumber<100 || roomNumber>199){
            throw new IndexOutOfBoundsException("Room number must be selected between 101-199 (101 and 199 included).");
        }

        if(guestCount<1 || guestCount>4){
            throw new IndexOutOfBoundsException("Guest count must be between 1-4. (1 and 4 included)");
        }

        for (Reservation res : reservations) {
            if (res.getRoomNumber().equals(roomNumber) && res.getDateTime().equals(date)) {
                return false;
            }
        }

        reservations.add(newReservation);
        return true;
    }

    /**
     * MUTANT 8: Constructor Call Mutation
     * Change: new Reservation(...) -> null
     */
    public boolean mutant8_ConstructorCallMutation(String customerName, LocalDate date, Integer roomNumber, Integer guestCount) {
        // MUTATION: Changed constructor call to null
        Reservation newReservation = null;

        if (customerName == null || date == null || roomNumber == null || guestCount == null) {
            throw new IllegalArgumentException("Name, Date-Time, Room Number and/or Guest Count fields must be filled.");
        }

        if (date.isBefore(LocalDate.now())) {
            return false;
        }

        if(date.minusYears(1).isAfter(LocalDate.now())){
            return false;
        }

        if (roomNumber<100 || roomNumber>199){
            throw new IndexOutOfBoundsException("Room number must be selected between 101-199 (101 and 199 included).");
        }

        if(guestCount<1 || guestCount>4){
            throw new IndexOutOfBoundsException("Guest count must be between 1-4. (1 and 4 included)");
        }

        for (Reservation res : reservations) {
            if (res.getRoomNumber().equals(roomNumber) && res.getDateTime().equals(date)) {
                return false;
            }
        }

        reservations.add(newReservation); // This will likely cause a NullPointerException
        return true;
    }

    /**
     * MUTANT 9: Method Call Removal
     * Change: res.getRoomNumber().equals(roomNumber) -> true
     */
    public boolean mutant9_MethodCallRemoval(String customerName, LocalDate date, Integer roomNumber, Integer guestCount) {
        Reservation newReservation = new Reservation(customerName, date, roomNumber, guestCount);

        if (customerName == null || date == null || roomNumber == null || guestCount == null) {
            throw new IllegalArgumentException("Name, Date-Time, Room Number and/or Guest Count fields must be filled.");
        }

        if (date.isBefore(LocalDate.now())) {
            return false;
        }

        if(date.minusYears(1).isAfter(LocalDate.now())){
            return false;
        }

        if (roomNumber<100 || roomNumber>199){
            throw new IndexOutOfBoundsException("Room number must be selected between 101-199 (101 and 199 included).");
        }

        if(guestCount<1 || guestCount>4){
            throw new IndexOutOfBoundsException("Guest count must be between 1-4. (1 and 4 included)");
        }

        for (Reservation res : reservations) {
            // MUTATION: Replaced method call with true
            if (true && res.getDateTime().equals(date)) {
                return false;
            }
        }

        reservations.add(newReservation);
        return true;
    }

    /**
     * MUTANT 10: Math Operator Replacement
     * Change: date.minusYears(1) -> date.plusYears(1)
     */
    public boolean mutant10_MathOperatorReplacement(String customerName, LocalDate date, Integer roomNumber, Integer guestCount) {
        Reservation newReservation = new Reservation(customerName, date, roomNumber, guestCount);

        if (customerName == null || date == null || roomNumber == null || guestCount == null) {
            throw new IllegalArgumentException("Name, Date-Time, Room Number and/or Guest Count fields must be filled.");
        }

        if (date.isBefore(LocalDate.now())) {
            return false;
        }

        // MUTATION: Changed minusYears to plusYears
        if(date.plusYears(1).isAfter(LocalDate.now())){
            return false;
        }

        if (roomNumber<100 || roomNumber>199){
            throw new IndexOutOfBoundsException("Room number must be selected between 101-199 (101 and 199 included).");
        }

        if(guestCount<1 || guestCount>4){
            throw new IndexOutOfBoundsException("Guest count must be between 1-4. (1 and 4 included)");
        }

        for (Reservation res : reservations) {
            if (res.getRoomNumber().equals(roomNumber) && res.getDateTime().equals(date)) {
                return false;
            }
        }

        reservations.add(newReservation);
        return true;
    }
}