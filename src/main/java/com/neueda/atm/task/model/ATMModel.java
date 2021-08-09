package com.neueda.atm.task.model;


import java.util.Map;

public class ATMModel {
	private Integer initialBalance;
	private Map<Integer,Integer> currencyMap;
	public Integer getInitialBalance() {
		return initialBalance;
	}
	public void setInitialBalance(Integer initialBalance) {
		this.initialBalance = initialBalance;
	}
	public Map<Integer, Integer> getCurrencyMap() {
		return currencyMap;
	}
	public void setCurrencyMap(Map<Integer, Integer> currencyMap) {
		this.currencyMap = currencyMap;
	}
	@Override
	public String toString() {
		return "ATMModel [initialBalance=" + initialBalance + ", currencyMap=" + currencyMap + "]";
	}
	
	
	

}
