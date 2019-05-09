package br.com.jhdev.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.jhdev.models.beans.Categoria;

/**
 * CategoriaDao
 */
public class CategoriaDao {

	public int create(Categoria cat){
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		final String sql = "INSERT INTO categorias(nome) VALUES (?)";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, cat.getNome());
			stmt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			return e.getErrorCode();
		}finally{
			ConnectionFactory.closeConnection(con, stmt);
		}

	}

	public List<Categoria> readAll(){
		List<Categoria> categorias = new ArrayList<>();
		
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet res = null;
		final String sql = "SELECT * FROM categorias ORDER BY nome";
		try {
			stmt = con.prepareStatement(sql);
			res = stmt.executeQuery();
			while(res.next()){
				categorias.add(new Categoria(res.getInt(1), res.getString(2)));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}finally{
			ConnectionFactory.closeConnection(con, stmt, res);
		}
		
		return categorias;
	}
}