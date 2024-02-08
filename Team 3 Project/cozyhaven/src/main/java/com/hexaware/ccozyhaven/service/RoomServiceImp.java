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

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoomServiceImp implements IRoomService {

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Override
	public Room addRoomToHotel(RoomDTO roomDTO, Long hotelId) {
		Room room = new Room();
        room.setRoomSize(roomDTO.getRoomSize());
        room.setBedType(roomDTO.getBedType());
        room.setMaxOccupancy(roomDTO.getMaxOccupancy());
        room.setBaseFare(roomDTO.getBaseFare());
        room.setAC(roomDTO.isAC());
        room.setAvailabilityStatus(roomDTO.isAvailabilityStatus());
		Room savedRoom = roomRepository.save(room); 
		roomRepository.addRoomToHotel(savedRoom.getRoomId(), hotelId); 
		return savedRoom;
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
	public List<Room> searchRooms(String location, LocalDate checkInDate, LocalDate checkOutDate) {
		if (checkInDate.isAfter(checkOutDate)) {
			throw new IllegalArgumentException("Check-in date must be before or equal to check-out date");
		}
		List<Room> availableRooms = roomRepository.findAvailableRooms(location, checkInDate, checkOutDate);
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
		                additionalCharge += baseFare *0.4;
		            }
					
				}
				else if (numberOfAdults > occupancy) {
					int remainingAdults = 0;
					remainingAdults = numberOfAdults - occupancy;
					for (int i = 1; i <= remainingAdults; i++) {
		                additionalCharge += baseFare *0.6;
		            }
					for (int i = 1; i <= numberOfChildren; i++) {
			            additionalCharge += baseFare * 0.4;
			        }
			}
				else {
					int remainingChildren = 0;
					remainingChildren = maxCapacity - occupancy;
					for (int i = 1; i <= remainingChildren; i++) {
		                additionalCharge += baseFare *0.4;
		            }
					
					
					
					
				}
			}

			double totalFare = baseFare + additionalCharge;
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
