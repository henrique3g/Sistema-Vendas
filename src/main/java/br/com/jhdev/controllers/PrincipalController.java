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
	@FXML
	private MenuItem menuCategoria;
	@FXML
	private MenuItem menuProdutos;
	@FXML
	private MenuItem menuCaixa;
	@FXML
	private MenuItem menuVenda;
	/* @FXML
	private Button btnClientes;
	@FXML
	private Button btnRegioes; */
	@FXML
	private JFXButton btnClientes;
	@FXML
	private JFXButton btnRegioes;
	@FXML
	private JFXButton btCat;
	@FXML
	private JFXButton btProdutos;
	@FXML
	private JFXButton btCaixa;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		menuClientes.setOnAction(event -> new View("Clientes", ViewName.CLIENTES));
		
		menuCadRegiao.setOnAction(e -> new View("Regiões", ViewName.REGIOES));
		
		menuSobre.setOnAction(e -> new View("Sobre", ViewName.SOBRE));

		menuCategoria.setOnAction(e -> new View("Categorias", ViewName.CATEGORIAS));

		menuProdutos.setOnAction(e -> new View("Produtos", ViewName.PRODUTOS));

		menuCaixa.setOnAction(e -> new View("Caixa", ViewName.CAIXA));

		menuVenda.setOnAction(e -> new View("Venda", ViewName.VENDAS));

		menuClose.setOnAction(e -> System.exit(0));
		
		btnClientes.setOnAction(event -> new View("Clientes", ViewName.CLIENTES));
		btnClientes.setOnKeyReleased(e -> {
			if(e.getCode() == KeyCode.ENTER){
				new View("Clientes", ViewName.CLIENTES);

			}	
		});
		btnRegioes.setOnAction(e -> new View("Regiões", ViewName.REGIOES));
		btCat.setOnAction(e -> new View("Categorias", ViewName.CATEGORIAS));
		btProdutos.setOnAction(e -> new View("Produtos", ViewName.PRODUTOS));
		btCaixa.setOnAction(e -> new View("Caixa", ViewName.CAIXA));
	}

	
}