package com.neueda.atm.task.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neueda.atm.task.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	

}
