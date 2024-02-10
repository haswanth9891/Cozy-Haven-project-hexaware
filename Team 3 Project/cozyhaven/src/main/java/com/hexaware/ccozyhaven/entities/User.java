package com.hexaware.ccozyhaven.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "User_Details")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@NotBlank(message = "Password is required")
	private String password;

	@Email(message = "Invalid email format")
	private String email;

	@NotBlank(message = "First name is required")
	private String firstName;

	@NotBlank(message = "Last name is required")
	private String lastName;

	@Size(max = 10, message = "Contact number must be at most 10 characters")
	@Pattern(regexp = "\\d{10}", message = "Invalid contact number")
	@Column(name = "contact_number")
	private String contactNumber;

	@Pattern(regexp = "^(male|female|non-binary)$", message = "Invalid gender")
	private String gender;

	@NotBlank(message = "Address is required")
	private String address;

	private String role;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@JsonIgnoreProperties("user")
	private Set<Reservation> reservations = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Review> reviews = new HashSet<>();

	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public User() {
		super();
	}


	public User(Long userId, @NotBlank(message = "Password is required") String password,
			@Email(message = "Invalid email format") String email,
			@NotBlank(message = "First name is required") String firstName,
			@NotBlank(message = "Last name is required") String lastName,
			@Size(max = 10, message = "Contact number must be at most 10 characters") @Pattern(regexp = "\\d{10}", message = "Invalid contact number") String contactNumber,
			@Pattern(regexp = "^(male|female|non-binary)$", message = "Invalid gender") String gender,
			@NotBlank(message = "Address is required") String address, String role) {
		super();
		this.userId = userId;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactNumber = contactNumber;
		this.gender = gender;
		this.address = address;
		this.role = role;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
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

	public Set<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", email=" + email + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", contactNumber=" + contactNumber + ", gender=" + gender + ", address="
				+ address + ", role=" + role + ", reservations=" + reservations + ", reviews=" + reviews + "]";
	}

}
