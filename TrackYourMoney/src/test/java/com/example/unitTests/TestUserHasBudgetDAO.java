package com.example.unitTests;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import org.junit.Test;

import com.tym.model.Budget;
import com.tym.model.User;
import com.tym.model.UserHasBudgetsDAO;
import com.tym.model.exceptions.BudgetException;
import com.tym.model.exceptions.PaymentException;

public class TestUserHasBudgetDAO {
	
	private static final String REPEATING_TYPE = "???";
	private static final String EXPENSE_TYPE = "....";
	private static final String PASSWORD = "81dc9bdb52d04dc20036dbd8313ed055";
	private static final String EMAIL = "pesho@abv.bg";
	private static final String USERNAME = "Pesho";
	private static final String BUDGET_DESCRIPTION = "Biudjet za transport";
	private static final LocalDate DATE = LocalDate.now();
	private static final int AMOUNT = 120;
	private static final int REPEATING_ID = 4;
	private static final int EXSPENSE_ID = 2;
	private static final int USER_ID = 14;	
	
	private UserHasBudgetsDAO userHasBudgetsDAO = new UserHasBudgetsDAO();

	@Test
	public void test() throws PaymentException, BudgetException {
		User user = new User(USER_ID, USERNAME, EMAIL, PASSWORD);
		
		boolean isInserted = userHasBudgetsDAO.insertBudget
				(USER_ID, new Budget(USER_ID, EXSPENSE_ID, EXPENSE_TYPE, REPEATING_ID, REPEATING_TYPE, AMOUNT, DATE, BUDGET_DESCRIPTION));
		assertTrue(isInserted);
		
		userHasBudgetsDAO.selectAndAddAllBudgetsOfUser(user);
		Set<Budget> budgets = user.getBudgets();
		System.out.println("Budgets from " + user.getUsername());
		printBudgets(budgets);
		boolean isDeleted = userHasBudgetsDAO.deleteBudget(USER_ID, EXSPENSE_ID);
		assertTrue(isDeleted);
		
	}
	
	void printBudgets(Set<Budget> budgets) {
		for (Budget budget : budgets) {
			System.out.println(budget.getDescription() + ", " + budget.getExpense() + ", " + budget.getDate() + ", "
					 + budget.getRepeating() + ", " + budget.getAmount());
		}
	}

}
