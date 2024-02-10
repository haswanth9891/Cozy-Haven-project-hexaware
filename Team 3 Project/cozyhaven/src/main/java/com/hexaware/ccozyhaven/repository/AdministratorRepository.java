package com.hexaware.ccozyhaven.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hexaware.ccozyhaven.entities.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long>{
	
	@Query("select a from Administrator a where a.email=?1")
	Optional<Administrator> findByEmail(String email);

}
