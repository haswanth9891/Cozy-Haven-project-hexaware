package com.hexaware.ccozyhaven.service;

import java.time.LocalDate;
import java.util.List;

import com.hexaware.ccozyhaven.dto.RoomDTO;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.exceptions.AuthorizationException;
import com.hexaware.ccozyhaven.exceptions.HotelNotFoundException;
import com.hexaware.ccozyhaven.exceptions.HotelOwnerMismatchException;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UnauthorizedAccessException;

public interface IRoomService {

	Room addRoomToHotel(RoomDTO roomDTO, Long hotelId) throws HotelNotFoundException, HotelOwnerMismatchException, UnauthorizedAccessException;

	public Room editRoom(Long roomId, RoomDTO updatedRoomDTO) throws RoomNotFoundException, UnauthorizedAccessException, AuthorizationException;

	void removeRoom(Long roomId) throws RoomNotFoundException, UnauthorizedAccessException, AuthorizationException;

	public List<Room> searchRooms(String location, LocalDate checkInDate, LocalDate checkOutDate);

	boolean isRoomAvailable(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) throws RoomNotFoundException;

	double calculateTotalFare(Long roomId, int numberOfAdults, int numberOfChildren) throws RoomNotFoundException;

	

}
