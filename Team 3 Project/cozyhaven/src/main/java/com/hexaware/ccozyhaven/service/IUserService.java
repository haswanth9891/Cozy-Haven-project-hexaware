package com.hexaware.ccozyhaven.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hexaware.ccozyhaven.dto.UserDTO;
import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.HotelOwnerNotFoundException;
import com.hexaware.ccozyhaven.exceptions.InvalidCancellationException;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.RoomNotAvailableException;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;

@Repository
public interface IUserService {

//	// User registration
//    User registerUser(UserDTO userDTO);
//
//    // User login
//    boolean loginUser(String username, String password);
	// add the user
	// public User addUser(User user);

	User updateUser(Long userId, UserDTO updatedUserDTO) throws UserNotFoundException;

	// search for hotel rooms
	//public List<Room> searchRooms(String location, LocalDate checkInDate, LocalDate checkOutDate, int numberOfRooms);

	// to get list of hotels
	//public List<Hotel> getAllHotels();

	// selecting hotel by ID
	//public Hotel getHotelDetailsById(Long hotelId);

	// list available rooms in a specific hotelbyID
	//List<Room> getAllAvailableRoomsInHotel(Long hotelId);

	// to view details of available rooms in a selected hotel
	//boolean isRoomAvailable(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) throws RoomNotFoundException;

	// to Calculate the total fare for a room based on the number of people and age
	//double calculateTotalFare(Long roomId, int numberOfAdults, int numberOfChildren) throws RoomNotFoundException;

	// to Calculate the total fare for a selected room and make a booking
//	boolean reservationRoom(Long userId, Long roomId, int numberOfAdults, int numberOfChildren, LocalDate checkInDate,
//			LocalDate checkOutDate) throws RoomNotAvailableException, RoomNotFoundException, UserNotFoundException;

	// to Search for hotel rooms based on specified criteria
	// List<Hotel> searchByHotel(String location, String checkInDate, String
	// checkOutDate, int numberOfRooms);

	// List of reservations done by user
//	List<Reservation> getUserReservations(Long userId);
//
//	// Cancel a booked room and request a refund
//	void cancelReservation(Long userId, Long reservationId) throws ReservationNotFoundException;
//
//	// Cancel
//	void cancelReservationAndRequestRefund(Long userId, Long reservationId)
//			throws InvalidCancellationException, ReservationNotFoundException;

	// Get auto-suggestions for location based on user input
	// List<String> getLocationSuggestions(String partialLocation);

}
