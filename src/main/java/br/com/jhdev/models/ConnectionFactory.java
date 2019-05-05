package br.com.jhdev.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Connection
 */
public class ConnectionFactory {
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/sistema_vendas";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	public static Connection getConnection(){
		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			Alert alert = new Alert(AlertType.ERROR, "Erro ao conectar com o banco!");
			alert.showAndWait();
			throw new RuntimeException("Erro na conexão: ",e);
		}
	}
	
	public static void closeConnection(Connection con){
		try {
			if(con != null){
				con.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeConnection(Connection con, PreparedStatement stmt){
		closeConnection(con);
		try {
			if(stmt != null){
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet res){
		closeConnection(con, stmt);
		try {
			if(res != null){
				res.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}