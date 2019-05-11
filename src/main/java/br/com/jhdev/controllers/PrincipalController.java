package br.com.jhdev.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import br.com.jhdev.views.View;
import br.com.jhdev.views.View.ViewName;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;

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
	private MenuItem menuSobre;
	/* @FXML
	private Button btnClientes;
	@FXML
	private Button btnRegioes; */
	@FXML
	private JFXButton btnClientes;
	@FXML
	private JFXButton btnRegioes;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		menuClientes.setOnAction(event -> new View("Clientes", ViewName.CLIENTES));
		
		menuCadRegiao.setOnAction(e -> new View("Regiões", ViewName.REGIOES));
		
		menuSobre.setOnAction(e -> new View("Sobre", ViewName.SOBRE));

		menuClose.setOnAction(e -> System.exit(0));
		
		btnClientes.setOnAction(event -> new View("Clientes", ViewName.CLIENTES));
		btnClientes.setOnKeyReleased(e -> {
			if(e.getCode() == KeyCode.ENTER){
				new View("Clientes", ViewName.CLIENTES);

			}	
		});
		btnRegioes.setOnAction(e -> new View("Regiões", ViewName.REGIOES));
	}

	
}