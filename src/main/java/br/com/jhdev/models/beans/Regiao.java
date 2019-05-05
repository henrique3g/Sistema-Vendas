package br.com.jhdev.models.beans;

public class Regiao {
	private int id;
	private String nome;
	private String povoados;

	public Regiao() {
	}

	public Regiao(int id) {
		this.id = id;
	}
	
	public Regiao(String nome){
		this.nome = nome;
	}
	public Regiao(int id, String nome) {
		this(id);
		this.nome = nome;
	}

	public Regiao(int id, String nome, String povoados) {
		this(id, nome);
		this.povoados = povoados;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPovoados() {
		return this.povoados;
	}

	public void setPovoados(String povoados) {
		this.povoados = povoados;
	}

	@Override
	public String toString() {
		return this.getNome();
	}

}