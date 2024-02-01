package com.hexaware.ccozyhaven.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "User_Details")
public class User {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(name = "user_name")
    private String userName;

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

    @NotBlank(message = "Address is required")
    private String address;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private Set<Reservation> reservations = new HashSet<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private Set<Review> reviews = new HashSet<>();

    public User() {
        super();
    }

//    public User(Long userId, String userName, String password, String email, String firstName, String lastName,
//            String contactNumber, String address, Set<Reservation> reservations, Set<Review> reviews) {
//        super();
//        this.userId = userId;
//        this.userName = userName;
//        this.password = password;
//        this.email = email;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.contactNumber = contactNumber;
//        this.address = address;
//        this.reservations = reservations;
//        this.reviews = reviews;
//    }

     

    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
//
//	public Set<Reservation> getReservations() {
//		return reservations;
//	}
//
//	public void setReservations(Set<Reservation> reservations) {
//		this.reservations = reservations;
//	}
//
//	public Set<Review> getReviews() {
//		return reviews;
//	}
//
//	public void setReviews(Set<Review> reviews) {
//		this.reviews = reviews;
//	}
//
//	@Override
//    public String toString() {
//        return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", email=" + email
//                + ", firstName=" + firstName + ", lastName=" + lastName + ", contactNumber=" + contactNumber
//                + ", address=" + address + ", reservations=" + reservations + ", reviews=" + reviews + "]";
//    }
}
