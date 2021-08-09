package com.neueda.atm.task.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.neueda.atm.task.dao.UserRepository;
import com.neueda.atm.task.model.ATMModel;
import com.neueda.atm.task.service.impl.ATMInitService;

@RunWith(MockitoJUnitRunner.class)
public class ATMInitServiceTests {

	@InjectMocks
	private ATMInitService atmint;

	@Mock
	UserRepository mockUserRepo;

	@Test
	public void initMethodTest() {

		atmint.initATM();
	}

	@Test
	public void getATMModelTest() {
		atmint.getATMBalance();
	}

	@Test
	public void setATMModelTest() {
		ATMModel atmmodel = new ATMModel();
		atmint.setATMBalance(atmmodel);
	}

	@Test
	public void setInitialAccountTest() {

		atmint.setInitalAccount();
	}

}
