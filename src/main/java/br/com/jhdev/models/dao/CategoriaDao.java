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

	private Connection con = null;
	private PreparedStatement stmt = null;
	private String sql = "";
	private ResultSet res = null;

	public String create(Categoria cat){
		con = ConnectionFactory.getConnection();
		
		sql = "INSERT INTO categorias(nome) VALUES (?)";
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, cat.getNome());
			stmt.executeUpdate();
			return "ok";
		} catch (SQLException e) {
			return e.getMessage();
		}finally{
			ConnectionFactory.closeConnection(con, stmt);
		}

	}

	public List<Categoria> readAll(){
		List<Categoria> categorias = new ArrayList<>();
		
		con = ConnectionFactory.getConnection();
		
		sql = "SELECT * FROM categorias ORDER BY nome";
		
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

	public Categoria read(int idCat){
		con = ConnectionFactory.getConnection();
		sql = "SELECT * FROM categorias WHERE idCategoria = ?";

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, idCat);
			res = stmt.executeQuery();
			res.next();
			return new Categoria(res.getInt(1), res.getString(2));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return null;
		} finally {
			ConnectionFactory.closeConnection(con, stmt, res);
		}
	}

	public String update(Categoria cat){
		con = ConnectionFactory.getConnection();
		sql = "UPDATE categorias SET nome = ? WHERE idCategoria = ? LIMIT 1";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, cat.getNome());
			stmt.setInt(2, cat.getId());
			stmt.executeQuery();
			return "ok";
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return e.getMessage();
		} finally {
			ConnectionFactory.closeConnection(con, stmt);	
		}
	}

	public String delete(int idCat){
		con = ConnectionFactory.getConnection();
		sql = "DELETE FROM categorias WHERE idCategoria = ? LIMIT 1";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, idCat);
			stmt.executeUpdate();
			return "ok";
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return e.getMessage();
		} finally {
			ConnectionFactory.closeConnection(con, stmt);
		}
	}
}