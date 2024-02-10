package com.hexaware.ccozyhaven.dto;

import jakarta.validation.constraints.Size;

public class UserDTO {

    
    private Long userId;
    
	private String userFirstName;

	private String userLastName;
	
	private String userName;
	
    private String password;
    private String email;
   
    private String contactNumber;
    private String gender;
    private String address;
    
    private String role;
	
	public UserDTO() {
		super();
	}

	

	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public UserDTO(Long userId, @Size(min = 3, max = 20) String userFirstName, String userLastName, String userName,
			@Size(min = 6, max = 20) String password, String email, String contactNumber, String gender, String address,
			String role) {
		super();
		this.userId = userId;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userName = userName;
		this.password = password;
		this.email = email;
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

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}



	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", userFirstName=" + userFirstName + ", userLastName=" + userLastName
				+ ", userName=" + userName + ", password=" + password + ", email=" + email + ", contactNumber="
				+ contactNumber + ", gender=" + gender + ", address=" + address + ", role=" + role + "]";
	}

	
	

    
}
