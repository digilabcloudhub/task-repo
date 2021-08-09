package com.neueda.atm.task.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	public User(String id, int pin, Integer openingBalance, Integer overDraft, Integer maxAmount) {
		super();
		this.id = id;
		this.pin = pin;
		this.openingBalance = openingBalance;
		this.overDraft = overDraft;
		this.maxAmount = maxAmount;
	}

	@Id
	@Column(name = "account_number")
	private String id;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Column(name = "pin")
	public int pin;

	@Column(name = "opening_balance")
	public Integer openingBalance;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public Integer getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(Integer openingBalance) {
		this.openingBalance = openingBalance;
	}

	public Integer getOverDraft() {
		return overDraft;
	}

	public void setOverDraft(Integer overDraft) {
		this.overDraft = overDraft;
	}

	@Column(name = "OVER_DRAFT")
	public Integer overDraft;
	
	@Column(name="MAX_AMOUNT")
	public Integer maxAmount;

	public Integer getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Integer maxAmount) {
		this.maxAmount = maxAmount;
	}

}
