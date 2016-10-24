package com.tym.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.tym.model.connections.DBConnection;
import com.tym.model.exceptions.UserException;

@Component
public class UserDAO {

	private static final String INSERT_USER_SQL = "INSERT INTO users VALUES (null, ?, ?, md5(?))";
	private static final String SELECT_USER_SQL = "SELECT user_id, email FROM users WHERE username = ? AND password = md5(?)";
	private static final String DELETE_USER_SQL = "DELETE FROM users WHERE username = ?";
	private static final String CHECK_IF_USER_ID_EXISTS = "SELECT COUNT(user_id) FROM users WHERE user_id = ?;";

	public int registerUser(User user) throws UserException {
		isUserNull(user);

		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL,
					PreparedStatement.RETURN_GENERATED_KEYS);

			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();

			rs.next();
			return rs.getInt(1);

		} catch (SQLException e) {
			throw new UserException("User registration failed!");
		}

	}

	public User loginUser(User user) throws UserException {
		isUserNull(user);

		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(SELECT_USER_SQL);

			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());

			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				throw new UserException("User login failed!");
			}
			return new User(rs.getInt(1), user.getUsername(), rs.getString(2), "**********");

		} catch (SQLException e) {
			throw new UserException("Someting went wrong!");
		}

	}

	public boolean deleteUser(User user) throws UserException {
		isUserNull(user);

		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(DELETE_USER_SQL);

			ps.setString(1, user.getUsername());
			int deletedRows = ps.executeUpdate();
			if (deletedRows == 0) {
				throw new UserException("No such user!");
			}
			return true;

		} catch (SQLException e) {
			throw new UserException("Someting went wrong!");
		}

	}

	private void isUserNull(User user) throws UserException {
		if (user == null){
			throw new UserException("Invalid user given!");
		}
	}

	public static boolean containsUser(int userId) throws UserException {
		if (userId <= 0){
			throw new UserException("Invalid user id given!");
		}

		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(CHECK_IF_USER_ID_EXISTS);
			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();
			rs.next();
			int result = rs.getInt(1);
			if (result == 0) {
				return false;
			}

		} catch (SQLException e) {
			throw new UserException("Someting went wrong!");
		}

		return true;
	}

}
