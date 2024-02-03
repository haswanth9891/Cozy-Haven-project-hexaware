package com.hexaware.ccozyhaven.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.hexaware.ccozyhaven.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation,Long>{
	
	List<Reservation> findByUserId(Long userId);
	Optional<Reservation> findByIdAndUserId(Long reservationId, Long userId);
	
	
	
	@Query("SELECT r FROM Reservation r " +
		       "WHERE r.room.roomId = :roomId " +
		       "AND (:checkInDate BETWEEN r.checkInDate AND r.checkOutDate " +
		       "OR :checkOutDate BETWEEN r.checkInDate AND r.checkOutDate " +
		       "OR r.checkInDate BETWEEN :checkInDate AND :checkOutDate " +
		       "OR r.checkOutDate BETWEEN :checkInDate AND :checkOutDate)")
		List<Reservation> findOverlappingReservations(@Param("roomId") Long roomId,
		                                              @Param("checkInDate") LocalDate checkInDate,
		                                              @Param("checkOutDate") LocalDate checkOutDate);

	


}
