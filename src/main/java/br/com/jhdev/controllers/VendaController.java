package br.com.jhdev.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * VendaController
 */
public class VendaController implements Initializable {

	@FXML
	private TextField tfCodCliente;

	@FXML
	private Button btBuscar;

	@FXML
	private TextField tfNomeCliente;

	@FXML
	private TextField tfCodProduto;

	@FXML
	private TextField tfQuantidade;

	@FXML
	private TextField tfNomeProduto;

	@FXML
	private TextField tfValorUnitario;

	@FXML
	private Button btLancar;

	@FXML
	private Button btEdit;

	@FXML
	private Button btRemove;

	@FXML
	private TableView<?> tblProdutos;

	@FXML
	private TableColumn<?, ?> colCod;

	@FXML
	private TableColumn<?, ?> colQuantidade;

	@FXML
	private TableColumn<?, ?> colUnidade;

	@FXML
	private TableColumn<?, ?> colNome;

	@FXML
	private TableColumn<?, ?> colValorUnitario;

	@FXML
	private TableColumn<?, ?> colValorTotal;

	@FXML
	private TextField tfTotalVenda;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}