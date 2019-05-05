package br.com.jhdev.models.beans;

public class Cliente {
	private int id;
	private String nome;
	private String apelido;
	private String rg;
	private String cpf;
	private String endereco;
	private String telefone1;
	private String telefone2;
	private Regiao regiao;


	public Cliente() {
	}

	public Cliente(int id, String nome, String apelido, String rg, String cpf, String endereco, String telefone1, String telefone2, Regiao regiao) {
		this.id = id;
		this.nome = nome;
		this.apelido = apelido;
		this.rg = rg;
		this.cpf = cpf;
		this.endereco = endereco;
		this.telefone1 = telefone1;
		this.telefone2 = telefone2;
		this.regiao = regiao;
	}

	public Cliente(int id, String nome, String apelido, String rg, String cpf, String endereco, String telefone1, String telefone2, String nomeRegiao) {
		this.id = id;
		this.nome = nome;
		this.apelido = apelido;
		this.rg = rg;
		this.cpf = cpf;
		this.endereco = endereco;
		this.telefone1 = telefone1;
		this.telefone2 = telefone2;
		this.regiao = new Regiao(nomeRegiao);
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

	public String getApelido() {
		return this.apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getRg() {
		return this.rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone1() {
		return this.telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return this.telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public Regiao getRegiao() {
		return this.regiao;
	}

	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}
	
}