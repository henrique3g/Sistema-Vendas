package br.com.jhdev.util;

import java.time.LocalDate;

import br.com.jhdev.models.dao.CaixaDao;

/**
 * InfoSystem
 */
public  class InfoSystem {
	private static LocalDate dataCaixaAtual;
	
	public InfoSystem() {
		dataCaixaAtual = new CaixaDao().getDataCaixaAtual();
	}
	
	public static LocalDate getDataCaixaAtual(){
		return dataCaixaAtual;
	}
	
	public static void setDataCaixaAtual(LocalDate data){
		dataCaixaAtual = data;
	}
	
	public static LocalDate updateDataCaixaAtual() {
		dataCaixaAtual = new CaixaDao().getDataCaixaAtual();
		return dataCaixaAtual;
	}
	
}