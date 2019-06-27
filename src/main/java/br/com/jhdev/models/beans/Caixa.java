package br.com.jhdev.models.beans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.jhdev.models.dao.MovimentoDao;
/**
 * Caixa
 */
public class Caixa {

	private LocalDate dataCaixa;
	private String descricao;
	private double entrada;
	private boolean state;
	private double suprimentos;
	private double sangrias;
	private double saldo;

	private List<Movimento> movimentos;

	public Caixa() {
	}

	public Caixa(LocalDate dataCaixa, double entrada) {
		this.dataCaixa = dataCaixa;
		this.entrada = entrada;
		this.state = true;
		this.movimentos = new ArrayList<>();
	}
	public Caixa(LocalDate dataCaixa, double entrada, boolean state) {
		this.dataCaixa = dataCaixa;
		this.entrada = entrada;
		this.state = state;
		this.movimentos = new ArrayList<>();
	}

	public Caixa(LocalDate dataCaixa, String descricao, double entrada) {
		this.dataCaixa = dataCaixa;
		this.descricao = descricao;
		this.entrada = entrada;
		this.movimentos = new ArrayList<>();
	}

	public Caixa(LocalDate dataCaixa, String descricao, double entrada, List<Movimento> movimentos) {
		this(dataCaixa, descricao, entrada);
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

	public double getEntrada() {
		return this.entrada;
	}

	public void setEntrada(double entrada) {
		this.entrada = entrada;
	}

	public boolean isState() {
		return this.state;
	}

	public boolean getState() {
		return this.state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public double getSuprimentos() {
		return this.suprimentos;
	}

	private void setSuprimentos(double suprimentos) {
		this.suprimentos = suprimentos;
	}

	public double getSangrias() {
		return this.sangrias;
	}

	private void setSangrias(double sangrias) {
		this.sangrias = sangrias;
	}

	public double getSaldo() {
		this.saldo = getSuprimentos() - getSangrias() + getEntrada();
		return this.saldo;
	}

	private void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public List<Movimento> getMovimentos() {
		setMovimentos(new MovimentoDao().readAllByDate(this.getDataCaixa()));
		return this.movimentos;
	}

	public void setMovimentos(List<Movimento> movimentos) {
		this.movimentos = movimentos;
		this.suprimentos = 0;
		this.sangrias = 0;

		this.movimentos.forEach(e -> {
			if(e.getTipo() == 'E'){
				this.suprimentos += e.getValor();
			} else {
				this.sangrias += e.getValor();
			}
		});
	}

}