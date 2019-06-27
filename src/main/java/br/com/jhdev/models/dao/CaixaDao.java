package br.com.jhdev.models.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import br.com.jhdev.models.beans.Caixa;

/**
 * CaixaDao
 */
public class CaixaDao {

	private Connection con;
	private PreparedStatement stmt;
	private ResultSet res;
	private String sql;
	
	public String abrirCaixa(Caixa caixa){
		con = ConnectionFactory.getConnection();
		sql = "INSERT INTO caixas(dataCaixa, entrada) VALUES(?, ?)";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setDate(1, Date.valueOf(caixa.getDataCaixa()));
			stmt.setDouble(2, caixa.getEntrada());
			stmt.executeUpdate();
			return "ok";
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return e.getMessage();		
		} finally {
			ConnectionFactory.closeConnection(con, stmt);
		}
	}

	public Caixa read(LocalDate data) {
		con = ConnectionFactory.getConnection();
		sql = "SELECT entrada, state FROM caixas WHERE dataCaixa = ?";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setDate(1, Date.valueOf(data));
			res = stmt.executeQuery();
			
			if(res.next()){
				Caixa caixa = new Caixa(data, res.getDouble(1), res.getBoolean(2));
				return caixa;
			}
			return null;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			ConnectionFactory.closeConnection(con, stmt, res);
		}

	}

	public Caixa readFull(LocalDate data) {
		Caixa caixa = read(data);
		if(caixa != null) {
			caixa.setMovimentos(new MovimentoDao().readAllByDate(caixa.getDataCaixa()));
			return caixa;
		}
		return null;
	}
	public List<Caixa> readAll() {
		return null;
	}

	public LocalDate getDataCaixaAtual() {
		con = ConnectionFactory.getConnection();
		sql = "SELECT MAX(dataCaixa) FROM caixas WHERE state = 1";
		try {
			stmt = con.prepareStatement(sql);
			res = stmt.executeQuery();

			res.next();
			if(res.getDate(1) != null) {
				System.out.println(res.getDate(1));
				return res.getDate(1).toLocalDate();
			}
			return null;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return null;
		} finally {
			ConnectionFactory.closeConnection(con, stmt, res);
		}
	}

	public String fecharCaixa(Caixa caixa) {
		con = ConnectionFactory.getConnection();
		sql = "UPDATE caixas SET state = ?, descricao = ? WHERE dataCaixa = ? LIMIT 1";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setBoolean(1, false);
			stmt.setString(2, caixa.getDescricao());
			stmt.setDate(3, Date.valueOf(caixa.getDataCaixa()));
			stmt.executeUpdate();
			return "ok";
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		} finally {
			ConnectionFactory.closeConnection(con, stmt);
		}
	}



	
}