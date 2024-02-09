package com.hexaware.ccozyhaven.dto;

import com.hexaware.ccozyhaven.entities.Hotel;

public class HotelOwnerDTO {

	private Long hotelOwnerId;
	private String hotelOwnerName;
	private String password;
	private String email;
	private String gender;
	private String address;
	private HotelDTO hotelDTO = new HotelDTO();

	public HotelOwnerDTO() {
		super();

	}

	public HotelOwnerDTO(Long hotelOwnerId, String hotelOwnerName, String password, String email, String gender,
			String address, HotelDTO hotelDTO) {
		super();
		this.hotelOwnerId = hotelOwnerId;
		this.hotelOwnerName = hotelOwnerName;
		this.password = password;
		this.email = email;
		this.gender = gender;
		this.address = address;
		this.hotelDTO = hotelDTO;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public HotelDTO getHotelDTO() {
		return hotelDTO;
	}

	public void setHotelDTO(HotelDTO hotelDTO) {
		this.hotelDTO = hotelDTO;
	}

	@Override
	public String toString() {
		return "HotelOwnerDTO [hotelOwnerId=" + hotelOwnerId + ", hotelOwnerName=" + hotelOwnerName + ", password="
				+ password + ", email=" + email + ", gender=" + gender + ", address=" + address + ", hotelDTO="
				+ hotelDTO + "]";
	}

}
