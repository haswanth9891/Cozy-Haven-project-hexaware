package com.hexaware.ccozyhaven.service;

import java.time.LocalDate;
import java.util.List;

import com.hexaware.ccozyhaven.dto.BookedRoomDTO;
import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.exceptions.InconsistentHotelException;
import com.hexaware.ccozyhaven.exceptions.InvalidCancellationException;
import com.hexaware.ccozyhaven.exceptions.InvalidRefundException;
import com.hexaware.ccozyhaven.exceptions.RefundProcessedException;
import com.hexaware.ccozyhaven.exceptions.ReservationNotFoundException;
import com.hexaware.ccozyhaven.exceptions.RoomNotAvailableException;
import com.hexaware.ccozyhaven.exceptions.RoomNotFoundException;
import com.hexaware.ccozyhaven.exceptions.UserNotFoundException;

public interface IReservationService {

	public List<Reservation> viewReservationByHotelId(Long hotelId);

	public double refundAmount(Long reservationId)
			throws RefundProcessedException, InvalidRefundException, ReservationNotFoundException;


	boolean reservationRoom(Long userId, List<BookedRoomDTO> bookedRooms, LocalDate checkInDate, LocalDate checkOutDate)
			throws RoomNotAvailableException, RoomNotFoundException, UserNotFoundException, InconsistentHotelException;

	public List<Reservation> getUserReservations(Long userId);

//	public void cancelReservation(Long userId, Long reservationId) throws ReservationNotFoundException;

	public void cancelReservationAndRequestRefund(Long userId, Long reservationId)
			throws InvalidCancellationException, ReservationNotFoundException;

	List<Reservation> viewValidReservationByHotelId(Long hotelId);

	boolean isRoomAvailable(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) throws RoomNotFoundException;

	

	

}
