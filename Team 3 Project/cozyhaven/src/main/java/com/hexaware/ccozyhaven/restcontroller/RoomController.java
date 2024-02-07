package com.hexaware.ccozyhaven.restcontroller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

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
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;
import com.hexaware.ccozyhaven.service.IRoomService;

@RestController
@RequestMapping("/cozyhaven-admin")
public class RoomController {

	@Autowired
	private IRoomService roomService;

	@PostMapping("/addrooms")
	public Room addRoom(@RequestBody RoomDTO roomDTO) {
		Room addedRoom = roomService.addRoom(roomDTO);
		return addedRoom;
	}

	@PutMapping("/editRoom/{roomId}")
	public Room editRoom(@PathVariable Long roomId, @RequestBody RoomDTO updatedRoomDTO)
			throws RoomNotFoundException {
		Room editedRoom = roomService.editRoom(roomId, updatedRoomDTO);
		return editedRoom;
	}

	@DeleteMapping("/removeRoom/{roomId}")
	public String removeRoom(@PathVariable Long roomId) throws RoomNotFoundException {
		roomService.removeRoom(roomId);
		return "Room removed successfully";
	}

	@GetMapping("/searchRooms")
	public List<Room> searchRooms(@RequestParam String location, @RequestParam LocalDate checkInDate,
			@RequestParam LocalDate checkOutDate, @RequestParam int numberOfRooms) {

		return roomService.searchRooms(location, checkInDate, checkOutDate, numberOfRooms);
	}

	@GetMapping("/rooms/{roomId}/availability")
	public boolean isRoomAvailable(@PathVariable Long roomId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate)
			throws RoomNotFoundException {
		return roomService.isRoomAvailable(roomId, checkInDate, checkOutDate);
	}

	@GetMapping("/rooms/{roomId}/calculateTotalFare")
	public double calculateTotalFare(@PathVariable Long roomId, @RequestParam int numberOfAdults,
			@RequestParam int numberOfChildren) throws RoomNotFoundException {
		return roomService.calculateTotalFare(roomId, numberOfAdults, numberOfChildren);
	}

}
