package br.com.jhdev.models.beans;

import java.time.LocalDate;
import java.sql.Date;

/**
 * Movimento
 */
public class Movimento {

	private int id;
	private LocalDate dataCaixa;
	private String descricao;
	private char tipo;
	private double valor;
	public static final char ENTRADA = 'E';
	public static final char SAIDA = 'S';

	public Movimento() {
	}

	public Movimento(String descricao, char tipo, double valor) {
		this.descricao = descricao;
		this.tipo = tipo;
		this.valor = valor;
	}

	public Movimento(int id, String descricao, char tipo, double valor) {
		this(descricao, tipo, valor);
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDataCaixa() {
		return this.dataCaixa;
	}

	
	public void setDataCaixa(LocalDate dataCaixa) {
		this.dataCaixa = dataCaixa;
	}
	
	public Date getDateCaixa() {
		return Date.valueOf(getDataCaixa());
	}

	public void setDateCaixa(Date dataCaixa) {
		setDataCaixa(dataCaixa.toLocalDate());
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public char getTipo() {
		return this.tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}

	public String getTipe() {
		if(this.tipo == 'E'){
			return "Entrada";
		}
		return "Saida";
	}

	public double getValor() {
		return this.valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}