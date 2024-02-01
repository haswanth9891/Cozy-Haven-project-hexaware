package com.hexaware.ccozyhaven.dto;



public class AdministratorDTO {
	
    private Long adminId;
    private String userName;
    private String password;
    private String email;
    
	public AdministratorDTO() {
		super();
		
	}

	public AdministratorDTO(Long adminId, String userName, String password, String email) {
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
		return "AdministratorDTO [adminId=" + adminId + ", userName=" + userName + ", password=" + password + ", email="
				+ email + "]";
	}
	
    
    

}
