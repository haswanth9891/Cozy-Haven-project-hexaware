package com.hexaware.ccozyhaven.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.ccozyhaven.entities.Hotel;
import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.entities.Room;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.exceptions.InvalidCancellationException;
import com.hexaware.ccozyhaven.exceptions.InvalidRefundException;
import com.hexaware.ccozyhaven.exceptions.RefundProcessedException;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.RoomNotAvailableException;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;
import com.hexaware.ccozyhaven.repository.ReservationRepository;
import com.hexaware.ccozyhaven.repository.RoomRepository;
import com.hexaware.ccozyhaven.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReservationServiceImp implements IReservationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImp.class);

	@Autowired
	ReservationRepository reservationRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoomRepository roomRepository;

	@Override
	public List<Reservation> viewReservationByHotelId(Long hotelId) {
		 LOGGER.info("Viewing all reservations for hotel with ID: {}", hotelId);
		return reservationRepository.findAllByHotel_HotelId(hotelId);

	}

	@Override
	public List<Reservation> viewValidReservationByHotelId(Long hotelId) {
		LOGGER.info("Viewing valid reservations for hotel with ID: {}", hotelId);
		return reservationRepository.findAllByHotel_HotelId(hotelId);

	}

	@Override
	public boolean reservationRoom(Long userId, Long roomId, int numberOfAdults, int numberOfChildren,
			LocalDate checkInDate, LocalDate checkOutDate)
			throws RoomNotAvailableException, RoomNotFoundException, UserNotFoundException {
		 LOGGER.info("Making a reservation for user with ID: {} and room with ID: {}", userId, roomId);
		if (!isRoomAvailable(roomId, checkInDate, checkOutDate)) {
			throw new RoomNotAvailableException(
					"Room with id " + roomId + " is not available for the selected date range.");
		}

		Reservation reservation = new Reservation();
		reservation.setNumberOfAdults(numberOfAdults);
		reservation.setNumberOfChildren(numberOfChildren);
		reservation.setCheckInDate(checkInDate);
		reservation.setCheckOutDate(checkOutDate);
		long numberOfDays = ChronoUnit.DAYS.between(checkInDate, checkOutDate) + 1;

		double fareForDay = 0;
		fareForDay += calculateTotalFare(roomId, numberOfAdults, numberOfChildren);

		double totalFare = fareForDay * numberOfDays;
		reservation.setTotalAmount(totalFare);

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
		reservation.setUser(user);

		
			Room room = roomRepository.findById(roomId)
					.orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + roomId));
			room.setReservation(reservation); // Set the reservation in each room
			Set<Room> rooms = new HashSet();
			rooms.add(room);
		

		
			
			Hotel hotel = room.getHotel();
			reservation.setHotel(hotel);
		
			
		reservation.setRooms(rooms);
		reservation.setReservationStatus("PENDING");

		reservationRepository.save(reservation);
		LOGGER.info("Reservation made successfully");
		return true;
	}
	

	@Override
	public boolean isRoomAvailable(Long roomId, LocalDate checkInDate, LocalDate checkOutDate)
			throws RoomNotFoundException {
		 LOGGER.info("Checking room availability with ID: {}", roomId);
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

	@Override
	public double refundAmount(Long reservationId)
			throws RefundProcessedException, InvalidRefundException, ReservationNotFoundException {
		LOGGER.info("Calculating refund amount for reservation with ID: {}", reservationId);
		Reservation reservation = reservationRepository.findById(reservationId)
				.orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + reservationId));

		if ("CANCELLED".equals(reservation.getReservationStatus())) {

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
	public List<Reservation> getUserReservations(Long userId) {
		LOGGER.info("Retrieving reservations for user with ID: {}", userId);
		return reservationRepository.findByUserId(userId);
	}

	@Override
	public void cancelReservation(Long userId, Long reservationId) throws ReservationNotFoundException {
		 LOGGER.info("Cancelling reservation with ID: {} for user with ID: {}", reservationId, userId);

		Reservation reservation = reservationRepository.findByReservationIdAndUser_UserId(reservationId, userId)
				.orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + reservationId));

		reservation.setReservationStatus("CANCELLED");
		reservationRepository.save(reservation);
		LOGGER.info("Reservation cancelled successfully");

	}

	@Override
	public void cancelReservationAndRequestRefund(Long userId, Long reservationId)
			throws InvalidCancellationException, ReservationNotFoundException {
		 LOGGER.info("Cancelling reservation with refund request for ID: {} for user with ID: {}", reservationId, userId);
		Reservation reservation = reservationRepository.findByReservationIdAndUser_UserId(reservationId, userId)
				.orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + reservationId));

		if (reservation.getReservationStatus() != "CANCELLED") {

			reservation.setReservationStatus("CANCELLED");
			reservationRepository.save(reservation);
		} else {
			throw new InvalidCancellationException("Reservation is already cancelled.");
		}
		LOGGER.info("Reservation cancelled with refund request successfully");
	}


}
