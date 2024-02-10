package com.hexaware.ccozyhaven.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hexaware.ccozyhaven.entities.Administrator;
import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.entities.UserInfo;

import io.jsonwebtoken.lang.Collections;

public class UserInfoUserDetails implements UserDetails {
	
	private String username;
	private String password;
	private List<GrantedAuthority> authorities;

	public UserInfoUserDetails (Administrator adminInfo) {
		username=adminInfo.getEmail();
        password=adminInfo.getPassword();
        authorities= Arrays.stream(adminInfo.getRole().split(","))

                .map(SimpleGrantedAuthority::new) // .map(str -> new SimpleGrantedAuthority(str))
                .collect(Collectors.toList());
	}

	public UserInfoUserDetails (User userInfo) {
		username=userInfo.getEmail();
        password=userInfo.getPassword();
        authorities= Arrays.stream(userInfo.getRole().split(","))
                .map(SimpleGrantedAuthority::new) // .map(str -> new SimpleGrantedAuthority(str))
                .collect(Collectors.toList());
	}
	
	public UserInfoUserDetails (HotelOwner hotelOwnerInfo) {
		username=hotelOwnerInfo.getEmail();
        password=hotelOwnerInfo.getPassword();
        authorities= Arrays.stream(hotelOwnerInfo.getRole().split(","))
                .map(SimpleGrantedAuthority::new) // .map(str -> new SimpleGrantedAuthority(str))
                .collect(Collectors.toList());
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}	