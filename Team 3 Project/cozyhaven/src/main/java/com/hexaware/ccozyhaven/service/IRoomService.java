package com.hexaware.ccozyhaven.service;

import java.time.LocalDate;
import java.util.List;

import com.hexaware.ccozyhaven.dto.RoomDTO;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;

public interface IRoomService {

	Room addRoomToHotel(RoomDTO roomDTO, Long hotelId);

	public Room editRoom(Long roomId, RoomDTO updatedRoomDTO) throws RoomNotFoundException;

	void removeRoom(Long roomId) throws RoomNotFoundException;

	public List<Room> searchRooms(String location, LocalDate checkInDate, LocalDate checkOutDate);

	boolean isRoomAvailable(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) throws RoomNotFoundException;

	double calculateTotalFare(Long roomId, int numberOfAdults, int numberOfChildren) throws RoomNotFoundException;

	

}
