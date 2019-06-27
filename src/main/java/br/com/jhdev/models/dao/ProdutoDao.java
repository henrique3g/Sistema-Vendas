package br.com.jhdev.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.jhdev.models.beans.Categoria;
import br.com.jhdev.models.beans.Produto;

/**
 * ProdutoDao
 */
public class ProdutoDao {

	private Connection con;
	private PreparedStatement stmt;
	private ResultSet res;
	private String sql;

	public String create(Produto produto){
		con = ConnectionFactory.getConnection();
		sql = "INSERT INTO produtos(nome, descricao, codBarras, unidade, estoqueMin, precoVenda, idCategoria) values(?, ?, ?, ?, ?, ?, ?)";

		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, produto.getNome());
			stmt.setString(2, produto.getDescricao());
			stmt.setString(3, produto.getCodigoBarras());
			stmt.setString(4, produto.getUnidade());
			stmt.setInt(5, produto.getEstoqueMinimo());
			stmt.setDouble(6, produto.getPrecoVenda());
			stmt.setInt(7, produto.getCategoria().getId());

			stmt.executeUpdate();
			return "ok";
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		} finally {
			ConnectionFactory.closeConnection(con, stmt);
		}
	}

	public List<Produto> readAll(){
		List<Produto> produtos = new ArrayList<>();
		con = ConnectionFactory.getConnection();
		sql = "SELECT produtos.idProduto, produtos.nome, produtos.descricao, produtos.codBarras, produtos.unidade, produtos.estoqueMin, " +
			  "produtos.precoVenda, categorias.idCategoria, categorias.nome FROM produtos JOIN categorias " +
			  "ON categorias.idCategoria = produtos.idCategoria ORDER BY produtos.nome";

		try {
			stmt = con.prepareStatement(sql);

			res = stmt.executeQuery();

			while(res.next()){
				produtos.add(new Produto(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getInt(6), res.getDouble(7), new Categoria(res.getInt(8), res.getString(9))));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.closeConnection(con, stmt, res);
		}
		return produtos;
	}

	public Produto read(int idProduto){
		con = ConnectionFactory.getConnection();
		sql = "SELECT produtos.idProduto, produtos.nome, produtos.descricao, produtos.codBarras, produtos.unidade, produtos.estoqueMin, " +
			  "produtos.precoVenda, categorias.idCategoria, categorias.nome FROM produtos JOIN categorias " +
			  "ON categorias.idCategoria = produtos.idCategoria WHERE idProduto = ?";

		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, idProduto);
			res = stmt.executeQuery();

			res.next();

			return new Produto(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getInt(6), res.getDouble(7), new Categoria(res.getInt(8), res.getString(9)));
			
		} catch (SQLException e) {

			System.out.println(e.getMessage());
			return null;
		} finally {
			ConnectionFactory.closeConnection(con, stmt, res);
		}
	}

	public String update(Produto produto){
		con = ConnectionFactory.getConnection();
		sql = "UPDATE produtos SET nome = ?, descricao = ?, codBarras = ?, unidade = ?, estoqueMin = ?, "+
			  "precoVenda = ?, idCategoria = ? WHERE idProduto = ? LIMIT 1";

		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, produto.getNome());
			stmt.setString(2, produto.getDescricao());
			stmt.setString(3, produto.getCodigoBarras());
			stmt.setString(4, produto.getUnidade());
			stmt.setInt(5, produto.getEstoqueMinimo());
			stmt.setDouble(6, produto.getPrecoVenda());
			stmt.setInt(7, produto.getCategoria().getId());
			stmt.setInt(8, produto.getId());

			stmt.executeUpdate();
			return "ok";
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		} finally {
			ConnectionFactory.closeConnection(con, stmt);
		}
	}

	public String delete(int idProduto){
		con = ConnectionFactory.getConnection();
		sql = "DELETE FROM produtos WHERE idProduto = ? LIMIT 1";
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, idProduto);
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