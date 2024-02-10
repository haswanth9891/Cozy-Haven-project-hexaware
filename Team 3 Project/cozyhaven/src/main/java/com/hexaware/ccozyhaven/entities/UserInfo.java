package com.hexaware.ccozyhaven.entities;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserInfo {

	private Administrator admin;
	private User user;
	private HotelOwner hotelOwner;
	
	public UserInfo() {
		super();
	}
	
	public UserInfo(User user) {
		super();
		this.user = user;
	}

	public UserInfo(Administrator admin) {
		super();
		this.admin = admin;
	}

	public UserInfo(Administrator admin, User user) {
		super();
		this.admin = admin;
		this.user = user;
	}

	public Administrator getAdmin() {
		return admin;
	}
	public void setAdmin(Administrator admin) {
		this.admin = admin;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public HotelOwner getHotelOwner() {
		return hotelOwner;
	}

	public void setHotelOwner(HotelOwner hotelOwner) {
		this.hotelOwner = hotelOwner;
	}
    
    
    
}
