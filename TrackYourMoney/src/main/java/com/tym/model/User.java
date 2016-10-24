package com.tym.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import com.google.gson.Gson;
import com.tym.model.exceptions.BudgetException;
import com.tym.model.exceptions.ExpenseException;
import com.tym.model.exceptions.IncomeException;
import com.tym.model.exceptions.ObligationException;
import com.tym.model.exceptions.PaymentException;
import com.tym.model.exceptions.UserException;

public class User {

	private static final int YEARS = 1;
	private static final int MONTHS = 1;
	private static final int WEEKS = 1;
	private static final int DAYS = 1;
	private static final int NO_CAT_ID = 0;
	private static final int ONCE_REP_ID = 1;
	private static final int YEARLY_REP_ID = 5;
	private static final int MONTHLY_REP_ID = 4;
	private static final int WEEKLY_REP_ID = 3;
	private static final int DAILY_REP_ID = 2;

	private int userId;
	private String username;
	private String password;
	@NotNull
	@Email
	private String email;
	private Set<Payment> expenses = new LinkedHashSet<Payment>();
	private Set<Payment> incomes = new LinkedHashSet<Payment>();
	private Set<Obligation> obligations = new LinkedHashSet<Obligation>();
	private Set<Budget> budgets = new LinkedHashSet<Budget>();

	public User() {
	}

	public User(int id, String username, String email, String password) {
		/*
		 * throwing an exception in case of invalid data given
		 */
		if (isValidString(username) || isValidString(email) || isValidString(password)) {
			try {
				throw new UserException("Invalid user data!");
			} catch (UserException e) {
				System.out.println(e.getMessage());
				return;
			}
		}
		this.userId = id;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	boolean isValidString(String string) {
		return string == null || string.trim().equals("");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public int getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public boolean addExpense(Expense expense) throws ExpenseException {
		if (expense != null) {
			return this.expenses.add(expense);
		} else {
			throw new ExpenseException("Invalid expense given!");
		}
	}

	public boolean addIncome(Income income) throws IncomeException {
		if (income != null) {
			// this.balance += income.getAmount();
			return this.incomes.add(income);
		} else {
			throw new IncomeException("Invalid income given");
		}
	}

	public boolean addObligation(Obligation obligation) throws ObligationException {
		if (obligation != null) {
			return this.obligations.add(obligation);
		} else {
			throw new ObligationException("Invalid obligation given!");
		}
	}

	public boolean addBudget(Budget budget) throws BudgetException {
		if (budget != null) {
			return this.budgets.add(budget);
		} else {
			throw new BudgetException("Invalid budget given!");
		}
	}

	public Set<Payment> getExpenses() {
		return Collections.unmodifiableSet(expenses);
	}

	public Set<Payment> getIncomes() {
		return Collections.unmodifiableSet(incomes);
	}

	public Set<Payment> getObligations() {
		return Collections.unmodifiableSet(obligations);
	}

	public Set<Budget> getBudgets() {
		return Collections.unmodifiableSet(budgets);
	}

	public void setUsername(String username) throws UserException {
		if (UserHasDAO.isValidString(username)) {
			this.username = username;
		} else {
			throw new UserException("Invalid username given!");
		}
	}

	public void setPassword(String password) throws UserException {
		if (UserHasDAO.isValidString(password)) {
			this.password = password;
		} else {
			throw new UserException("Invalid password given!");
		}
	}

	public void setEmail(String email) throws UserException {
		if (UserHasDAO.isValidString(email)) {
			this.email = email;
		} else {
			throw new UserException("Invalid email given!");
		}
	}

	public void removeIncome(int id) throws UserException {
		if (isValidId(id)) {
			for (Payment income : incomes) {
				if (income.getId() == id) {
					incomes.remove(income);
					return;
				}
			}
		} else {
			throw new UserException("Invalid id given - cannot remove the income!");
		}
	}

	private boolean isValidId(int id) {
		return id > 0;
	}

	public void removeExpense(int id) throws UserException {
		if (isValidId(id)) {
			for (Payment expense : this.expenses) {
				if (expense.getId() == id) {
					expenses.remove(expense);
					return;
				}
			}

		} else {
			throw new UserException("Invalid id given - cannot remove the expense!");
		}
	}

	public void removeBudget(int expenseId) throws UserException {
		if (isValidId(expenseId)) {
			for (Budget budget : this.budgets) {
				if (budget.getExpenseId() == expenseId) {
					this.budgets.remove(budget);
					return;
				}
			}
		} else {
			throw new UserException("Invalid id given - cannot remove the budget!");
		}
	}

	public void removeObligation(int id) throws UserException {
		if (isValidId(id)) {
			for (Payment obligation : this.obligations) {
				if (obligation.getId() == id) {
					this.obligations.remove(obligation);
					return;
				}
			}

		} else {
			throw new UserException("Invalid id given - cannot remove the obligation!");
		}
	}

	public double getBalance() {
		try {
			return getTotalAmountFor(this.incomes) - getTotalAmountFor(this.expenses);
		} catch (UserException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/*
	 * calculating the total amount of money for collection of payments
	 * calculating the total amount from payment date to now by repeating
	 * payments
	 */
	public double getTotalAmountFor(Collection<Payment> payments) throws UserException {
		if (payments == null || payments.size() == 0) {
			return 0;
		}
		LocalDate now = LocalDate.now();
		double totalPyments = 0;
		for (Payment payment : payments) {
			LocalDate paymentDate = payment.getDate();
			switch (payment.getRepeatingId()) {
			case DAILY_REP_ID:
				for (LocalDate date = paymentDate; !date.isAfter(now); date = date.plusDays(1)) {
					totalPyments += payment.getAmount();
				}
				break;
			case WEEKLY_REP_ID:
				for (LocalDate date = paymentDate; !date.isAfter(now); date = date.plusDays(7)) {
					totalPyments += payment.getAmount();

				}
				break;
			case MONTHLY_REP_ID:
				for (LocalDate date = paymentDate; !date.isAfter(now); date = date.plusMonths(1)) {
					totalPyments += payment.getAmount();
				}
				break;
			case YEARLY_REP_ID:
				for (LocalDate date = paymentDate; !date.isAfter(now); date = date.plusYears(1)) {
					totalPyments += payment.getAmount();
				}
				break;
			default:
				totalPyments += payment.getAmount();
				break;
			}
		}
		return totalPyments;
	}

	public double getTotalBudgets() {
		double totalAmount = 0;
		for (Budget budget : this.budgets) {
			totalAmount += budget.getAmount();
		}
		return totalAmount;
	}

	public double getTotalObligations() {
		double totalAmount = 0;
		for (Payment obligation : this.obligations) {
			totalAmount += obligation.getAmount();
		}
		return totalAmount;
	}

	public double getTotalPaidObligations() {
		double totalAmount = 0;
		for (Payment obligation : this.obligations) {
			totalAmount += ((Obligation) obligation).getPaidAmount();
		}
		return totalAmount;
	}

	public double getTotalRemainObligations() {
		double totalAmount = 0;
		for (Payment obligation : this.obligations) {
			totalAmount += ((Obligation) obligation).getRemainedAmount();
		}
		return totalAmount;
	}

	public List<Payment> getUpcomingPaymentsForMonth(Collection<Payment> payments) throws UserException {
		if (payments == null || payments.size() == 0) {
			return new ArrayList<Payment>();
		}
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String from = now.format(formatter);
		int remainingDays = now.lengthOfMonth() - now.getDayOfMonth();
		now = now.plusDays(remainingDays);
		String to = now.format(formatter);

		return this.getPaymentsBy(from, to, 0, payments);
	}

	public List<Payment> getPaymentsBy(String from, String to, int categoryId, Collection<Payment> payments)
			throws UserException {

		/*
		 * checking if the data given are valid then parsing the dates given to
		 * LocalDate in order to find the expenses which date is between the
		 * initial and the end date by a specific category given
		 */

		if (payments == null || payments.size() == 0 || categoryId < 0) {
			return new ArrayList<Payment>();
		}

		if (UserHasDAO.isValidString(from) && UserHasDAO.isValidString(to)) {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDate parsedDateFrom = LocalDate.parse(from, formatter);
			LocalDate parsedDateTo = LocalDate.parse(to, formatter);
			List<Payment> paymentsBy = new LinkedList<Payment>();
			for (Payment payment : payments) {
				LocalDate paymentDate = payment.getDate();
				if ((!paymentDate.isBefore(parsedDateFrom) || payment.getRepeatingId() > ONCE_REP_ID)
						&& !paymentDate.isAfter(parsedDateTo)) {
					calculateAndAddRepeatingPayments(categoryId, parsedDateFrom, parsedDateTo, paymentsBy, payment,
							paymentDate);
				}
			}
			return sortCollectionByDate(paymentsBy);
		} else {
			throw new UserException("Invalid data given!");
		}
	}

	private List<Payment> sortCollectionByDate(List<Payment> paymentsBy) {
		Collections.sort(paymentsBy, new Comparator<Payment>() {
			public int compare(Payment o1, Payment o2) {
				return o1.getDate().compareTo(o2.getDate());
			}
		});
		return paymentsBy;
	}

	private void calculateAndAddRepeatingPayments(int categoryId, LocalDate parsedDateFrom, LocalDate parsedDateTo,
			List<Payment> paymentsBy, Payment payment, LocalDate paymentDate) {
		Payment paymentToAdd = null;
		switch (payment.getRepeatingId()) {
		case ONCE_REP_ID:
			if (categoryId != NO_CAT_ID) {
				if (payment.getCategoryId() == categoryId) {
					paymentsBy.add(payment);
				}
				return;
			}
			paymentsBy.add(payment);
			return;
		case DAILY_REP_ID:
			for (LocalDate date = paymentDate; !date.isAfter(parsedDateTo); date = date.plusDays(DAYS)) {
				if (!date.isBefore(parsedDateFrom) && !date.isAfter(parsedDateTo)) {
					try {
						paymentToAdd = (Payment) payment.getCopy();
						paymentToAdd.setLocalDate(date);
						paymentToAdd.setRepeatingId(1);
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					} catch (PaymentException e) {
						e.printStackTrace();
					}
					if (categoryId != NO_CAT_ID) {
						if (payment.getCategoryId() == categoryId) {
							paymentsBy.add(paymentToAdd);
						}
						continue;
					}
					paymentsBy.add(paymentToAdd);
				}
			}
			break;
		case WEEKLY_REP_ID:
			for (LocalDate date = paymentDate; !date.isAfter(parsedDateTo); date = date.plusWeeks(WEEKS)) {
				if (!date.isBefore(parsedDateFrom) && !date.isAfter(parsedDateTo)) {
					try {
						paymentToAdd = (Payment) payment.getCopy();
						paymentToAdd.setLocalDate(date);
						paymentToAdd.setRepeatingId(1);
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					} catch (PaymentException e) {
						e.printStackTrace();
					}
					if (categoryId != NO_CAT_ID) {
						if (payment.getCategoryId() == categoryId) {
							paymentsBy.add(paymentToAdd);
						}
						continue;
					}
					paymentsBy.add(paymentToAdd);
				}
			}
			break;
		case MONTHLY_REP_ID:
			for (LocalDate date = paymentDate; !date.isAfter(parsedDateTo); date = date.plusMonths(MONTHS)) {
				if (!date.isBefore(parsedDateFrom) && !date.isAfter(parsedDateTo)) {
					try {
						paymentToAdd = (Payment) payment.getCopy();
						paymentToAdd.setLocalDate(date);
						paymentToAdd.setRepeatingId(1);
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					} catch (PaymentException e) {
						e.printStackTrace();
					}
					if (categoryId != NO_CAT_ID) {
						if (payment.getCategoryId() == categoryId) {
							paymentsBy.add(paymentToAdd);
						}
						continue;
					}
					paymentsBy.add(paymentToAdd);

				}
			}
			break;
		case YEARLY_REP_ID:
			for (LocalDate date = paymentDate; date.isAfter(parsedDateTo); date = date.plusYears(YEARS)) {
				if (!date.isBefore(parsedDateFrom) && !date.isAfter(parsedDateTo)) {
					try {
						paymentToAdd = (Payment) payment.getCopy();
						paymentToAdd.setLocalDate(date);
						paymentToAdd.setRepeatingId(1);
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					} catch (PaymentException e) {
						e.printStackTrace();
					}
					if (categoryId != NO_CAT_ID) {
						if (payment.getCategoryId() == categoryId) {
							paymentsBy.add(paymentToAdd);
						}
						continue;
					}
					paymentsBy.add(paymentToAdd);
				}
			}
			break;
		}
	}

	/*
	 * getting the total amount of money for payments having a given category
	 */
	public double getAmoutByPaymentCategoryId(int categoryId, Collection<Payment> payments) {
		if (payments == null || payments.size() == 0 || categoryId < 0) {
			return 0;
		}
		List<Payment> paymentsByCategory = new LinkedList<>();
		for (Payment payment : payments) {
			if (payment.getCategoryId() == categoryId) {
				paymentsByCategory.add(payment);
			}
		}
		try {
			return this.getTotalAmountFor(paymentsByCategory);
		} catch (UserException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/*
	 * getting the remaining amount of money of the budgets for a given
	 * category, calculating all budgets amount and then removing the amount of
	 * money given as expenses
	 */
	public double getRemainAmountForBudget(int expenseId) {
		if (expenseId <= 0) {
			return 0;
		}
		double amount = 0;
		List<Payment> budgetExpenses = new LinkedList<>();
		for (Budget budget : this.budgets) {
			if (budget.getExpenseId() == expenseId) {
				amount += budget.getAmount();
			}
		}

		for (Payment expense : this.expenses) {
			if (expense.getCategoryId() == expenseId) {
				budgetExpenses.add(expense);
			}
		}
		return amount - this.getPaymentsForMonth(budgetExpenses);
	}

	/*
	 * getting all money for payments for the current month
	 */
	public double getPaymentsForMonth(Collection<Payment> payments) {
		if (payments == null || payments.size() == 0) {
			return 0;
		}
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate firstDay = now.minusDays(now.getDayOfMonth() - 1);
		String to = now.format(formatter);
		String from = firstDay.format(formatter);

		List<Payment> paymentsForMonths = null;
		try {
			paymentsForMonths = this.getPaymentsBy(from, to, 0, payments);
		} catch (UserException e) {
			e.printStackTrace();
		}
		double paymentsForMonth = 0;

		for (Payment income : paymentsForMonths) {
			paymentsForMonth += income.getAmount();
		}
		return paymentsForMonth;
	}

	public double getBalanceForMonth() {
		return getPaymentsForMonth(this.incomes) - getPaymentsForMonth(this.expenses);
	}

	public String getObligationsJson() {
		for (Obligation obligation : obligations) {
			obligation.getRemainedAmount();
		}
		return new Gson().toJson(obligations);
	}

	public String getBudgetsJson() throws BudgetException {
		for (Budget budget : budgets) {
			budget.getRemainedAmount(this, budget.getExpenseId());
		}
		return new Gson().toJson(budgets);
	}

}
