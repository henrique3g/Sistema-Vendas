package br.com.jhdev.models.beans;

/**
 * Movimento
 */
public class Movimento {

	private int id;
	private String descricao;
	private char tipo;
	private double valor;
	public static final char ENTRADA = 'E';
	public static final char SAIDA = 'S';

	public Movimento() {
	}

	public Movimento(int id, String descricao, char tipo, double valor) {
		this.id = id;
		this.descricao = descricao;
		this.tipo = tipo;
		this.valor = valor;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public double getValor() {
		return this.valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}