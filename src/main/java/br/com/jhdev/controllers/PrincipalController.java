package br.com.jhdev.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.jhdev.views.View;
import br.com.jhdev.views.View.ViewName;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

/**
 * PrincipalController
 *
 * @author JHenrique
 */
public class PrincipalController implements Initializable {
	
	@FXML
	private MenuItem menuCadRegiao;
	
	@FXML
	private MenuItem menuClientes;

	@FXML
	private MenuItem menuClose;

	@FXML
	private Button btnClientes;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		menuClientes.setOnAction(event -> new View("Clientes", "ListClientesN"));
		
		menuCadRegiao.setOnAction(e -> new View("Regiões", ViewName.REGIOES));
		
		menuClose.setOnAction(e -> System.exit(0));
		
		btnClientes.setOnAction(event -> new View("Clientes", ViewName.DADOS_CLIENTES));

	}

	
}