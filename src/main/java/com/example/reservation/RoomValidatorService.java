package com.example.reservation;

public class RoomValidatorService {

    public boolean isValidRoomNumber(int roomNumber) {
        return roomNumber >= 101 && roomNumber <= 199;
    }

    public boolean isValidGuestCount(int guestCount) {
        return guestCount >= 1 && guestCount <= 4;
    }
}
