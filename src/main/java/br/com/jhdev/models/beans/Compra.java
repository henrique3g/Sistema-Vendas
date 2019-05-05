package br.com.jhdev.models.beans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Compra
 */
public class Compra {

	private int id;
	private int numeroNota;
	private LocalDate dataCompra;
	private Fornecedor fornecedor;
	private double desconto;
	private List<Produto> produtos;

	public Compra() {
	}

	public Compra(int id, int numeroNota, LocalDate dataCompra, Fornecedor fornecedor, double desconto) {
		this.id = id;
		this.numeroNota = numeroNota;
		this.dataCompra = dataCompra;
		this.fornecedor = fornecedor;
		this.desconto = desconto;
		this.produtos = new ArrayList<Produto>();
	}

	public Compra(int id, int numeroNota, LocalDate dataCompra, Fornecedor fornecedor, double desconto, List<Produto> produtos) {
		this.id = id;
		this.numeroNota = numeroNota;
		this.dataCompra = dataCompra;
		this.fornecedor = fornecedor;
		this.desconto = desconto;
		this.produtos = produtos;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumeroNota() {
		return this.numeroNota;
	}

	public void setNumeroNota(int numeroNota) {
		this.numeroNota = numeroNota;
	}

	public LocalDate getDataCompra() {
		return this.dataCompra;
	}

	public void setDataCompra(LocalDate dataCompra) {
		this.dataCompra = dataCompra;
	}

	public Fornecedor getFornecedor() {
		return this.fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public double getDesconto() {
		return this.desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public List<Produto> getProdutos() {
		return this.produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

}