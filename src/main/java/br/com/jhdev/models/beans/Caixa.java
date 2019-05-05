package br.com.jhdev.models.beans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Caixa
 */
public class Caixa {

	private LocalDate dataCaixa;
	private String descricao;
	private double valorCaixa;
	private List<Movimento> movimentos;

	public Caixa() {
	}

	public Caixa(LocalDate dataCaixa, String descricao, double valorCaixa) {
		this.dataCaixa = dataCaixa;
		this.descricao = descricao;
		this.valorCaixa = valorCaixa;
		this.movimentos = new ArrayList<>();
	}


	public Caixa(LocalDate dataCaixa, String descricao, double valorCaixa, List<Movimento> movimentos) {
		this(dataCaixa, descricao, valorCaixa);
		this.movimentos = movimentos;
	}
	

	public LocalDate getDataCaixa() {
		return this.dataCaixa;
	}

	public void setDataCaixa(LocalDate dataCaixa) {
		this.dataCaixa = dataCaixa;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValorCaixa() {
		return this.valorCaixa;
	}

	public void setValorCaixa(double valorCaixa) {
		this.valorCaixa = valorCaixa;
	}

	public List<Movimento> getMovimentos() {
		return this.movimentos;
	}

	public void setMovimentos(List<Movimento> movimentos) {
		this.movimentos = movimentos;
	}

}