package com.neueda.atm.task.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neueda.atm.task.dao.UserRepository;
import com.neueda.atm.task.entity.User;
import com.neueda.atm.task.model.ATMModel;

@Service
public class ATMInitService {
	
	private ATMModel atmModel=new ATMModel();
	
	@Autowired
	UserRepository userRepo;
	
	
	public void initATM() {
		
		Map<Integer,Integer> currencyMap=new HashMap();
		currencyMap.put(5,20);
		currencyMap.put(10,30);
		currencyMap.put(20,30);
		currencyMap.put(50,10);
		atmModel.setCurrencyMap(currencyMap);
		atmModel.setInitialBalance(1500);		
	
		
	}
	
	public ATMModel getATMBalance() {
		return atmModel;
	}
	
	public void setATMBalance(ATMModel updatedModel) {
		atmModel.setCurrencyMap(updatedModel.getCurrencyMap());
		atmModel.setInitialBalance(updatedModel.getInitialBalance());
	}
	
	public void setInitalAccount() {
		User user1=new User();
		user1.setId("123456789");
		user1.setPin(1234);
		user1.setOpeningBalance(800);
		user1.setOverDraft(200);
		user1.setMaxAmount(user1.getOpeningBalance()+user1.getOverDraft());
		
		User user2=new User();
		user2.setId("987654321");
		user2.setPin(4321);
		user2.setOpeningBalance(1230);
		user2.setOverDraft(150);
		user2.setMaxAmount(user2.getOpeningBalance()+user2.getOverDraft());
		userRepo.save(user1);
		userRepo.save(user2);
	}

}
