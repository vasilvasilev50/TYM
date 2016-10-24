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
import com.tym.model.exceptions.ObligationException;
import com.tym.model.exceptions.PaymentException;

@Component
public class UserHasObligationsDAO implements UserHasDAO {

	private static final String DELETE_OBLIGATION_SQL = "DELETE FROM `finance_track_test`.`users_has_obligations` WHERE `id`=?;";
	private static final String INSERT_OBLIGATION_SQL = "INSERT INTO users_has_obligations VALUES (?, ?, ?, ?, ?, ?, ?, ?, null)";

	public int insertPayment(int userId, Payment obligation) throws PaymentException {

		if (obligation != null && userId > 0) {

			Connection connection = DBConnection.getInstance().getConnection();

			try {

				PreparedStatement ps = connection.prepareStatement(INSERT_OBLIGATION_SQL,
						PreparedStatement.RETURN_GENERATED_KEYS);

				ps.setInt(1, userId);
				ps.setInt(2, obligation.getCategoryId());
				ps.setInt(3, obligation.getRepeatingId());
				ps.setInt(4, ((Obligation) obligation).getPeriodId());
				ps.setDouble(5, obligation.getAmount());
				ps.setDate(6, Date.valueOf(obligation.getDate()));
				ps.setString(7, obligation.getDescription());
				ps.setInt(8, ((Obligation) obligation).getPeriodQuantity());

				ps.executeUpdate();

				ResultSet rs = ps.getGeneratedKeys();

				rs.next();
				return rs.getInt(1);

			} catch (SQLException e) {
				throw new PaymentException("Obligation insert failed!", e);
			}
		} else {
			throw new ObligationException("The obligation given is not valid!");
		}
	}

	public void selectAndAddAllPaymentsOfUser(User user) throws PaymentException {

		if (user != null) {

			Connection connection = DBConnection.getInstance().getConnection();

			try {
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT o.obligation_id, o.category, r.value, r.repeating_id, uho.amount, "
								+ "uho.date, uho.description, uho.id, p.period_type, p.period_id, uho.period_quantity FROM users u "
								+ "JOIN users_has_obligations uho ON (uho.user_id = " + user.getUserId()
								+ " AND u.user_id = " + user.getUserId() + ") "
								+ "JOIN obligations o ON (uho.obligation_id = o.obligation_id) "
								+ "JOIN repeatings r ON (r.repeating_id = uho.repeating_id) "
								+ "JOIN period p ON (p.period_id = uho.period_id) ORDER BY date LIMIT 0, 1000");

				Obligation obligation = null;

				while (rs.next()) {
					int categoryId = rs.getInt(1);
					String category = rs.getString(2);
					String repeating = rs.getString(3);
					int reapeatingId = rs.getInt(4);
					double amount = rs.getDouble(5);
					LocalDate date = rs.getDate(6).toLocalDate();
					String description = rs.getString(7);
					int id = rs.getInt(8);
					String period = rs.getString(9);
					int periodId = rs.getInt(10);
					int periodQuantity = rs.getInt(11);

					obligation = new Obligation(categoryId, category, repeating, reapeatingId, amount, date,
							description, id, period, periodId, periodQuantity);
					user.addObligation(obligation);

				}

			} catch (SQLException e) {
				throw new PaymentException("Expenses select failed!", e);
			}
		} else {
			throw new ObligationException("The obligation given is not valid!");
		}
	}

	public boolean deletePayment(int id) throws PaymentException {
		if (id <= 0) {
			
		}
		
		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(DELETE_OBLIGATION_SQL);

			ps.setInt(1, id);
			int deletedRows = ps.executeUpdate();
			if (deletedRows == 0) {
				throw new PaymentException("No such Obligation!");
			}
			return true;

		} catch (SQLException e) {
			throw new PaymentException("Someting went wrong!", e);
		}
	}

}
