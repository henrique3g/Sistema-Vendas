package br.com.jhdev.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.jhdev.models.ConnectionFactory;
import br.com.jhdev.models.beans.Regiao;

/**
 * DaoRegiao
 */
public class DaoRegiao {
	
	public int create(Regiao regiao) {
		
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		final String sql = "INSERT INTO regioes(nome, povoados) VALUES (?, ?)";

		try {
			stmt = con.prepareStatement(sql);

			stmt.setString(1, regiao.getNome());
			stmt.setString(2, regiao.getPovoados());

			stmt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			//System.err.println("Mens: " + e.getMessage() + " - CodErro: " + e.getErrorCode());
			return e.getErrorCode();
		} finally {
			ConnectionFactory.closeConnection(con, stmt);
		}
	}

	public Regiao read(int id) {
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet res = null;
		final String sql = "SELECT * FROM regioes WHERE idRegiao = ?";
		Regiao rg = null;

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			res = stmt.executeQuery();
			if(res.next()){
				rg = new Regiao(res.getInt(1), res.getString(2), res.getString(3));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionFactory.closeConnection(con, stmt, res);
		}
		return rg;
	}

	public List<Regiao> readAll() {
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet res = null;
		final String sql = "SELECT * FROM regioes ORDER BY nome";
		List<Regiao> listRegiao = new ArrayList<>();

		try {
			stmt = con.prepareStatement(sql);

			res = stmt.executeQuery();

			while(res.next()){
				listRegiao.add(new Regiao(res.getInt(1), res.getString(2), res.getString(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionFactory.closeConnection(con, stmt, res);
		}
		return listRegiao;
	}

	public int update(Regiao regiao) {
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		final String sql = "UPDATE regioes SET nome = ?, povoados = ? WHERE idRegiao = ?";

		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, regiao.getNome());
			stmt.setString(2, regiao.getPovoados());
			stmt.setInt(3, regiao.getId());

			stmt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getErrorCode();
		}finally{
			ConnectionFactory.closeConnection(con, stmt);
		}
	}

	public int delete(int idRegiao) {
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		final String sql = "DELETE from regioes WHERE idRegiao = ?";

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, idRegiao);

			System.out.println(stmt.executeUpdate());
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getErrorCode();
		}finally{
			ConnectionFactory.closeConnection(con, stmt);
		}
	}
}