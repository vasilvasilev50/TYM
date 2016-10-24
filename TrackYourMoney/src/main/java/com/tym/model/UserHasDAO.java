package com.tym.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tym.model.connections.DBConnection;
import com.tym.model.exceptions.PaymentException;

public interface UserHasDAO {

	int insertPayment(int userId, Payment payment) throws PaymentException;

	void selectAndAddAllPaymentsOfUser(User user) throws PaymentException;

	boolean deletePayment(int id) throws PaymentException;
	
	static boolean isContainedInDB(int objectId, final String statement){
		
		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(statement);
			ps.setInt(1, objectId);
			ResultSet rs = ps.executeQuery(statement);
			rs.next();
			int result = rs.getInt(1);
			if (result == 0) {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
		
	}
	
	static boolean isValidString(String string){
		return (string != null && string.trim() != "");
	}

}