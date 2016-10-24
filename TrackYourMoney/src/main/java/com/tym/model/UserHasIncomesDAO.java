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
import com.tym.model.exceptions.IncomeException;
import com.tym.model.exceptions.PaymentException;

@Component
public class UserHasIncomesDAO implements UserHasDAO {

	private static final String INSERT_INCOME_SQL = "INSERT INTO users_has_incomes VALUES (?, ?, ?, ?, ?, ?, null)";
	private static final String DELETE_INCOME_SQL = "DELETE FROM `finance_track_test`.`users_has_incomes` WHERE `id`=?;";

	public int insertPayment(int userId, Payment income) throws PaymentException {
		
		if(income != null && userId > 0){
		
		Connection connection = DBConnection.getInstance().getConnection();

		try {
			
			PreparedStatement ps = connection.prepareStatement(INSERT_INCOME_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
			
			ps.setInt(1, userId);
			ps.setInt(2,income.getCategoryId());
			ps.setInt(3, income.getRepeatingId());
			ps.setDouble(4, income.getAmount());
			ps.setDate(5, Date.valueOf(income.getDate()));
			ps.setString(6,income.getDescription());
			
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();

			rs.next();
			return rs.getInt(1);

		} catch (SQLException e) {
			throw new PaymentException("Income insert failed!",e);
		}
		}
		else{
			throw new IncomeException("Invalid income given!");
		}
	}

	public void selectAndAddAllPaymentsOfUser(User user) throws PaymentException {
		
		if(user != null){
		
		Connection connection = DBConnection.getInstance().getConnection();

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT i.incomes_id, i.category,r.value, r.repeating_id, "
					+ "uhi.amount, uhi.date, uhi.description, uhi.id FROM users u "
					+ "JOIN users_has_incomes uhi ON (uhi.user_id = "+user.getUserId()+" AND u.user_id = "+user.getUserId()+") "
					+ "JOIN incomes i ON (uhi.incomes_id = i.incomes_id) "
					+ "JOIN repeatings r ON (r.repeating_id = uhi.repeating_id) order by date LIMIT 0, 1000");

			Income income = null;
			while (rs.next()) {
				int categoryId = rs.getInt(1);
				String category = rs.getString(2);
				String repeating = rs.getString(3);
				int reapeatingId = rs.getInt(4);
				double amount = rs.getDouble(5);
				LocalDate date = rs.getDate(6).toLocalDate();
				String description = rs.getString(7);
				int id = rs.getInt(8);
				
				income = new Income(categoryId, category, repeating, reapeatingId, amount, date, description, id);
				System.out.println("************"+income);
				user.addIncome(income);
	
			}

		} catch (SQLException e) {
			throw new PaymentException("Incomes select failed!");
		}
		
		}
		else{
			throw new IncomeException("There is not such a user!");
		}
		
	}

	public boolean deletePayment(int id) throws PaymentException {
		if (id <= 0) {
			throw new IncomeException("Invalid id givenr!");
		}
		
		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(DELETE_INCOME_SQL);

			ps.setInt(1, id);
			int deletedRows = ps.executeUpdate(); 
			if (deletedRows == 0){
				throw new PaymentException("No such Income!");
			}
			return true;

		} catch (SQLException e) {
			throw new PaymentException ("Someting went wrong!",e);
		} 
	}
	
}
