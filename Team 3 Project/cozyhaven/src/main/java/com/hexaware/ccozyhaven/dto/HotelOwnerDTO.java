package com.hexaware.ccozyhaven.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
/*
 * Author: Nafisa
 * 
 * DTO description: Represents the data transfer object for HotelOwner entity.
 * It contains fields relevant to HotelOwner, getter and setters, constructors, and relevant validations.
 */

public class HotelOwnerDTO {

	private Long hotelOwnerId;
	private String hotelOwnerName;
	
	private String username;
	
	@Size(min=6,max=20)
	private String password;
	
	@Email
	private String email;
	

	private String gender;
	private String address;
	private HotelDTO hotelDTO = new HotelDTO();
	
	private String role;

	public HotelOwnerDTO() {
		super();

	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	

	public void setAddress(String address) {
		this.address = address;
	}

	public HotelDTO getHotelDTO() {
		return hotelDTO;
	}

	public void setHotelDTO(HotelDTO hotelDTO) {
		this.hotelDTO = hotelDTO;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "HotelOwnerDTO [hotelOwnerId=" + hotelOwnerId + ", hotelOwnerName=" + hotelOwnerName + ", username="
				+ username + ", password=" + password + ", email=" + email + ", gender=" + gender + ", address="
				+ address + ", hotelDTO=" + hotelDTO + ", role=" + role + "]";
	}



	

	
	
	

}
