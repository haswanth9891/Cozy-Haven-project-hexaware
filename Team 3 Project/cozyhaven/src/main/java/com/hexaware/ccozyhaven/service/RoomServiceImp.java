package com.hexaware.ccozyhaven.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.ccozyhaven.dto.RoomDTO;
import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;
import com.hexaware.ccozyhaven.repository.ReservationRepository;
import com.hexaware.ccozyhaven.repository.RoomRepository;

@Service
public class RoomServiceImp implements IRoomService{
	
	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;

	@Override
	public Room addRoom(RoomDTO roomDTO) {
		Room newRoom = new Room();
		newRoom.setRoomSize(roomDTO.getRoomSize());
		newRoom.setBedType(roomDTO.getBedType());
		newRoom.setMaxOccupancy(roomDTO.getMaxOccupancy());
		newRoom.setBaseFare(roomDTO.getBaseFare());
		newRoom.setAC(roomDTO.isAC());
		newRoom.setAvailabilityStatus(roomDTO.isAvailabilityStatus());

		return roomRepository.save(newRoom);
	}

	@Override
	public Room editRoom(Long roomId, RoomDTO updatedRoomDTO) throws RoomNotFoundException {
		Room existingRoom = roomRepository.findById(roomId)
				.orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + roomId));

		existingRoom.setRoomSize(updatedRoomDTO.getRoomSize());
		existingRoom.setBedType(updatedRoomDTO.getBedType());
		existingRoom.setMaxOccupancy(updatedRoomDTO.getMaxOccupancy());
		existingRoom.setBaseFare(updatedRoomDTO.getBaseFare());
		existingRoom.setAC(updatedRoomDTO.isAC());
		existingRoom.setAvailabilityStatus(updatedRoomDTO.isAvailabilityStatus());

		return roomRepository.save(existingRoom);
		
	}

	@Override
	public void removeRoom(Long roomId) throws RoomNotFoundException {
		Room roomToDelete = roomRepository.findById(roomId)
				.orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + roomId));

		roomRepository.delete(roomToDelete);
		
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

}
