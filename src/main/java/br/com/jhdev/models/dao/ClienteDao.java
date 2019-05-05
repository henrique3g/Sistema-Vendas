package br.com.jhdev.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.jhdev.models.ConnectionFactory;
import br.com.jhdev.models.beans.Cliente;

/**
 * ClienteDao
 */
public class ClienteDao {

	public int create(Cliente cliente){
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		final String sql = "INSERT INTO clientes(nomeCompleto, nome, rg, cpf, endereco, telefone1, telefone2, idRegiao) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
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
			return 1;
		} catch (SQLException e) {
			return e.getErrorCode();
		}finally{
			ConnectionFactory.closeConnection(con, stmt);
		}
	}

	public List<Cliente> readAll(){
		List<Cliente> clientes = new ArrayList<>();
		
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		ResultSet res = null;

		final String sql = "SELECT clientes.idCliente, clientes.nomeCompleto, clientes.nome, clientes.rg, clientes.cpf," +
						   "clientes.endereco, clientes.telefone1, clientes.telefone2, regioes.nome from clientes join regioes" +
						   " on clientes.idRegiao = regioes.idRegiao ORDER BY clientes.nome";

		try {
			stmt = con.prepareStatement(sql);
			res = stmt.executeQuery();
			while(res.next()){
				clientes.add(new Cliente(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7), res.getString(8), res.getString(9)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ConnectionFactory.closeConnection(con, stmt, res);
		}
		return clientes;	

	}
}