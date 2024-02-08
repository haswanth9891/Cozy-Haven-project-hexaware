package com.hexaware.ccozyhaven.dto;
import com.hexaware.ccozyhaven.entities.Hotel;


public class HotelOwnerDTO {
	
	
	private Long hotelOwnerId;
	private String hotelOwnerName;
	private String password;
    private String email;
    private String gender;
    private String address;
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

	private Hotel hotel = new Hotel();
    
    public HotelOwnerDTO() {
		super();
		
	}

	

	public HotelOwnerDTO(Long hotelOwnerId, String hotelOwnerName, String password, String email, String gender,
			String address, Hotel hotel) {
		super();
		this.hotelOwnerId = hotelOwnerId;
		this.hotelOwnerName = hotelOwnerName;
		this.password = password;
		this.email = email;
		this.gender = gender;
		this.address = address;
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

	

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	@Override
	public String toString() {
		return "HotelOwnerDTO [hotelOwnerId=" + hotelOwnerId + ", hotelOwnerName=" + hotelOwnerName + ", password="
				+ password + ", email=" + email + ", gender=" + gender + ", address=" + address + ", hotel=" + hotel
				+ "]";
	}

	
    



}
