package com.tym.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.tym.model.connections.DBConnection;
import com.tym.model.exceptions.ExpenseException;
import com.tym.model.exceptions.PaymentException;

@Component
public class UserHasExpensesDAO implements UserHasDAO {

	private static final String DELETE_EXPENSE_SQL = "DELETE FROM `finance_track_test`.`users_has_expenses` WHERE `id`=?;";
	private static final String INSERT_EXPENSE_SQL = "INSERT INTO users_has_expenses VALUES (?, ?, ?, ?, ?, ?, null)";

	public int insertPayment(int userId, Payment expense) throws PaymentException {

		if (expense != null && userId > 0) {
			Connection connection = DBConnection.getInstance().getConnection();

			try {

				PreparedStatement ps = connection.prepareStatement(INSERT_EXPENSE_SQL,
						PreparedStatement.RETURN_GENERATED_KEYS);

				ps.setInt(1, userId);
				ps.setInt(2, expense.getCategoryId());
				ps.setInt(3, expense.getRepeatingId());
				ps.setDouble(4, expense.getAmount());
				ps.setDate(5, Date.valueOf(expense.getDate()));
				ps.setString(6, expense.getDescription());

				ps.executeUpdate();

				ResultSet rs = ps.getGeneratedKeys();

				rs.next();
				return rs.getInt(1);

			} catch (SQLException e) {
				throw new PaymentException("Expense insert failed!", e);
			}
		} else {
			throw new ExpenseException("The expense given is not valid!");
		}
	}

	public void selectAndAddAllPaymentsOfUser(User user) throws PaymentException {
		if (user != null) {
			Connection connection = DBConnection.getInstance().getConnection();

			try {
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT e.expenses_id, e.category, "
						+ "r.value, r.repeating_id, uhe.amount, uhe.date, uhe.description, uhe.id FROM users u "
						+ "JOIN users_has_expenses uhe ON (uhe.user_id = " + user.getUserId() + " AND u.user_id = "
						+ user.getUserId() + ") "
						+ "JOIN expenses e ON (uhe.expenses_id = e.expenses_id) JOIN repeatings r "
						+ "ON (r.repeating_id = uhe.repeating_id) order by date LIMIT 0, 1000");

				Expense expense = null;

				while (rs.next()) {
					int categoryId = rs.getInt(1);
					String category = rs.getString(2);
					String repeating = rs.getString(3);
					int reapeatingId = rs.getInt(4);
					double amount = rs.getDouble(5);
					LocalDate date = rs.getDate(6).toLocalDate();
					String description = rs.getString(7);
					int id = rs.getInt(8);

					expense = new Expense(categoryId, category, repeating, reapeatingId, amount, date, description, id);
					user.addExpense(expense);

				}

			} catch (SQLException e) {
				throw new PaymentException("Expenses select failed!", e);
			}
		} else {
			throw new ExpenseException("There is not such a user!");
		}

	}

	public boolean deletePayment(int id) throws PaymentException {
		if (id <= 0){
			throw new PaymentException("Invalid id given");
		}
		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(DELETE_EXPENSE_SQL);

			ps.setInt(1, id);
			int deletedRows = ps.executeUpdate();
			if (deletedRows == 0) {
				throw new PaymentException("No such Expense!");
			}
			return true;

		} catch (SQLException e) {
			throw new PaymentException("Someting went wrong!", e);
		}
	}

}
