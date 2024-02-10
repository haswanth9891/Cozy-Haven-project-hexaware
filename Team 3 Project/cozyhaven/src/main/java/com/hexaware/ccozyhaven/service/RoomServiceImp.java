package com.hexaware.ccozyhaven.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.hexaware.ccozyhaven.config.UserInfoUserDetails;
import com.hexaware.ccozyhaven.dto.RoomDTO;
import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.exceptions.AuthorizationException;
import com.hexaware.ccozyhaven.exceptions.HotelNotFoundException;
import com.hexaware.ccozyhaven.exceptions.HotelOwnerMismatchException;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UnauthorizedAccessException;
import com.hexaware.ccozyhaven.repository.HotelRepository;
import com.hexaware.ccozyhaven.repository.ReservationRepository;
import com.hexaware.ccozyhaven.repository.RoomRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoomServiceImp implements IRoomService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoomServiceImp.class);

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	HotelRepository hotelRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Override
	public Room addRoomToHotel(RoomDTO roomDTO, Long hotelId)
			throws HotelNotFoundException, HotelOwnerMismatchException, UnauthorizedAccessException {
		LOGGER.info("Adding room to hotel");

		// Retrieve the authenticated user details from the security context
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Check if the user is authenticated and has a valid JWT token
		if (authentication != null && authentication.isAuthenticated()) {
			UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();

			// Assuming your user entity has a method to get the hotel owner ID, replace
			// this with your actual method
			Long hotelOwnerId = userDetails.getHotelOwnerId();

			// Check if the hotel owner is the owner of the specified hotel
			Hotel hotel = hotelRepository.findById(hotelId)
					.orElseThrow(() -> new HotelNotFoundException("Hotel not found with id: " + hotelId));

			if (!hotel.getHotelOwner().getHotelOwnerId().equals(hotelOwnerId)) {
				throw new HotelOwnerMismatchException(
						"The authenticated user is not the owner of the specified hotel.");
			}
		} else {
			throw new UnauthorizedAccessException("User not authenticated or invalid JWT token.");
		}
		Room room = new Room();
		room.setRoomSize(roomDTO.getRoomSize());
		room.setBedType(roomDTO.getBedType());
		room.setMaxOccupancy(roomDTO.getMaxOccupancy());
		room.setBaseFare(roomDTO.getBaseFare());
		room.setAC(roomDTO.isAC());
		room.setAvailabilityStatus(roomDTO.isAvailabilityStatus());

		Room savedRoom = roomRepository.save(room);
		roomRepository.addRoomToHotel(savedRoom.getRoomId(), hotelId);

		LOGGER.info("Room added to hotel successfully");
		return savedRoom;
	}

	@Override
	public Room editRoom(Long roomId, RoomDTO updatedRoomDTO)
			throws RoomNotFoundException, UnauthorizedAccessException, AuthorizationException {
		LOGGER.info("Editing room with ID {}", roomId);
		// Retrieve the authenticated user details from the security context
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Check if the user is authenticated and has a valid role
		if (authentication != null && authentication.isAuthenticated()) {
			UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();

			// Extract the hotel owner ID from the authenticated user
			Long hotelOwnerId = userDetails.getHotelOwnerId();

			// Retrieve the room
			Room existingRoom = roomRepository.findById(roomId)
					.orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + roomId));

			// Check if the hotel owner is the owner of the room
			if (!existingRoom.getHotel().getHotelOwner().getHotelOwnerId().equals(hotelOwnerId)) {
				throw new AuthorizationException("You are not authorized to edit this room.");
			}

			existingRoom.setRoomSize(updatedRoomDTO.getRoomSize());
			existingRoom.setBedType(updatedRoomDTO.getBedType());
			existingRoom.setMaxOccupancy(updatedRoomDTO.getMaxOccupancy());
			existingRoom.setBaseFare(updatedRoomDTO.getBaseFare());
			existingRoom.setAC(updatedRoomDTO.isAC());
			existingRoom.setAvailabilityStatus(updatedRoomDTO.isAvailabilityStatus());

			LOGGER.info("Room with ID {} edited successfully", roomId);
			return roomRepository.save(existingRoom);
		} else {
			throw new UnauthorizedAccessException("User not authenticated or invalid JWT token.");
		}

	}

	@Override
	public void removeRoom(Long roomId) throws RoomNotFoundException, UnauthorizedAccessException, AuthorizationException {
		LOGGER.info("Removing room with ID {}", roomId);
		// Retrieve the authenticated user details from the security context
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Check if the user is authenticated and has a valid role
		if (authentication != null && authentication.isAuthenticated()) {
			UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();

			// Extract the hotel owner ID from the authenticated user
			Long hotelOwnerId = userDetails.getHotelOwnerId();

			// Retrieve the room to be deleted
			Room roomToDelete = roomRepository.findById(roomId)
					.orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + roomId));

			// Check if the hotel owner is the owner of the room
			if (!roomToDelete.getHotel().getHotelOwner().getHotelOwnerId().equals(hotelOwnerId)) {
				throw new AuthorizationException("You are not authorized to remove this room.");
			}

			roomRepository.delete(roomToDelete);
			LOGGER.info("Room with ID {} removed successfully", roomId);
		} else {
			throw new UnauthorizedAccessException("User not authenticated or invalid JWT token.");
		}

	}

	@Override
	public List<Room> searchRooms(String location, LocalDate checkInDate, LocalDate checkOutDate) {
		LOGGER.info("Searching rooms");
		if (checkInDate.isAfter(checkOutDate)) {
			throw new IllegalArgumentException("Check-in date must be before or equal to check-out date");
		}
		List<Room> availableRooms = roomRepository.findAvailableRooms(location, checkInDate, checkOutDate);
		LOGGER.info("Rooms searched successfully");
		return availableRooms;
	}

	@Override
	public boolean isRoomAvailable(Long roomId, LocalDate checkInDate, LocalDate checkOutDate)
			throws RoomNotFoundException {
		LOGGER.info("Checking room availability with ID {}", roomId);
		Optional<Room> optionalRoom = roomRepository.findById(roomId);

		if (optionalRoom.isPresent()) {
			Room room = optionalRoom.get();

			List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(roomId,
					checkInDate, checkOutDate);

			return overlappingReservations.isEmpty();

		} else {
			LOGGER.error("Room not found with ID: {}", roomId);
			throw new RoomNotFoundException("Room not found with id: " + roomId);
		}

	}

	@Override
	public double calculateTotalFare(Long roomId, int numberOfAdults, int numberOfChildren)
			throws RoomNotFoundException {
		LOGGER.info("Calculating total fare for room with ID {}", roomId);
		if (numberOfAdults <= 0) {
			throw new IllegalArgumentException("Number of adults must be greater than zero.");
		}

		Optional<Room> optionalRoom = roomRepository.findById(roomId);

		if (optionalRoom.isPresent()) {
			Room room = optionalRoom.get();
			double baseFare = room.getBaseFare();

			int maxCapacity = calculateMaxCapacity(room);
			int occupancy = room.getMaxOccupancy() + 1;

			int totalPeople = numberOfAdults + numberOfChildren;
			if (totalPeople > maxCapacity) {
				throw new IllegalArgumentException("Number of people exceeds the room's maximum capacity.");
			}
			double additionalCharge = 0;
			if (totalPeople > occupancy) {

				if (numberOfAdults == occupancy) {
					for (int i = 1; i <= numberOfChildren; i++) {
						additionalCharge += baseFare * 0.4;
					}

				} else if (numberOfAdults > occupancy) {
					int remainingAdults = 0;
					remainingAdults = numberOfAdults - occupancy;
					for (int i = 1; i <= remainingAdults; i++) {
						additionalCharge += baseFare * 0.6;
					}
					for (int i = 1; i <= numberOfChildren; i++) {
						additionalCharge += baseFare * 0.4;
					}
				} else {
					int remainingChildren = 0;
					remainingChildren = maxCapacity - occupancy;
					for (int i = 1; i <= remainingChildren; i++) {
						additionalCharge += baseFare * 0.4;
					}

				}
			}

			double totalFare = baseFare + additionalCharge;
			LOGGER.info("Total fare calculated successfully: {}", totalFare);
			return totalFare;
		} else {
			throw new RoomNotFoundException("Room not found with id: " + roomId);
		}
	}

	private int calculateMaxCapacity(Room room) {

		switch (room.getBedType().toLowerCase()) {
		case "single bed":
			return 2;
		case "double bed":
			return 4;
		default:
			return 6;
		}
	}

}
