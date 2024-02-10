package com.hexaware.ccozyhaven.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hexaware.ccozyhaven.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	

	
	
	
	Optional<User> findByUsername(String username);

}
