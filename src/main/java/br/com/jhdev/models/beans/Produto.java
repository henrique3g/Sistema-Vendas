package br.com.jhdev.models.beans;

/**
 * Produto
 */
public class Produto {

	private int id;
	private String nome;
	private String descricao;
	private	String codigoBarras;
	private String unidade;
	private int estoqueMinimo;
	private double precoVenda;
	private double precoCompra;
	private int quantidade;
	private double valorTotal;
	private	Categoria categoria;

	public Produto() {
	}

	public Produto(int id, String nome, String unidade, double precoVenda){
		this.id = id;
		this.nome = nome;
		this.unidade = unidade;
		this.precoVenda = precoVenda;
	}

	public Produto(int id, String nome, String unidade, double precoVenda, int quantidade, double valorTotal) {
		this(id, nome, unidade, precoVenda);
		this.quantidade = quantidade;
		this.valorTotal = valorTotal;
	}
	
	public Produto(int id, String nome, String descricao, String codigoBarras, String unidade, int estoqueMinimo, double precoVenda, Categoria categoria){
		this(id, nome, unidade, precoVenda);
		this.descricao = descricao;
		this.codigoBarras = codigoBarras;
		this.estoqueMinimo = estoqueMinimo;
		this.categoria = categoria;
	}

	public Produto(int id, String nome, String descricao, String codigoBarras, String unidade, int estoqueMinimo, double precoVenda, double precoCompra, int quantidade, double valorTotal, Categoria categoria) {
		this(id, nome, descricao, codigoBarras, unidade, estoqueMinimo, precoVenda, categoria);
		this.precoCompra = precoCompra;
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

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCodigoBarras() {
		return this.codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getUnidade() {
		return this.unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public int getEstoqueMinimo() {
		return this.estoqueMinimo;
	}

	public void setEstoqueMinimo(int estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}

	public double getPrecoVenda() {
		return this.precoVenda;
	}

	public void setPrecoVenda(double precoVenda) {
		this.precoVenda = precoVenda;
	}

	public double getPrecoCompra() {
		return this.precoCompra;
	}

	public void setPrecoCompra(double precoCompra) {
		this.precoCompra = precoCompra;
	}

	public int getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getValorTotal() {
		return this.valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}