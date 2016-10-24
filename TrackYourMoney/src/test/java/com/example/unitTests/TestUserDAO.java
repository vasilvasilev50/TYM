package com.example.unitTests;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import com.tym.model.User;
import com.tym.model.UserDAO;
import com.tym.model.exceptions.UserException;

public class TestUserDAO {

	private UserDAO userDAO = new UserDAO();
	private int randomNumber = new Random().nextInt(1000);
	
	@Test
	public void testUser() throws UserException {
		User user = new User(0, "Panaiot"+randomNumber, "gosho@abv.bg", "1234");
		
		int id = this.userDAO.registerUser(user);
		assertTrue(id != 0);

		user = this.userDAO.loginUser(user);
		assertTrue(user.getUserId() != 0);
		
		boolean isDeleted = this.userDAO.deleteUser(user);
		assertTrue(isDeleted);
		
	}

}
