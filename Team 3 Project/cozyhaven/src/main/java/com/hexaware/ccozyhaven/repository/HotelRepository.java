package com.hexaware.ccozyhaven.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexaware.ccozyhaven.entities.Hotel;

import com.hexaware.ccozyhaven.entities.Room;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long>{
	
//	@Query("SELECT h FROM Hotel h " +
//	           "JOIN h.reservation r " +
//	           "JOIN r.user u " +
//	           "WHERE r.reservationId = :reservationId " +
//	           "AND u.userId = :userId")
//	    Optional<Hotel> findByReservationIdAndUserId(@Param("reservationId") Long reservationId,
//	                                                 @Param("userId") Long userId);
	
	 List<Room> findByHotelIdAndRoomAvailabilityStatus(Long hotelId, boolean availabilityStatus);

	

	

}
