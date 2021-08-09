package com.neueda.atm.task.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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
	private final AtomicInteger remainingBalance = new AtomicInteger(0);
	private final AtomicInteger totalAmountLeft = new AtomicInteger(0);
	private static Map<Integer, Integer> denonminatioMap = new ConcurrentHashMap<>();

	private static final String ERROR_DISPERSE_CASH = "Not enough cash to dispense";
	private static final String INCORRECT_DENOMINATION = "Incorrect Amount Entered.Kindly enter amount in multiple of 5";
	private static final String SUCCESS = "Transaction Successfull";

	@Override
	public Optional<User> findById(String id) {

		return userRepository.findById(id);

	}

	@Override
	public User isAuthenticated(UserRequest user) {
		// TODO Auto-generated method stub
		Optional<User> userDetails = userRepository.findById(user.getAccount_number());
		if(userDetails.isPresent()) {
			if (userDetails.get().getPin() == user.getPin()) {
				return userDetails.get();
			}
		}
		
		return null;
	}

	

	private Integer calculate(Integer amount, int denom, int remainingBalance) {
		Integer value = 0;
		Integer updatedValue = (Integer) denonminatioMap.get(denom);
		ATMModel updated = new ATMModel();
		value = amount / denom;
		if (value > 0) {
			if (updatedValue < value) {
				denonminatioMap.put(denom, 0);
				amount = amount - (denom * updatedValue);

			} else {

				amount = amount % denom;
				denonminatioMap.put(denom, updatedValue - value);

			}
		}

		updated.setCurrencyMap(denonminatioMap);
		updated.setInitialBalance(remainingBalance);
		atmServices.setATMBalance(updated);
		return amount;

	}

	@Override
	public synchronized UserResponseModel withdraw(UserRequest user, User cust) {
		Optional<User> userDetails = userRepository.findById(user.getAccount_number());
		User customer = new User();
		ATMModel atmTrans = atmServices.getATMBalance();
		Integer value = 0;

		denonminatioMap = atmTrans.getCurrencyMap();

		Map<Integer, Integer> dispersedCurrency = new HashMap();

		UserResponseModel userResponse = new UserResponseModel();

		remainingBalance.compareAndSet(remainingBalance.get(),atmTrans.getInitialBalance());

		if (remainingBalance.get() < user.getWithdrawalAmount()) {
			userResponse.setErrorMessage(ERROR_DISPERSE_CASH);
			return userResponse;
		} else {

			remainingBalance.compareAndSet(remainingBalance.get(), remainingBalance.get() - user.getWithdrawalAmount());

			totalAmountLeft.compareAndSet(totalAmountLeft.get(),
					userDetails.get().getMaxAmount() - user.getWithdrawalAmount());
		}

		if (user.getWithdrawalAmount() % 5 != 0) {
			userResponse.setErrorMessage(INCORRECT_DENOMINATION);
			return userResponse;
		}
		int checkAmount = user.getWithdrawalAmount();

		if (checkAmount > 0 && denonminatioMap.get(50) > 0) {

			Integer updatedValue = (Integer) denonminatioMap.get(50);
			value = checkAmount / 50;
			if (value > 0) {
				if (updatedValue < value) {
					dispersedCurrency.put(50, updatedValue);
				} else {
					dispersedCurrency.put(50, value);
				}
			}

			checkAmount = calculate(checkAmount, 50, remainingBalance.get());

		}
		if (checkAmount > 0 && denonminatioMap.get(20) > 0) {
			Integer updatedValue = (Integer) denonminatioMap.get(20);
			value = checkAmount / 20;
			if (value > 0) {
				if (updatedValue < value) {
					dispersedCurrency.put(20, updatedValue);
				} else {
					dispersedCurrency.put(20, value);
				}
			}

			checkAmount = calculate(checkAmount, 20, remainingBalance.get());

		}
		if (checkAmount > 0 && denonminatioMap.get(10) > 0) {
			Integer updatedValue = (Integer) denonminatioMap.get(10);
			value = checkAmount / 10;
			if (value > 0) {
				if (updatedValue < value) {
					dispersedCurrency.put(10, updatedValue);
				} else {
					dispersedCurrency.put(10, value);
				}
			}

			checkAmount = calculate(checkAmount, 10, remainingBalance.get());
		}

		if (checkAmount > 0 && denonminatioMap.get(5) > 0) {
			Integer updatedValue = (Integer) denonminatioMap.get(5);
			value = checkAmount / 5;
			if (value > 0) {
				if (updatedValue < value) {
					dispersedCurrency.put(5, updatedValue);
				} else {
					dispersedCurrency.put(5, value);
				}
			}

			checkAmount = calculate(checkAmount, 5, remainingBalance.get());
		}

		userResponse.setDisburesedMap(dispersedCurrency);
		userResponse.setMessage(SUCCESS);
		userResponse.setTimestamp(LocalDateTime.now());
		userResponse.setRemainingBalance(totalAmountLeft.get());

		customer.setId(user.getAccount_number());
		customer.setOpeningBalance(cust.getOpeningBalance());
		customer.setOverDraft(cust.getOverDraft());
		customer.setPin(cust.getPin());
		customer.setMaxAmount(totalAmountLeft.get());
		userRepository.save(customer);

		return userResponse;
	}
}
