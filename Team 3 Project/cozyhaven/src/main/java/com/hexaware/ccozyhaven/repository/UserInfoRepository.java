package com.hexaware.ccozyhaven.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.ccozyhaven.entities.UserInfo;



public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
	public Optional<UserInfo> findByName(String name);
}
