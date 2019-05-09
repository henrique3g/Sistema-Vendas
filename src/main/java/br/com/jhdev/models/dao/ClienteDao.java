package br.com.jhdev.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.jhdev.models.dao.ConnectionFactory;
import br.com.jhdev.models.beans.Cliente;
import br.com.jhdev.models.beans.Regiao;

/**
 * ClienteDao
 */
public class ClienteDao {

	private Connection con = null;
	private PreparedStatement stmt = null;
	private ResultSet res = null;
	private String sql = null;
	private List<Cliente> clientes= null;


	public String create(Cliente cliente){
		con = ConnectionFactory.getConnection();
		sql = "INSERT INTO clientes(nomeCompleto, nome, rg, cpf, endereco, telefone1, telefone2, idRegiao) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getApelido());
			stmt.setString(3, cliente.getRg());
			stmt.setString(4, cliente.getCpf());
			stmt.setString(5, cliente.getEndereco());
			stmt.setString(6, cliente.getTelefone1());
			stmt.setString(7, cliente.getTelefone2());
			stmt.setInt(8, cliente.getRegiao().getId());

			stmt.executeUpdate();
			return "ok";
		} catch (SQLException e) {
			return e.getMessage();
		}finally{
			ConnectionFactory.closeConnection(con, stmt);
		}
	}

	public Cliente read(int idCliente){
		con = ConnectionFactory.getConnection();
		sql = "SELECT clientes.idCliente, clientes.nomeCompleto, clientes.nome, clientes.rg, clientes.cpf," +
			  "clientes.endereco, clientes.telefone1, clientes.telefone2, regioes.idRegiao,regioes.nome from clientes join regioes" +
			  " on clientes.idRegiao = regioes.idRegiao WHERE idCliente = ?";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, idCliente);
			res = stmt.executeQuery();
			res.next();
			return new Cliente(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7), res.getString(8), new Regiao(res.getInt(9), res.getString(10)));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return null;		
		} finally {
			ConnectionFactory.closeConnection(con, stmt, res);
		}
	}
	public List<Cliente> readAll(){
		clientes = new ArrayList<>();
		
		con = ConnectionFactory.getConnection();

		sql = "SELECT clientes.idCliente, clientes.nomeCompleto, clientes.nome, clientes.rg, clientes.cpf," +
			  "clientes.endereco, clientes.telefone1, clientes.telefone2, regioes.idRegiao,regioes.nome from clientes join regioes" +
			  " on clientes.idRegiao = regioes.idRegiao ORDER BY clientes.nome";

		try {
			stmt = con.prepareStatement(sql);
			res = stmt.executeQuery();
			while(res.next()){
				clientes.add(new Cliente(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7), res.getString(8), new Regiao(res.getInt(9), res.getString(10))));
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}finally{
			ConnectionFactory.closeConnection(con, stmt, res);
		}
		return clientes;	

	}

	public String update(Cliente cliente){
		con = ConnectionFactory.getConnection();
		sql = "UPDATE clientes SET nomeCompleto = ?, nome = ?, rg = ?, cpf = ?, endereco = ?, telefone1 = ?, telefone2 = ?, idRegiao = ? WHERE idCliente = ?";
		// System.out.println(cliente.getRegiao().getId()+"::"+cliente.getRegiao().getNome());
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getApelido());
			stmt.setString(3, cliente.getRg());
			stmt.setString(4, cliente.getCpf());
			stmt.setString(5, cliente.getEndereco());
			stmt.setString(6, cliente.getTelefone1());
			stmt.setString(7, cliente.getTelefone2());
			stmt.setInt(8, cliente.getRegiao().getId());
			stmt.setInt(9, cliente.getId());
			stmt.executeUpdate();
			System.out.println("retornou 1 daocliente");
			return "ok";
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			
			//System.out.println("CodeErroSql");
			return e.getMessage();
		}finally {
			ConnectionFactory.closeConnection(con, stmt);
		}
	}

	public int delete(int idCliente){
		con = ConnectionFactory.getConnection();
		sql = "DELETE FROM `clientes` WHERE `clientes`.`idCliente` = ? LIMIT 1";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, idCliente);			
			stmt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return e.getErrorCode();
		} finally {
			ConnectionFactory.closeConnection(con, stmt);
		}
		
	}
}