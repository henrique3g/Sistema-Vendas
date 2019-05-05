package br.com.jhdev.models.beans;

/**
 * Fornecedor
 */
public class Fornecedor {

	private int id;
	private String razaoSocial;
	private String fantasia;
	private String cnpj;
	private String endereco;
	private String email;
	private String telefone1;
	private String telefone2;

	public Fornecedor() {
	}

	public Fornecedor(int id, String razaoSocial, String fantasia, String cnpj, String endereco, String email, String telefone1, String telefone2) {
		this.id = id;
		this.razaoSocial = razaoSocial;
		this.fantasia = fantasia;
		this.cnpj = cnpj;
		this.endereco = endereco;
		this.email = email;
		this.telefone1 = telefone1;
		this.telefone2 = telefone2;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return this.razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getFantasia() {
		return this.fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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


}