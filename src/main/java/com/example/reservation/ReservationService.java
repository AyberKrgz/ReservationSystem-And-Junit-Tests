package com.example.reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private final List<Reservation> reservations = new ArrayList<>();       //List of reservations

    private final RoomValidatorService validatorService = new RoomValidatorService();
    private final ReservationConflictChecker conflictChecker = new ReservationConflictChecker();


    //Add a reservation to the list.
    public boolean addReservation(String customerName, LocalDate date, Integer roomNumber, Integer guestCount) {

        Reservation newReservation = new Reservation(customerName, date, roomNumber, guestCount);

        //Checking if all fields are filled.
        if (customerName == null || date == null || roomNumber == null || guestCount == null) {
            throw new IllegalArgumentException("Name, Date-Time, Room Number and/or Guest Count fields must be filled.");
        }

        //Reservations for past dates cannot be made.
        if (date.isBefore(LocalDate.now())) {return false;}

        //Reservations can be made up to 1 year later.
        if(date.minusYears(1).isAfter(LocalDate.now())) {return false;}

        //Customers can only select room numbers between 101-199 (101 and 199 included).
        if (!validatorService.isValidRoomNumber(roomNumber)){
            throw new IndexOutOfBoundsException("Room number must be selected between 101-199 (101 and 199 included).");
        }

        //Guest count must be between 1-4. (1 and 4 included)
        if(!validatorService.isValidGuestCount(guestCount)){
            throw new IndexOutOfBoundsException("Guest count must be between 1-4. (1 and 4 included)");
        }

        //Checking conflict
        if (conflictChecker.hasConflict(reservations, roomNumber, date)) {return false;}


        reservations.add(newReservation);   //No conflict. Add the reservation
        return true;

    }

    //Remove the reservation from the list.
    public boolean cancelReservation(String customerName, LocalDate date, Integer roomNumber, Integer guestCount) {
        return reservations.removeIf(res -> res.getCustomerName().equals(customerName) &&
                                            res.getDateTime().equals(date) &&
                                            res.getRoomNumber().equals(roomNumber) &&
                                            res.getGuestCount().equals(guestCount));

    }

    //Search and find a reservation from the list.
    public Reservation findReservation(String name, Integer room) {
        for (Reservation reservation : reservations) {
            if (reservation.getCustomerName().equals(name) && reservation.getRoomNumber().equals(room)) {
                return reservation;
            }
        }
        return null; //If no reservations found return null.
    }

    //List and show all the reservations in the list.
    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }

}
