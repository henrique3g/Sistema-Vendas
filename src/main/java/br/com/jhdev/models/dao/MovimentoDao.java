package br.com.jhdev.models.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.jhdev.models.beans.Movimento;

/**
 * MovimentacaoDao
 */
public class MovimentoDao {

	private Connection con;
	private PreparedStatement stmt;
	private ResultSet res;
	private String sql = "";

	public String create(Movimento mov){
		con = ConnectionFactory.getConnection();
		sql = "INSERT INTO movimentacoes(dataCaixa, descricao, tipo, valor) VALUES(?, ?, ?, ?)";
		try {
			stmt = con.prepareStatement(sql);
			
			stmt.setDate(1, mov.getDateCaixa());
			stmt.setString(2, mov.getDescricao());
			stmt.setString(3, String.valueOf(mov.getTipo()));
			stmt.setDouble(4, mov.getValor());

			stmt.executeUpdate();
			return "ok";
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		} finally {
			ConnectionFactory.closeConnection(con, stmt);
		}
	}

	public List<Movimento> readAll(){
		List<Movimento> movimentos = new ArrayList<>();
		con = ConnectionFactory.getConnection();
		sql = "SELECT tipo, descricao, valor FROM movimentacoes";
		try {
			stmt = con.prepareStatement(sql);

			res = stmt.executeQuery();

			while(res.next()){
				movimentos.add(new Movimento());
				
			}


		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.closeConnection(con, stmt, res);
		}
		return movimentos;
	}

	public List<Movimento> readAllByDate(LocalDate date) {
		List<Movimento> movimentos = new ArrayList<>();
		con = ConnectionFactory.getConnection();
		sql = "SELECT descricao, tipo, valor FROM movimentacoes WHERE dataCaixa = ?";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setDate(1, Date.valueOf(date));
			res = stmt.executeQuery();


			while(res.next()){
				movimentos.add(new Movimento(res.getString(1), res.getString(2).charAt(0), res.getDouble(3)));
				
			}


		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.closeConnection(con, stmt, res);
		}
		return movimentos;
	}
}