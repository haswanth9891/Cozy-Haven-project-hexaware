package com.hexaware.ccozyhaven.dto;

import java.util.HashSet;
import java.util.Set;

import com.hexaware.ccozyhaven.entities.Reservation;
import com.hexaware.ccozyhaven.entities.Review;
import com.hexaware.ccozyhaven.entities.Room;



public class HotelDTO {

	private Long hotelId;
	private String hotelName;
	private String location;
	private boolean hasDining;
	private boolean hasParking;
	private boolean hasFreeWiFi;
	private boolean hasRoomService;
	private boolean hasSwimmingPool;
	private boolean hasFitnessCenter;
	
	public HotelDTO() {
		super();
		
	}

	public HotelDTO(Long hotelId, String hotelName, String location, boolean hasDining, boolean hasParking,
			boolean hasFreeWiFi, boolean hasRoomService, boolean hasSwimmingPool, boolean hasFitnessCenter) {
		super();
		this.hotelId = hotelId;
		this.hotelName = hotelName;
		this.location = location;
		this.hasDining = hasDining;
		this.hasParking = hasParking;
		this.hasFreeWiFi = hasFreeWiFi;
		this.hasRoomService = hasRoomService;
		this.hasSwimmingPool = hasSwimmingPool;
		this.hasFitnessCenter = hasFitnessCenter;
	}

	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isHasDining() {
		return hasDining;
	}

	public void setHasDining(boolean hasDining) {
		this.hasDining = hasDining;
	}

	public boolean isHasParking() {
		return hasParking;
	}

	public void setHasParking(boolean hasParking) {
		this.hasParking = hasParking;
	}

	public boolean isHasFreeWiFi() {
		return hasFreeWiFi;
	}

	public void setHasFreeWiFi(boolean hasFreeWiFi) {
		this.hasFreeWiFi = hasFreeWiFi;
	}

	public boolean isHasRoomService() {
		return hasRoomService;
	}

	public void setHasRoomService(boolean hasRoomService) {
		this.hasRoomService = hasRoomService;
	}

	public boolean isHasSwimmingPool() {
		return hasSwimmingPool;
	}

	public void setHasSwimmingPool(boolean hasSwimmingPool) {
		this.hasSwimmingPool = hasSwimmingPool;
	}

	public boolean isHasFitnessCenter() {
		return hasFitnessCenter;
	}

	public void setHasFitnessCenter(boolean hasFitnessCenter) {
		this.hasFitnessCenter = hasFitnessCenter;
	}

	@Override
	public String toString() {
		return "HotelDTO [hotelId=" + hotelId + ", hotelName=" + hotelName + ", location=" + location + ", hasDining="
				+ hasDining + ", hasParking=" + hasParking + ", hasFreeWiFi=" + hasFreeWiFi + ", hasRoomService="
				+ hasRoomService + ", hasSwimmingPool=" + hasSwimmingPool + ", hasFitnessCenter=" + hasFitnessCenter
				+ "]";
	}
	
	
	
	
	
	

	

}
