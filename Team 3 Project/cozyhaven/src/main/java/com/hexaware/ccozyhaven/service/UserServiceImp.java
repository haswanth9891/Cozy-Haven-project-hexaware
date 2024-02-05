package com.hexaware.ccozyhaven.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.ccozyhaven.dto.HotelOwnerDTO;
import com.hexaware.ccozyhaven.dto.UserDTO;
import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.entities.Reservation;

import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.HotelOwnerNotFoundException;
import com.hexaware.ccozyhaven.exceptions.InvalidCancellationException;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.RoomNotAvailableException;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;
import com.hexaware.ccozyhaven.repository.HotelRepository;
import com.hexaware.ccozyhaven.repository.ReservationRepository;
import com.hexaware.ccozyhaven.repository.RoomRepository;
import com.hexaware.ccozyhaven.repository.UserRepository;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class UserServiceImp implements IUserService {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private HotelRepository hotelRepository;

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
//	@Override
//	public User addUser(User user) {
//		
//		return userRepository.save(user);
//	}

	
	@Override
	public User updateUser(Long userId, UserDTO updatedUserDTO)
			throws UserNotFoundException {

		User existingUser = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

		existingUser.setEmail(updatedUserDTO.getEmail());

		existingUser.setFirstName(updatedUserDTO.getFirstName());

		existingUser.setLastName(updatedUserDTO.getLastName());
		existingUser.setContactNumber(updatedUserDTO.getContactNumber());
		existingUser.setGender(updatedUserDTO.getGender());
		existingUser.setAddress(updatedUserDTO.getAddress());

		return userRepository.save(existingUser);

	}

	
	
	@Override
	public List<Room> searchRooms(String location, LocalDate checkInDate, LocalDate checkOutDate, int numberOfRooms) {
		if (checkInDate.isAfter(checkOutDate)) {
			throw new IllegalArgumentException("Check-in date must be before or equal to check-out date");
		}
		List<Room> availableRooms = roomRepository.findAvailableRooms(location, checkInDate, checkOutDate,
				numberOfRooms);
		return availableRooms;

	}

	@Override
	public List<Hotel> getAllHotels() {
		return hotelRepository.findAll();

	}

	@Override
	public Hotel getHotelDetailsById(Long hotelId) {
		return hotelRepository.findById(hotelId).orElse(null);

	}

	@Override
	public List<Room> getAllAvailableRoomsInHotel(Long hotelId) {
		return hotelRepository.findByHotelIdAndRoomAvailabilityStatus(hotelId, true);

	}

	@Override
	public boolean isRoomAvailable(Long roomId, LocalDate checkInDate, LocalDate checkOutDate)
			throws RoomNotFoundException {
		Optional<Room> optionalRoom = roomRepository.findById(roomId);

		if (optionalRoom.isPresent()) {
			Room room = optionalRoom.get();

			
			List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(roomId,
					checkInDate, checkOutDate);

			
			return overlappingReservations.isEmpty();
		} else {
			throw new RoomNotFoundException("Room not found with id: " + roomId);
		}
	}

	@Override
	public double calculateTotalFare(Long roomId, int numberOfAdults, int numberOfChildren)
			throws RoomNotFoundException {
		Optional<Room> optionalRoom = roomRepository.findById(roomId);

		if (optionalRoom.isPresent()) {
			Room room = optionalRoom.get();
			double baseFare = room.getBaseFare();

			
			int maxCapacity = calculateMaxCapacity(room);

			
			int totalPeople = numberOfAdults + numberOfChildren;
			if (totalPeople > maxCapacity) {
				throw new IllegalArgumentException("Number of people exceeds the room's maximum capacity.");
			}

			
			double additionalCharge = 0;
			for (int i = 1; i <= numberOfAdults; i++) {
				additionalCharge += calculateAdditionalCharge(i, true);
			}
			for (int i = 1; i <= numberOfChildren; i++) {
				additionalCharge += calculateAdditionalCharge(i, false);
			}

			
			double totalFare = baseFare + additionalCharge;

			return totalFare;
		} else {
			throw new RoomNotFoundException("Room not found with id: " + roomId);
		}
	}

	private int calculateMaxCapacity(Room room) {
		
		
		switch (room.getBedType()) {
		case "Single Bed":
			return 2;
		case "Double Bed":
			return 4;
		default:
			return 6;
		}
	}

	private double calculateAdditionalCharge(int personIndex, boolean isAdult) {
		
		if (isAdult) {
			return 0.4; 
		} else {
			return 0.2; 
		}
	}

	@Override
	public boolean reservationRoom(Long userId, Long roomId, int numberOfAdults, int numberOfChildren,
			LocalDate checkInDate, LocalDate checkOutDate) throws RoomNotAvailableException, RoomNotFoundException, UserNotFoundException {
		
		
	    if (!isRoomAvailable(roomId, checkInDate, checkOutDate)) {
	        throw new RoomNotAvailableException("Room is not available for the selected date range.");
	    }

	    
	    Room room = roomRepository.findById(roomId)
	            .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + roomId));

	    Reservation reservation = new Reservation();
	    reservation.setNumberOfAdults(numberOfAdults);
	    reservation.setNumberOfChildren(numberOfChildren);
	    reservation.setCheckInDate(checkInDate);
	    reservation.setCheckOutDate(checkOutDate);

	   
	    double totalFare = calculateTotalFare(room.getRoomId(), numberOfAdults, numberOfChildren);
	    reservation.setTotalAmount(totalFare);

	   
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
	    reservation.setUser(user);

	   
	    Set<Room> rooms = new HashSet<>();
	    rooms.add(room);
	    reservation.setRooms(rooms);

	   
	    reservationRepository.save(reservation);

	    return true;

	}

	@Override
	public List<Reservation> getUserReservations(Long userId) {

		return reservationRepository.findByUser_UserId(userId);
	}

	@Override
	public void cancelReservation(Long userId, Long reservationId) throws ReservationNotFoundException {
		Reservation reservation = reservationRepository.findByReservationIdAndUser_UserId(reservationId, userId)
				.orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + reservationId));

		
		reservation.setReservationStatus("CANCELLED");
		reservationRepository.save(reservation);

	}

	@Override
	public void cancelReservationAndRequestRefund(Long userId, Long reservationId)
			throws InvalidCancellationException, ReservationNotFoundException {
		
		Reservation reservation = reservationRepository.findByReservationIdAndUser_UserId(reservationId, userId)
				.orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + reservationId));

		
		if (reservation.getReservationStatus() != "CANCELLED") {
			
			reservation.setReservationStatus("CANCELLED");
			reservationRepository.save(reservation);
		} else {
			throw new InvalidCancellationException("Reservation is already cancelled.");
		}

	}



	
	

}
