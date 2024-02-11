package com.hexaware.ccozyhaven.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
/*
 * Author: Nafisa
 * 
 * DTO description: Represents the data transfer object for Administrator entity.
 * It contains fields relevant to Administrator, getter and setters, constructors, and relevant validations.
 */



public class AdministratorDTO {

	private Long adminId;
	
	private String adminFirstName;

	
	private String adminLastName;
	private String userName;
	
	@Size(min=5,max=20)
	private String password;
	
	@Email
	private String email;
	
	private String role;

	public AdministratorDTO() {
		super();

	}

	

	public AdministratorDTO(Long adminId, String adminFirstName, String adminLastName, String userName,
			@Size(min = 5, max = 20) String password, @Email String email) {
		super();
		this.adminId = adminId;
		this.adminFirstName = adminFirstName;
		this.adminLastName = adminLastName;
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

	public String getAdminFirstName() {
		return adminFirstName;
	}

	public void setAdminFirstName(String adminFirstName) {
		this.adminFirstName = adminFirstName;
	}

	public String getAdminLastName() {
		return adminLastName;
	}

	public void setAdminLastName(String adminLastName) {
		this.adminLastName = adminLastName;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	@Override
	public String toString() {
		return "AdministratorDTO [adminId=" + adminId + ", adminFirstName=" + adminFirstName + ", adminLastName="
				+ adminLastName + ", userName=" + userName + ", password=" + password + ", email=" + email + ", role="
				+ role + "]";
	}

	

}