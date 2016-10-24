package com.example.unitTests;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import org.junit.Test;

import com.tym.model.Expense;
import com.tym.model.Income;
import com.tym.model.Obligation;
import com.tym.model.Payment;
import com.tym.model.User;
import com.tym.model.UserHasDAO;
import com.tym.model.UserHasExpensesDAO;
import com.tym.model.UserHasIncomesDAO;
import com.tym.model.UserHasObligationsDAO;
import com.tym.model.exceptions.PaymentException;
import com.tym.model.exceptions.UserException;

public class TestUserHasPaymentDAO {

	private static final int ID_AUTO_INCREMENT = 0;
	private static final int PERIOD_QUANTITY = 3;
	private static final int PERIOD_ID = 4;
	private static final String OBLIGATION_DESCRIPTION = "Credit to delete";
	private static final int OBLIGATION_AMOUNT = 50000;
	private static final int O_REPEATING_ID = 4;
	private static final int OBLIGATION_ID = 1;
	private static final String INCOME_DESCRIPTION = "Income Description text";
	private static final int INCOME_AMOUNT = 350;
	private static final int INCOME_ID = 7;
	private static final int USER_ID = 14;
	private static final String EXPENSE_DESCRIPTION = "Expense description text";
	private static final int AMOUNT_EXPENSE = 20;
	private static final int REPEATING_ID = 1;
	private static final int EXPENSE_ID = 1;
	
	private UserHasDAO userHasExpensesDAO = new UserHasExpensesDAO();
	private UserHasDAO userHasIncomesDAO = new UserHasIncomesDAO();
	private UserHasDAO userHasObligationsDAO = new UserHasObligationsDAO();

	@Test
	public void testUser() throws UserException, PaymentException {
		User user = new User(USER_ID, "Pesho", "pesho@abv.bg", "81dc9bdb52d04dc20036dbd8313ed055");

		testUserHasExpenses(user);
		
		testUserHasIncomes(user);
		
		testUserHasObligations(user);
		
		
		System.out.println(user.getPaymentsForMonth(user.getExpenses()));
		
		System.out.println(user.getUpcomingPaymentsForMonth(user.getExpenses()));
		

	}

	void testUserHasObligations(User user) throws PaymentException {
		int oId = userHasObligationsDAO.insertPayment(USER_ID, new Obligation(OBLIGATION_ID, "???", "???", 
							O_REPEATING_ID, OBLIGATION_AMOUNT, LocalDate.now(), OBLIGATION_DESCRIPTION, ID_AUTO_INCREMENT, "???", PERIOD_ID, PERIOD_QUANTITY));
		assertTrue(oId != 0);
		userHasObligationsDAO.selectAndAddAllPaymentsOfUser(user);
		Set<Payment> obligations = user.getObligations();
		System.out.println("Obligations from " + user.getUsername());
		printPayments(obligations);
		userHasObligationsDAO.deletePayment(oId);
	}

	void testUserHasIncomes(User user) throws PaymentException {
		int iId = userHasIncomesDAO.insertPayment(USER_ID, new Income(INCOME_ID, "???", "???", REPEATING_ID, INCOME_AMOUNT , LocalDate.now(), INCOME_DESCRIPTION, ID_AUTO_INCREMENT));
		assertTrue(iId != 0);
		userHasIncomesDAO.selectAndAddAllPaymentsOfUser(user);
		Set<Payment> incomes = user.getIncomes();
		System.out.println("Incomes from " + user.getUsername());
		printPayments(incomes);
		userHasIncomesDAO.deletePayment(iId);
	}

	void testUserHasExpenses(User user) throws PaymentException {
		int eId = userHasExpensesDAO.insertPayment(USER_ID, new Expense(EXPENSE_ID, "???", "???", REPEATING_ID, AMOUNT_EXPENSE, LocalDate.now(), EXPENSE_DESCRIPTION, ID_AUTO_INCREMENT));
		assertTrue(eId != 0);
		userHasExpensesDAO.selectAndAddAllPaymentsOfUser(user);
		Set<Payment> expenses = user.getExpenses();
		System.out.println("Expenses from " + user.getUsername());
		printPayments(expenses);
		userHasExpensesDAO.deletePayment(eId);
	}

	void printPayments(Set<Payment> payments) {
		for (Payment payment : payments) {
			System.out.println(payment.getCategory() + ", " + payment.getAmount() + ", " + payment.getDate() + ", "
					+ payment.getDescription() + ", " + payment.getRepeating() + ", id="+payment.getId());
		}
	}

}
