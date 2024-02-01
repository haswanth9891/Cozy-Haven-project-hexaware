package com.hexaware.ccozyhaven.dto;

import java.util.HashSet;
import java.util.Set;

import com.hexaware.ccozyhaven.entities.Hotel;


public class HotelOwnerDTO {
	
	
	private Long hotelOwnerId;
	private String hotelOwnerName;
	private String password;
    private String email;
    private Set<Hotel> hotel = new HashSet<Hotel>();
    
    public HotelOwnerDTO() {
		super();
		
	}

	public HotelOwnerDTO(Long hotelOwnerId, String hotelOwnerName, String password, String email, Set<Hotel> hotel) {
		super();
		this.hotelOwnerId = hotelOwnerId;
		this.hotelOwnerName = hotelOwnerName;
		this.password = password;
		this.email = email;
		this.hotel = hotel;
	}

	public Long getHotelOwnerId() {
		return hotelOwnerId;
	}

	public void setHotelOwnerId(Long hotelOwnerId) {
		this.hotelOwnerId = hotelOwnerId;
	}

	public String getHotelOwnerName() {
		return hotelOwnerName;
	}

	public void setHotelOwnerName(String hotelOwnerName) {
		this.hotelOwnerName = hotelOwnerName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Hotel> getHotel() {
		return hotel;
	}

	public void setHotel(Set<Hotel> hotel) {
		this.hotel = hotel;
	}

	@Override
	public String toString() {
		return "HotelOwnerDTO [hotelOwnerId=" + hotelOwnerId + ", hotelOwnerName=" + hotelOwnerName + ", password="
				+ password + ", email=" + email + ", hotel=" + hotel + "]";
	}
    
    



}
