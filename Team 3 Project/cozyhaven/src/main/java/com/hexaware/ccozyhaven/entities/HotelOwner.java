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
    @Column(name ="hotelOwnerId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Pattern(regexp = "^[0-9]+$")
    private Long hotelOwnerId;

    @Column(name = "user_name")
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

    @OneToMany(mappedBy = "HotelOwner", cascade = CascadeType.ALL)
    @JoinColumn(name="hotel_id")
    private Set<Hotel> hotel = new HashSet<Hotel>();


    public HotelOwner() {
        super();
    }

    public HotelOwner(Long hotelOwnerId, String hotelOwnerName, String password, String email, Set<Hotel> hotel) {
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
		return "HotelOwner [hotelOwnerId=" + hotelOwnerId + ", hotelOwnerName=" + hotelOwnerName + ", password="
				+ password + ", email=" + email + ", hotel=" + hotel + "]";
	}

    

}
