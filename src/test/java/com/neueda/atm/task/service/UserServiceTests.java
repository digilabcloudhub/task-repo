package com.neueda.atm.task.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.neueda.atm.task.dao.UserRepository;
import com.neueda.atm.task.entity.User;
import com.neueda.atm.task.model.ATMModel;
import com.neueda.atm.task.model.UserRequest;
import com.neueda.atm.task.model.UserResponseModel;
import com.neueda.atm.task.service.impl.ATMInitService;
import com.neueda.atm.task.service.impl.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {

	@InjectMocks
	private UserServiceImpl userService;
	@Mock
	private UserRepository userRepository;
	@Mock
	private ATMInitService atmServices;
	
	@Test
	public void findByIDTest() {
		User usser = new User("123456789",1234,345,4566,4567);
		Optional<User> user=Optional.of(usser);
        when(userRepository.findById("123456789")).thenReturn(user);
		Optional<User> users=userService.findById("123456789");
		
		
		assertNotNull(users.get());
		
	}
	@Test
	public void withdrawalSuccessTest() {
		UserRequest userReq=new UserRequest();
		User user=new User();
		User usser = new User("123456789",1234,345,4566,4567);
		Optional<User> userTest=Optional.of(usser);
		userReq.setAccount_number("123456789");
		ATMModel atmModel=new ATMModel();
		Map<Integer,Integer> currencyMap=new HashMap();
		currencyMap.put(5,20);
		currencyMap.put(10,30);
		currencyMap.put(20,30);
		currencyMap.put(50,10);
		atmModel.setCurrencyMap(currencyMap);
		atmModel.setInitialBalance(1500);
		when(atmServices.getATMBalance()).thenReturn(atmModel);
		userReq.setWithdrawalAmount(230);
		Optional<User> users=Optional.of(usser);
        when(userRepository.findById("123456789")).thenReturn(users);
        //when(userRepository.save(user)).thenReturn(usser);
        
        
        UserResponseModel userResponse=userService.withdraw(userReq, user);
        assertNull(userResponse.getErrorMessage());
	}
	
	@Test
	public void withdrawalFailTest() {
		UserRequest userReq=new UserRequest();
		User user=new User();
		User usser = new User("123456789",1234,345,4566,4567);
		Optional<User> userTest=Optional.of(usser);
		userReq.setAccount_number("123456789");
		ATMModel atmModel=new ATMModel();
		Map<Integer,Integer> currencyMap=new HashMap();
		currencyMap.put(5,20);
		currencyMap.put(10,30);
		currencyMap.put(20,30);
		currencyMap.put(50,10);
		atmModel.setCurrencyMap(currencyMap);
		atmModel.setInitialBalance(1500);
		when(atmServices.getATMBalance()).thenReturn(atmModel);
		userReq.setWithdrawalAmount(234);
		Optional<User> users=Optional.of(usser);
        when(userRepository.findById("123456789")).thenReturn(users);
        //when(userRepository.save(user)).thenReturn(usser);
        
        
        UserResponseModel userResponse=userService.withdraw(userReq, user);
        assertNotNull(userResponse.getErrorMessage());
	}
}
