package com.neueda.atm.task;



import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.neueda.atm.task.service.impl.ATMInitService;

@SpringBootApplication
public class UserServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServicesApplication.class, args);
	}
	@Autowired
	ATMInitService atminit;
	
	@PostConstruct
	private void init() {
		
		atminit.initATM();
		atminit.setInitalAccount();
	}
}
