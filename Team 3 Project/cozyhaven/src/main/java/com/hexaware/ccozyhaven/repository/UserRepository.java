package com.hexaware.ccozyhaven.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.ccozyhaven.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
