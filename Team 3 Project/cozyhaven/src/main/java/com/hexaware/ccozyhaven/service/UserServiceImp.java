package com.hexaware.ccozyhaven.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hexaware.ccozyhaven.dto.RoomDTO;
import com.hexaware.ccozyhaven.dto.UserDTO;
import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.repository.HotelRepository;
import com.hexaware.ccozyhaven.repository.ReservationRepository;
import com.hexaware.ccozyhaven.repository.UserRepository;

public class UserServiceImp implements IUserService{
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ReservationRepository reservationRepo;
	
	@Autowired
	HotelRepository hotelrepo;

	@Override
	public User registerUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean loginUser(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Hotel> searchByHotel(String location, String checkInDate, String checkOutDate, int numberOfRooms) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Room> viewRoomsInHotel(Long hotelId, String checkInDate, String checkOutDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean reservationRoom(Long userId, Long roomId, int numberOfAdults, int numberOfChildren,
			String checkInDate, String checkOutDate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Reservation> viewReservationHistory(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean cancelReservation(Long reservationId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateSelectedDates(String checkInDate, String checkOutDate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getLocationSuggestions(String partialLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double calculateTotalFare(RoomDTO roomDTO, int numberOfAdults, int numberOfChildren) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Hotel viewHotel(Long hotelId) {
		// TODO Auto-generated method stub
		return null;
	}

}
