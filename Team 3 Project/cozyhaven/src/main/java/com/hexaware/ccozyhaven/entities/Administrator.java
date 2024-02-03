package com.hexaware.ccozyhaven.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Administrator_Details")
public class Administrator {

    @Id
    @Column(name = "admin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Pattern(regexp = "^[0-9]+$")
    private Long adminId;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(name = "user_name")
    private String userName;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Column(name = "password")
    private String password;
    
    @NotBlank(message = "Password cannot be blank")
    @Email(message = "Password cannot be blank")
    @Column(name = "email")
    private String email;
    
    @Pattern(regexp = "^(male|female|non-binary)$", message = "Invalid gender")
    private String gender;
    
    @NotBlank(message = "Address is required")
    private String address;

	public Administrator() {
		super();
	}

	public Administrator(Long adminId, String userName, String password, String email) {
		super();
		this.adminId = adminId;
		this.userName = userName;
		this.password = password;
		this.email = email;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
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

	@Override
	public String toString() {
		return "Administrator [adminId=" + adminId + ", userName=" + userName + ", password=" + password + ", email="
				+ email + "]";
	}

	
}

