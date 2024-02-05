package com.hexaware.ccozyhaven.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexaware.ccozyhaven.entities.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long>{
	
	List<Reservation> findByUser_UserId(Long userId);
	
	Optional<Reservation> findByReservationIdAndUser_UserId(Long reservationId, Long userId);
	
	
	@Query(value = "SELECT * FROM Reservation r " +
		       "WHERE r.room_id = :roomId " +
		       "AND (:checkInDate BETWEEN r.check_in_date AND r.check_out_date " +
		       "OR :checkOutDate BETWEEN r.check_in_date AND r.check_out_date " +
		       "OR r.check_in_date BETWEEN :checkInDate AND :checkOutDate " +
		       "OR r.check_out_date BETWEEN :checkInDate AND :checkOutDate)",
		       nativeQuery = true)
		List<Reservation> findOverlappingReservations(@Param("roomId") Long roomId,
		                                              @Param("checkInDate") LocalDate checkInDate,
		                                              @Param("checkOutDate") LocalDate checkOutDate);



	


}
