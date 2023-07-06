package model.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.entity.UserOng;

public class UserOngDAO {
	public boolean registerUserOng(UserOng user, int idAdress) throws SQLException, ClassNotFoundException, IOException {
		Connection connection = null;
		PreparedStatement statement = null;
        String sql = "INSERT INTO userOng (email, pw, username, cpf, birth, ongName, publicKey, privateKey, idAdress) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
        	connection = new DatabaseConnector().connectDB();
            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPw());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getCpf());
            statement.setString(5, user.getBirth());
            statement.setString(6, user.getOngName());
            statement.setString(7, user.getPublicKey());
            statement.setString(8, user.getPrivateKey());
            statement.setInt(9, idAdress);
            @SuppressWarnings("unused")
			int update = statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                @SuppressWarnings("unused")
				int id = keys.getInt(1);
                return true;
            }else {
                throw new SQLException("Nenhuma chave primaria foi gerada.");
            }
        }catch (SQLException e) {
            System.err.println("Erro durante o registro: " + e.getMessage());
            return false;
        }finally {
        	if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
	public boolean checkForDuplicityOngEmail(String login) throws ClassNotFoundException, IOException, SQLException {
		Connection conn = null;
		PreparedStatement statement = null;
		String sql = "SELECT * FROM userOng WHERE email = ?";
		try {   
			conn = new DatabaseConnector().connectDB();
	        statement = conn.prepareStatement(sql);
	        statement.setString(1, login);
	        ResultSet rs = statement.executeQuery();
	        return rs.next();
	    }catch (SQLException e) {
	        System.out.println(e.getMessage());
	        return false;
	    }finally {
        	if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
	            }
		    }
		}
	public boolean checkForDuplicityOngName(String ongName) throws ClassNotFoundException, IOException, SQLException {
		Connection conn = null;
		PreparedStatement statement = null;
		String sql = "SELECT * FROM userOng WHERE ongName = ?";
		try {
		    conn = new DatabaseConnector().connectDB();
	        statement = conn.prepareStatement(sql);
	        statement.setString(1, ongName);
	        ResultSet rs = statement.executeQuery();
	        return rs.next();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	        return false;
	    }finally {
        	if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
	    }
	}
	public boolean checkForDuplicityOngCPF(String cpf) throws ClassNotFoundException, IOException, SQLException {
		Connection conn = null;
		PreparedStatement statement = null;
		String sql = "SELECT * FROM userOng WHERE cpf = ?";
		try {
			conn = new DatabaseConnector().connectDB();
	        statement = conn.prepareStatement(sql);
	        statement.setString(1, cpf);
	        ResultSet rs = statement.executeQuery();
	        return rs.next();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	        return false;
	    }finally {
        	if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
	    }
	}
	public boolean validateLoginOng(String hashPassword, String email) throws ClassNotFoundException, IOException, SQLException {
		Connection conn = null;
		PreparedStatement statement = null;
		String sql = "SELECT * FROM userOng WHERE email =? AND pw =?";
		try{
			conn = new DatabaseConnector().connectDB();
			statement = conn.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, hashPassword);
			ResultSet rs = statement.executeQuery();
			return rs.next();
		}catch(SQLException e) {
				System.out.println(e.getMessage());
				return false;
		}finally {
        	if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
		}
	}
}
