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

import com.hexaware.ccozyhaven.dto.RoomDTO;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.exceptions.AuthorizationException;
import com.hexaware.ccozyhaven.exceptions.HotelNotFoundException;
import com.hexaware.ccozyhaven.exceptions.HotelOwnerMismatchException;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UnauthorizedAccessException;
import com.hexaware.ccozyhaven.service.IRoomService;

@RestController
@RequestMapping("/cozyhaven-room")
public class RoomController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomController.class);


	@Autowired
	private IRoomService roomService;

	@PostMapping("/add-room")
	@PreAuthorize("hasAuthority('HOTEL_OWNER')")
    public Room addRoomsToHotel(@RequestBody RoomDTO roomDTO, @RequestParam Long hotelId) throws HotelNotFoundException, HotelOwnerMismatchException, UnauthorizedAccessException {
		LOGGER.info("Received request to add a room to the hotel with ID: {}", hotelId);
        Room addedRoom = roomService.addRoomToHotel(roomDTO, hotelId);
        LOGGER.info("Room added successfully");
        return addedRoom;
    }

	@PutMapping("/edit/{roomId}")
	@PreAuthorize("hasAuthority('HOTEL_OWNER')")
	public Room editRoom(@PathVariable Long roomId, @RequestBody RoomDTO updatedRoomDTO)
			throws RoomNotFoundException, UnauthorizedAccessException, AuthorizationException {
		 LOGGER.info("Received request to edit room with ID: {}", roomId);
		Room editedRoom = roomService.editRoom(roomId, updatedRoomDTO);
		 LOGGER.info("Room edited successfully");
		return editedRoom;
	}

	@DeleteMapping("/remove/{roomId}")
	@PreAuthorize("hasAuthority('HOTEL_OWNER')")
	public String removeRoom(@PathVariable Long roomId) throws RoomNotFoundException, UnauthorizedAccessException, AuthorizationException {
		 LOGGER.info("Received request to remove room with ID: {}", roomId);
		roomService.removeRoom(roomId);
		LOGGER.info("Room removed successfully");
		return "Room removed successfully";
	}

	@GetMapping("/search")
	public List<Room> searchRooms(@RequestParam String location, @RequestParam LocalDate checkInDate,
			@RequestParam LocalDate checkOutDate) {
		 LOGGER.info("Received request to search rooms in location: {} for the date range: {} to {}", location,
	                checkInDate, checkOutDate);

		return roomService.searchRooms(location, checkInDate, checkOutDate);
	}

	@GetMapping("/availability/{roomId}")
	public boolean isRoomAvailable(@PathVariable Long roomId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate)
			throws RoomNotFoundException {
		 LOGGER.info("Checking room availability for room ID {} in the date range: {} to {}", roomId, checkInDate, checkOutDate);
		return roomService.isRoomAvailable(roomId, checkInDate, checkOutDate);
	}

	@GetMapping("/calculateTotalFare/{roomId}")
	public double calculateTotalFare(@PathVariable Long roomId, @RequestParam int numberOfAdults,
			@RequestParam int numberOfChildren) throws RoomNotFoundException {
		 LOGGER.info("Calculating total fare for room ID {} with {} adults and {} children", roomId, numberOfAdults, numberOfChildren);
		return roomService.calculateTotalFare(roomId, numberOfAdults, numberOfChildren);
	}

}
