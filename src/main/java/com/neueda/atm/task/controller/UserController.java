package com.neueda.atm.task.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neueda.atm.task.entity.User;
import com.neueda.atm.task.exception.InsufficientBalanceException;
import com.neueda.atm.task.exception.ResourceNotAutenticateException;
import com.neueda.atm.task.model.RegisterResponse;
import com.neueda.atm.task.model.UserRequest;
import com.neueda.atm.task.model.UserResponseModel;
import com.neueda.atm.task.service.ATMService;

@RestController
@RequestMapping("atm")
public class UserController {

	@Autowired
	private ATMService usrService;
	
	private static final String INSUFFICIENT_BALANCE="Insufficient Amount in Account";
	private static final String INVALID_PIN="Invalid Pincode/Account Number";

	@CrossOrigin
	@GetMapping("/balance")
	public ResponseEntity<RegisterResponse> balanceCheck(@RequestBody UserRequest request)
			throws ResourceNotAutenticateException {

		User user = usrService.isAuthenticated(request);

		if (null != user) {

			RegisterResponse success = new RegisterResponse();
			success.setMaxBalance(user.getOpeningBalance() + user.getOverDraft());
			success.setTimestamp(LocalDateTime.now());
			success.setStatus(HttpStatus.OK.value());
			success.setMessage("Transaction Successfull");
			return new ResponseEntity<>(success, HttpStatus.OK);

		} else {
			throw new ResourceNotAutenticateException(INVALID_PIN);
		}

	}

	@CrossOrigin
	@PostMapping("/withdrawal")
	public ResponseEntity<UserResponseModel> withdrawal(@RequestBody UserRequest request)
			throws ResourceNotAutenticateException, InsufficientBalanceException {
		UserResponseModel userResponse=null;
		User user = usrService.isAuthenticated(request);

		if (null != user) {
			Integer maxAmountToWithDraw = user.getMaxAmount();
			if (maxAmountToWithDraw >= request.getWithdrawalAmount()) {
				userResponse=usrService.withdraw(request,user);
				//userResponse=usrService.withdrawRefactor(request, user);
				if(null!=userResponse.getErrorMessage() && !userResponse.getErrorMessage().isEmpty()) {
					throw new InsufficientBalanceException(userResponse.getErrorMessage());
				}
			} else {
				throw new InsufficientBalanceException(INSUFFICIENT_BALANCE);
			}

		} else {
			throw new ResourceNotAutenticateException(INVALID_PIN);
		}

		return  new ResponseEntity<>(userResponse, HttpStatus.OK);

	}

}
