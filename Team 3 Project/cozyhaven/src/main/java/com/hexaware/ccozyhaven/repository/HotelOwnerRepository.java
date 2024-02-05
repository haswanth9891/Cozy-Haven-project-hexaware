package com.hexaware.ccozyhaven.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.ccozyhaven.entities.HotelOwner;

@Repository
public interface HotelOwnerRepository extends JpaRepository<HotelOwner, Long>{

}
