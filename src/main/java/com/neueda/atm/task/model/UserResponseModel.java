package com.neueda.atm.task.model;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserResponseModel {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime timestamp;
	private int status;
	private String message;
	private Integer remainingBalance;
	private String errorMessage;
	private Map<Integer,Integer> disburesedMap;
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getRemainingBalance() {
		return remainingBalance;
	}
	public void setRemainingBalance(Integer remainingBalance) {
		this.remainingBalance = remainingBalance;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Map<Integer, Integer> getDisburesedMap() {
		return disburesedMap;
	}
	public void setDisburesedMap(Map<Integer, Integer> disburesedMap) {
		this.disburesedMap = disburesedMap;
	}

}
