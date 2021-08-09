package com.neueda.atm.task.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neueda.atm.task.dao.UserRepository;
import com.neueda.atm.task.entity.User;
import com.neueda.atm.task.model.ATMModel;
import com.neueda.atm.task.model.UserRequest;
import com.neueda.atm.task.model.UserResponseModel;
import com.neueda.atm.task.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ATMInitService atmServices;

	@Override
	public Optional<User> findById(String id) {

		return userRepository.findById(id);

	}

	@Override
	public User isAuthenticated(UserRequest user) {
		// TODO Auto-generated method stub
		Optional<User> userDetails = userRepository.findById(user.getAccount_number());
		if (userDetails.get().getPin() == user.getPin()) {
			return userDetails.get();
		}
		return null;
	}

	@Override
	public synchronized UserResponseModel withdraw(UserRequest user, User cust) {
		Optional<User> userDetails = userRepository.findById(user.getAccount_number());
		User customer = new User();
		ATMModel atmTrans = atmServices.getATMBalance();
		ATMModel updated = new ATMModel();
		Map<Integer, Integer> denomMap = atmTrans.getCurrencyMap();
		Map<Integer, Integer> dispersedCurrency = new HashMap();
		Integer amountLeft = 0;
		UserResponseModel userResponse = new UserResponseModel();
		Integer remainingBal = atmTrans.getInitialBalance();
		Integer value = 0;
		if (atmTrans.getInitialBalance() < user.getWithdrawalAmount()) {
			userResponse.setErrorMessage("Not enough cash to dispense");
			return userResponse;
		} else {
			remainingBal = remainingBal - user.getWithdrawalAmount();
			amountLeft = userDetails.get().getMaxAmount() - user.getWithdrawalAmount();
		}
		// Check denomination part
		if (user.getWithdrawalAmount() % 5 != 0) {
			userResponse.setErrorMessage("Incorrect Amount Entered.Kindly enter amount in multiple of 5");
			return userResponse;
		}
		int checkAmount = user.getWithdrawalAmount();

		if (checkAmount > 0 && denomMap.get(50) > 0) {
			Integer updatedValue = (Integer) denomMap.get(50);
			value = checkAmount / 50;

			if (updatedValue < value) {
				denomMap.put(50, 0);
				checkAmount = checkAmount - (50 * updatedValue);
				dispersedCurrency.put(50, updatedValue);
			} else {
				value = checkAmount / 50;
				checkAmount = checkAmount % 50;
				denomMap.put(50, updatedValue - value);
				dispersedCurrency.put(50, value);
			}

		}
		if (checkAmount > 0 && denomMap.get(20) > 0) {
			Integer updatedValue = (Integer) denomMap.get(20);
			value = checkAmount / 20;

			if (updatedValue < value) {
				denomMap.put(20, 0);
				checkAmount = checkAmount - (20 * updatedValue);
				dispersedCurrency.put(20, updatedValue);
			} else {
				value = checkAmount / 20;
				checkAmount = checkAmount % 20;
				denomMap.put(20, updatedValue - value);
				dispersedCurrency.put(20, value);
			}

		}
		if (checkAmount > 0 && denomMap.get(10) > 0) {
			Integer updatedValue = (Integer) denomMap.get(10);
			value = checkAmount / 10;

			if (updatedValue < value) {
				denomMap.put(10, 0);
				checkAmount = checkAmount - (10 * updatedValue);
				dispersedCurrency.put(10, updatedValue);
			} else {
				value = checkAmount / 10;
				checkAmount = checkAmount % 10;
				denomMap.put(10, updatedValue - value);
				dispersedCurrency.put(10, value);
			}
		}

		if (checkAmount > 0 && denomMap.get(5) > 0) {
			Integer updatedValue = (Integer) denomMap.get(5);
			value = checkAmount / 5;

			if (updatedValue < value) {
				denomMap.put(5, 0);
				checkAmount = checkAmount - (5 * updatedValue);
				dispersedCurrency.put(5, updatedValue);
			} else {
				value = checkAmount / 5;
				checkAmount = checkAmount % 5;
				denomMap.put(5, updatedValue - value);
				dispersedCurrency.put(5, value);
			}
		}
		updated.setCurrencyMap(denomMap);
		updated.setInitialBalance(remainingBal);
		atmServices.setATMBalance(updated);

		userResponse.setDisburesedMap(dispersedCurrency);
		userResponse.setMessage("Success");
		userResponse.setTimestamp(LocalDateTime.now());
		customer.setId(user.getAccount_number());
		customer.setOpeningBalance(cust.getOpeningBalance());
		customer.setOverDraft(cust.getOverDraft());
		customer.setPin(cust.getPin());
		customer.setMaxAmount(amountLeft);
		userRepository.save(customer);
		userResponse.setRemainingBalance(amountLeft);

		return userResponse;
	}

}
