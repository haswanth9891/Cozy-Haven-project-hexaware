package com.hexaware.ccozyhaven.service;



import java.util.List;

import org.springframework.stereotype.Repository;

import com.hexaware.ccozyhaven.dto.RoomDTO;
import com.hexaware.ccozyhaven.dto.UserDTO;
import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.entities.User;


@Repository
public interface IUserService {

	// User registration
    User registerUser(UserDTO userDTO);

    // User login
    boolean loginUser(String username, String password);

    // Search for hotel rooms based on specified criteria
    List<Hotel> searchByHotel(String location, String checkInDate, String checkOutDate, int numberOfRooms);

    // View details of available rooms in a selected hotel
    List<Room> viewRoomsInHotel(Long hotelId, String checkInDate, String checkOutDate);

    // Calculate total fare for a selected room and make a booking
    boolean reservationRoom(Long userId, Long roomId, int numberOfAdults, int numberOfChildren, String checkInDate, String checkOutDate);
    
    // View and manage user's booking history
    List<Reservation> viewReservationHistory(Long userId);
    
    // Cancel a booked room and request a refund
    boolean cancelReservation(Long reservationId);

    // Validate the selected dates for the duration of stay
    boolean validateSelectedDates(String checkInDate, String checkOutDate);
    
    // Get auto-suggestions for location based on user input
    List<String> getLocationSuggestions(String partialLocation);
    
    // Calculate the total fare for a room based on the number of people and age
    double calculateTotalFare(RoomDTO roomDTO, int numberOfAdults, int numberOfChildren);
    
 // View facilities available in a hotel
    Hotel viewHotel(Long hotelId);
}

