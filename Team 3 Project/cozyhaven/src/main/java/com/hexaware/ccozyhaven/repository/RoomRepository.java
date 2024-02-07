package com.hexaware.ccozyhaven.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexaware.ccozyhaven.entities.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

	@Modifying
	@Query("UPDATE Room r SET r.hotel.hotelId = :hotelId WHERE r.roomId = :roomId")
	void addRoomToHotel(@Param("roomId") Long roomId, @Param("hotelId") Long hotelId);

	@Query(value = "SELECT * FROM room_details r " + "WHERE r.hotel_id IN ("
			+ "  SELECT h.hotel_id FROM hotel_details h " + "  WHERE h.location = :location" + ") "
			+ "AND r.availability_status = true " + "AND NOT EXISTS ("
			+ "  SELECT res.reservation_id FROM reservation_details res "
			+ "  JOIN room_details resRoom ON res.reservation_id = resRoom.reservation_id "
			+ "  WHERE (resRoom.room_id = r.room_id) " + "  AND ("
			+ "    (res.check_in_date BETWEEN :checkInDate AND :checkOutDate) "
			+ "    OR (res.check_out_date BETWEEN :checkInDate AND :checkOutDate)" + "  )" + ") "
			+ "ORDER BY r.base_fare ASC", nativeQuery = true)
	List<Room> findAvailableRooms(@Param("location") String location, @Param("checkInDate") LocalDate checkInDate,
			@Param("checkOutDate") LocalDate checkOutDate);

}
