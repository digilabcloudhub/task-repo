package com.neueda.atm.task.model;

public class UserRequest {
	
	private String account_number;
	private int pin;
private Integer withdrawalAmount;
	
	public Integer getWithdrawalAmount() {
		return withdrawalAmount;
	}
	public void setWithdrawalAmount(Integer withdrawalAmount) {
		this.withdrawalAmount = withdrawalAmount;
	}
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	public int getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}
	@Override
	public String toString() {
		return "UserRequest [account_number=" + account_number + ", pin=" + pin + "]";
	}
	

}
