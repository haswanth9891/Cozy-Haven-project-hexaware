package com.hexaware.ccozyhaven.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.hexaware.ccozyhaven.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
