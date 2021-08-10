package com.neueda.atm.task.service;

import java.util.Optional;

import com.neueda.atm.task.entity.User;
import com.neueda.atm.task.model.UserRequest;
import com.neueda.atm.task.model.UserResponseModel;

public interface ATMService {

	

	Optional<User> findById(String id);
	
	public User isAuthenticated(UserRequest user);
	public UserResponseModel withdraw(UserRequest user,User customer);


}
