package com.hexaware.ccozyhaven.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hexaware.ccozyhaven.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query("select u from User  u where u.email =?1")	
	Optional<User> findByEmail(String email);
	
	@Query("select u from User u where u.phoneNumer = ?1")
	User findByPhoneNumber(String phoneNumber);

}
