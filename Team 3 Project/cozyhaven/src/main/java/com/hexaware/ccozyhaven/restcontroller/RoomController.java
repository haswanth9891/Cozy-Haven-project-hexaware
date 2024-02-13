package com.hexaware.ccozyhaven.restcontroller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.ccozyhaven.dto.BookedRoomDTO;
import com.hexaware.ccozyhaven.dto.RoomDTO;
import com.hexaware.ccozyhaven.dto.SearchDTO;
import com.hexaware.ccozyhaven.entities.Room;

import com.hexaware.ccozyhaven.exceptions.HotelNotFoundException;
import com.hexaware.ccozyhaven.exceptions.HotelOwnerMismatchException;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;

import com.hexaware.ccozyhaven.service.IRoomService;
/*
 * Author: Haswanth
 * 
 * Controller description: Handles HTTP requests related to the Room entity.
 * It contains methods for registering a new Room, logging in, updating details, etc.
 */

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/room")
public class RoomController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoomController.class);

	private final IRoomService roomService;

	@Autowired
	public RoomController(IRoomService roomService) {
		this.roomService = roomService;
	}

	@PostMapping("/add/{hotelId}")
	@PreAuthorize("hasAuthority('HOTEL_OWNER')")
	public Room addRoomsToHotel(@RequestBody RoomDTO roomDTO, @PathVariable Long hotelId)
			throws HotelNotFoundException, HotelOwnerMismatchException {
		LOGGER.info("Received request to add a room to the hotel with ID: {}", hotelId);
		Room addedRoom = roomService.addRoomToHotel(roomDTO, hotelId);
		LOGGER.info("Room added successfully");
		return addedRoom;
	}

	@PutMapping("/edit/{roomId}")
	@PreAuthorize("hasAuthority('HOTEL_OWNER')")
	public Room editRoom(@PathVariable Long roomId, @RequestBody RoomDTO updatedRoomDTO) throws RoomNotFoundException {
		LOGGER.info("Received request to edit room with ID: {}", roomId);
		Room editedRoom = roomService.editRoom(roomId, updatedRoomDTO);
		LOGGER.info("Room edited successfully");
		return editedRoom;
	}

	@DeleteMapping("/remove/{roomId}")
	@PreAuthorize("hasAuthority('HOTEL_OWNER')")
	public String removeRoom(@PathVariable Long roomId) throws RoomNotFoundException {
		LOGGER.info("Received request to remove room with ID: {}", roomId);
		roomService.removeRoom(roomId);
		LOGGER.info("Room removed successfully");
		return "Room removed successfully";
	}

	@GetMapping("/search")
	public List<Room> searchRooms(@RequestBody @Valid SearchDTO  searchDTO) {
		LOGGER.info("Received request to search rooms in location: {} for the date range: {} to {}", searchDTO.getLocation(),
				searchDTO.getCheckInDate(), searchDTO.getCheckOutDate());

		return roomService.searchRooms(searchDTO.getLocation(), searchDTO.getCheckInDate(), searchDTO.getCheckOutDate());
	}

	@GetMapping("/availability/{roomId}")
	public boolean isRoomAvailable(@PathVariable Long roomId, @RequestBody @Valid SearchDTO searchDTO)
			throws RoomNotFoundException {
		LOGGER.info("Checking room availability for room ID {} in the date range: {} to {}", roomId, searchDTO.getCheckInDate(),
				searchDTO.getCheckInDate());
		return roomService.isRoomAvailable(roomId, searchDTO.getCheckInDate(),
				searchDTO.getCheckInDate());
	}

	@GetMapping("/calculate-fare-room")
	public double calculateTotalFare(@RequestBody  BookedRoomDTO bookedRoomDTO) throws RoomNotFoundException {
		LOGGER.info("Calculating total fare for room ID {} with {} adults and {} children", bookedRoomDTO.getRoomId(), bookedRoomDTO.getNumberOfAdults(),
				bookedRoomDTO.getNumberOfChildren());
		return roomService.calculateTotalFare( bookedRoomDTO.getRoomId(), bookedRoomDTO.getNumberOfAdults(),
				bookedRoomDTO.getNumberOfChildren());
	}

}
