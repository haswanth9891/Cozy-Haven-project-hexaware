package com.hexaware.ccozyhaven.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "HotelOwner_Details")
public class HotelOwner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hotelOwnerId;

	@Column(name = "hotel_owner_name")
	@NotBlank(message = "Username cannot be blank")
	@Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
	private String hotelOwnerName;

	@Column(name = "password")
	@NotBlank(message = "Password cannot be blank")
	@Size(min = 6, message = "Password must be at least 6 characters")
	private String password;

	@Column(name = "email")
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Invalid email format")
	private String email;

	@Pattern(regexp = "^(male|female|non-binary)$", message = "Invalid gender")
	private String gender;

	@NotBlank(message = "Address is required")
	private String address;

	@OneToOne
	@JoinColumn(name = "hotel_id")
	private Hotel hotel = new Hotel();

	public HotelOwner() {
		super();
	}

	public HotelOwner(Long hotelOwnerId,
			@Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters") String hotelOwnerName,
			@Size(min = 6, message = "Password must be at least 6 characters") String password,
			@Email(message = "Invalid email format") String email,
			@Pattern(regexp = "^(male|female|non-binary)$", message = "Invalid gender") String gender, String address,
			Hotel hotel) {
		super();
		this.hotelOwnerId = hotelOwnerId;
		this.hotelOwnerName = hotelOwnerName;
		this.password = password;
		this.email = email;
		this.gender = gender;
		this.address = address;
		this.hotel = hotel;
	}

	public HotelOwner(
			@NotBlank(message = "Username cannot be blank") @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters") String hotelOwnerName,
			@NotBlank(message = "Password cannot be blank") @Size(min = 6, message = "Password must be at least 6 characters") String password,
			@NotBlank(message = "Email cannot be blank") @Email(message = "Invalid email format") String email,
			@Pattern(regexp = "^(male|female|non-binary)$", message = "Invalid gender") String gender,
			@NotBlank(message = "Address is required") String address) {
		super();
		this.hotelOwnerName = hotelOwnerName;
		this.password = password;
		this.email = email;
		this.gender = gender;
		this.address = address;
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

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
		hotel.setHotelOwner(this);
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

	@Override
	public String toString() {
		return "HotelOwner [hotelOwnerId=" + hotelOwnerId + ", hotelOwnerName=" + hotelOwnerName + ", password="
				+ password + ", email=" + email + ", hotel=" + hotel + "]";
	}

}
