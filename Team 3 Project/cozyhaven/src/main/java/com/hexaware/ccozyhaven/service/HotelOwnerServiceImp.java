package com.hexaware.ccozyhaven.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.ccozyhaven.dto.HotelDTO;
import com.hexaware.ccozyhaven.dto.HotelOwnerDTO;
import com.hexaware.ccozyhaven.dto.RoomDTO;
import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.exceptions.HotelOwnerNotFoundException;
import com.hexaware.ccozyhaven.exceptions.InvalidRefundException;
import com.hexaware.ccozyhaven.exceptions.RefundProcessedException;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;
import com.hexaware.ccozyhaven.repository.HotelOwnerRepository;
import com.hexaware.ccozyhaven.repository.HotelRepository;
import com.hexaware.ccozyhaven.repository.ReservationRepository;
import com.hexaware.ccozyhaven.repository.RoomRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class HotelOwnerServiceImp implements IHotelOwnerService {

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	HotelOwnerRepository hotelOwnerRepository;

	@Autowired
	HotelRepository hotelRepository;

	@Autowired
	private EntityManager entityManager;
	
	Logger  log =	LoggerFactory.getLogger(HotelOwnerServiceImp.class);

//	@Override
//	public HotelOwner registerHotelOwner(HotelOwnerDTO hotelOwnerDTO) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean loginHotelOwner(String username, String password) {
//		// TODO Auto-generated method stub
//		return false;
//	}
	@Override
	@Transactional
	public void addHotelOwnerWithHotel(HotelOwner hotelOwner) {
		
		 log.info("Before saving: " + hotelOwner);

		  Hotel hotel = hotelOwner.getHotel();
		 
		 
		    hotelRepository.save(hotel);  // Save the Hotel entity first
		    
		    hotelOwnerRepository.save(hotelOwner);
		    log.info("After saving: " + hotelOwner);
		

	}
	

	@Override
	public HotelOwner updateHotelOwner(Long hotelOwnerId, HotelOwnerDTO updatedHotelOwnerDTO)
			throws HotelOwnerNotFoundException {

		HotelOwner existingHotelOwner = hotelOwnerRepository.findById(hotelOwnerId)
				.orElseThrow(() -> new HotelOwnerNotFoundException("HotelOwner not found with id: " + hotelOwnerId));

		existingHotelOwner.setHotelOwnerName(updatedHotelOwnerDTO.getHotelOwnerName());

		existingHotelOwner.setEmail(updatedHotelOwnerDTO.getEmail());

		existingHotelOwner.setGender(updatedHotelOwnerDTO.getGender());
		existingHotelOwner.setAddress(updatedHotelOwnerDTO.getAddress());

		return hotelOwnerRepository.save(existingHotelOwner);

	}

	@Override
	public void deleteHotelOwner(Long hotelOwnerId) throws HotelOwnerNotFoundException {
		HotelOwner hotelOwnerToDelete = hotelOwnerRepository.findById(hotelOwnerId)
				.orElseThrow(() -> new HotelOwnerNotFoundException("HotelOwner not found with id: " + hotelOwnerId));

		hotelOwnerRepository.delete(hotelOwnerToDelete);

	}

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
	public List<Reservation> viewReservation(Long hotelId) {

		return reservationRepository.findByUser_UserId(hotelId);
	}

	@Override
	public double refundAmount(Long reservationId)
			throws RefundProcessedException, InvalidRefundException, ReservationNotFoundException {
		Reservation reservation = reservationRepository.findById(reservationId)
				.orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + reservationId));

		if (reservation.getReservationStatus() == "CANCELLED") {

			if (!reservation.isRefundProcessed()) {

				double refundedAmount = calculateRefundedAmount(reservation);

				reservation.setRefundProcessed(true);
				reservationRepository.save(reservation);

				return refundedAmount;
			} else {
				throw new RefundProcessedException("Refund already processed for reservationId: " + reservationId);
			}
		} else {
			throw new InvalidRefundException("Refund can only be processed for canceled reservations.");
		}
	}

	private double calculateRefundedAmount(Reservation reservation) {

		return reservation.getTotalAmount() * 0.8; // 80 percent refund
	}


	@Override
	public Hotel addHotel(Long hotelOwnerId, HotelDTO hotelDTO) throws HotelOwnerNotFoundException {
		HotelOwner hotelOwner = hotelOwnerRepository.findById(hotelOwnerId)
                .orElseThrow(() -> new HotelOwnerNotFoundException("HotelOwner not found with id: " + hotelOwnerId));

        Hotel newHotel = new Hotel();
        newHotel.setHotelName(hotelDTO.getHotelName());
        newHotel.setLocation(hotelDTO.getLocation());
        //newHotel.setRating(hotelDTO.getRating());
        newHotel.setHotelOwner(hotelOwner);

        return hotelRepository.save(newHotel);
	}

}
