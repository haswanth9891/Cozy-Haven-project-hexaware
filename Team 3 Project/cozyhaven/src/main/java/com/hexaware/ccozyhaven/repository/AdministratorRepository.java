package com.hexaware.ccozyhaven.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.ccozyhaven.entities.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long>{

}
