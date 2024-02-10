package com.hexaware.ccozyhaven.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hexaware.ccozyhaven.entities.HotelOwner;

@Repository
public interface HotelOwnerRepository extends JpaRepository<HotelOwner, Long>{
	
	@Query("select h from HotelOwner h where h.email=?1")
	Optional<HotelOwner> findByEmail(String email);

}
