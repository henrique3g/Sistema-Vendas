package br.com.jhdev.models.beans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Venda
 */
public class Venda {

	private int id;
	private Cliente	cliente;
	private LocalDate dataCaixa;
	private double desconto;
	private char tipoDesconto;
	private char tipoVenda;
	private double entrada;
	private List<Produto> produtos;

	public Venda() {
	}

	public Venda(int id, Cliente cliente, LocalDate dataCaixa, double desconto, char tipoDesconto, char tipoVenda, double entrada) {
		this.id = id;
		this.cliente = cliente;
		this.dataCaixa = dataCaixa;
		this.desconto = desconto;
		this.tipoDesconto = tipoDesconto;
		this.tipoVenda = tipoVenda;
		this.entrada = entrada;
		this.produtos = new ArrayList<Produto>();
	}

	public Venda(int id, Cliente cliente, LocalDate dataCaixa, double desconto, char tipoDesconto, char tipoVenda, double entrada, List<Produto> produtos) {
		this.id = id;
		this.cliente = cliente;
		this.dataCaixa = dataCaixa;
		this.desconto = desconto;
		this.tipoDesconto = tipoDesconto;
		this.tipoVenda = tipoVenda;
		this.entrada = entrada;
		this.produtos = produtos;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public LocalDate getDataCaixa() {
		return this.dataCaixa;
	}

	public void setDataCaixa(LocalDate dataCaixa) {
		this.dataCaixa = dataCaixa;
	}

	public double getDesconto() {
		return this.desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public char getTipoDesconto() {
		return this.tipoDesconto;
	}

	public void setTipoDesconto(char tipoDesconto) {
		this.tipoDesconto = tipoDesconto;
	}

	public char getTipoVenda() {
		return this.tipoVenda;
	}

	public void setTipoVenda(char tipoVenda) {
		this.tipoVenda = tipoVenda;
	}

	public double getEntrada() {
		return this.entrada;
	}

	public void setEntrada(double entrada) {
		this.entrada = entrada;
	}

	public List<Produto> getProdutos() {
		return this.produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

}