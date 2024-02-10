package com.hexaware.ccozyhaven.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hexaware.ccozyhaven.entities.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long>{
	
	
	
	 Optional<Administrator> findByUserName(String username);

}
