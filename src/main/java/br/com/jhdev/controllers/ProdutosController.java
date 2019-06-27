package br.com.jhdev.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.jhdev.models.beans.Categoria;
import br.com.jhdev.models.beans.Produto;
import br.com.jhdev.models.dao.CategoriaDao;
import br.com.jhdev.models.dao.ProdutoDao;
import br.com.jhdev.util.Formatter;
import br.com.jhdev.views.View;
import br.com.jhdev.views.View.ViewName;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * ProdutosController
 */
public class ProdutosController implements Initializable {

	@FXML
	private VBox root;
	@FXML
    private Button btNew;
    @FXML
    private Button btEdit;
    @FXML
    private Button btDelete;
    @FXML
    private Button btCancel;
    @FXML
	private Button btSave;
    @FXML
    private Button btFind;
    @FXML
    private TextField tfId;
    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfCodBarra;
    @FXML
    private TextField tfEstoqueMin;
    @FXML
    private TextField tfPreco;
    @FXML
    private TextField tfUnidade;
    @FXML
    private TextArea tfDescricao;
    @FXML
	private ComboBox<Categoria> cbCategoria;
	
	private ObservableList<Categoria> obsCat;
	private boolean edit;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initComboBox();
		setDisableButtons(true);
		setDisableInputs(true);
		
		tfDescricao.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.TAB){
				tfCodBarra.requestFocus();
			}
		});

		btFind.setOnAction(e -> findProduto());
		
		btCancel.setOnAction(e -> ((Stage)btCancel.getScene().getWindow()).close());

		btNew.setOnAction(e -> newProduto());
		btNew.setOnKeyReleased(e -> {
			if(e.getCode() == KeyCode.ENTER){
				newProduto();
			}
		});

		btSave.setOnAction(e -> saveProduto());
		btSave.setOnKeyReleased(e -> {
			if(e.getCode() == KeyCode.ENTER){
				saveProduto();
			}
		});

		btEdit.setOnAction(e -> editProduto());
		btEdit.setOnKeyReleased(e -> {
			if(e.getCode() == KeyCode.ENTER){
				editProduto();
			}
		});

		btDelete.setOnAction(e -> deleteProduto());
		btDelete.setOnKeyReleased(e -> {
			if(e.getCode() == KeyCode.ENTER){
				deleteProduto();
			}
		});

		root.setOnKeyReleased(e -> {
			if(e.getCode() == KeyCode.ESCAPE){
				closeStage();
			}
			if(e.getCode() == KeyCode.PAGE_DOWN){
				findProduto();
			}
		});
	}

	private void initComboBox(){
		obsCat = FXCollections.observableArrayList(new CategoriaDao().readAll());

		cbCategoria.setItems(obsCat);
	}

	private void closeStage(){
		((Stage)root.getScene().getWindow()).close();
	}

	private void findProduto(){
		new View("Produtos", ViewName.LIST_PRODUTOS);
		if(ListProdutoController.prod != null){
			setInputs(ListProdutoController.prod);
			setDisableButtons(false);
			setDisableInputs(true);
			btEdit.requestFocus();
		}
	}

	private void newProduto(){
		edit = false;
		setDisableButtons(true);
		setDisableInputs(false);
		clearInputs();
		tfNome.requestFocus();
	}

	private void editProduto(){
		edit = true;
		setDisableInputs(false);
		tfNome.requestFocus();

	}

	private void deleteProduto(){
		edit = true;
		Produto p = getInputs();

		Alert alert = new Alert(AlertType.CONFIRMATION, "Deseja excluir o produto: " + p.getNome(), ButtonType.YES, ButtonType.NO);
		alert.setHeaderText(null);
		alert.showAndWait().ifPresent(response -> {
			if(response == ButtonType.YES){
				String result = new ProdutoDao().delete(p.getId());
				if(result.equals("ok")){
					Alert a = new Alert(AlertType.INFORMATION, "Produto excluido com sucesso!");
					a.setHeaderText(null);
					a.showAndWait();
					clearInputs();
					setDisableInputs(true);
					setDisableButtons(true);
				} else if(result.contains("fk_produto")){
					alertValidate("Este produto não pode ser excluido!");
				} else {
					alertValidate("Erro ao excluir: " + result);
				}
			}
		});
	}

	private void saveProduto(){
		Produto p = getInputs();
		if(p != null){
			String result;
			if(edit){
				result = new ProdutoDao().update(p);
			} else {
				result = new ProdutoDao().create(p);
			}
			if(checkReturnSQL(result)){
				Alert a = new Alert(AlertType.INFORMATION);
				a.setHeaderText(null);
				if(edit){
					a.setContentText("Produto alterado com sucesso!");
				} else {
					a.setContentText("Produto Cadastrado com sucesso!");
				}
				a.showAndWait();
				clearInputs();
				setDisableInputs(true);
			} 
		}
	}
	
	private Produto getInputs(){
		Produto produto = new Produto();
		if(tfNome.getText().isEmpty()){
			alertValidate("Nome é obrigatório!");
			tfNome.requestFocus();
			return null;
		}
		if(tfDescricao.getText().isEmpty()){
			alertValidate("Descrição é obrigatória!");
			tfDescricao.requestFocus();
			return null;
		}
		if(tfEstoqueMin.getText().isEmpty()){
			alertValidate("Estoque mínimo é obrigratório!");
			tfEstoqueMin.requestFocus();
			return null;
		}
		if(tfPreco.getText().isEmpty()){
			alertValidate("Preço é obrigatório!");
			tfPreco.requestFocus();
			return null;
		}
		if(tfUnidade.getText().isEmpty()){
			alertValidate("Unidade é obrigatória!");
			tfUnidade.requestFocus();
			return null;
		}
		if(cbCategoria.getSelectionModel().getSelectedItem() == null){
			alertValidate("Categoria é obrigatória!");
			cbCategoria.requestFocus();
			return null;
		}
		
		
		if(edit){
			produto.setId(Integer.parseInt(tfId.getText()));
		}
		produto.setNome(tfNome.getText());
		produto.setDescricao(tfDescricao.getText());
		produto.setCodigoBarras(tfCodBarra.getText());
		// if(Integer.valueOf(s) tfEstoqueMin.getText())
		if(tfPreco.getText().matches("[^0-9]")){
			alertValidate("Valor de estoque inválido!");
			tfEstoqueMin.requestFocus();
			return null;
		}
		produto.setEstoqueMinimo(Integer.parseInt(tfEstoqueMin.getText()));
		
		produto.setUnidade(tfUnidade.getText());
		if(tfPreco.getText().matches("[^0-9,]")) {
			alertValidate("Preço de venda Inválido!");
			tfPreco.requestFocus();
			return null;
		}
		produto.setPrecoVenda(Double.parseDouble(tfPreco.getText().replace(',', '.')));
		produto.setCategoria(cbCategoria.getSelectionModel().getSelectedItem());

		return produto;
	}

	private void setInputs(Produto produto){
		tfId.setText(Integer.toString(produto.getId()));
		tfNome.setText(produto.getNome());
		tfDescricao.setText(produto.getDescricao());
		tfCodBarra.setText(produto.getCodigoBarras());
		tfUnidade.setText(produto.getUnidade());
		tfEstoqueMin.setText(Integer.toString(produto.getEstoqueMinimo()));
		tfPreco.setText(Formatter.formatMoney(produto.getPrecoVenda()));
		cbCategoria.getSelectionModel().select(produto.getCategoria());
	}

	private void clearInputs(){
		tfId.setText("");
		tfNome.setText("");
		tfDescricao.setText("");
		tfCodBarra.setText("");
		tfUnidade.setText("");
		tfEstoqueMin.setText("");
		tfPreco.setText("");
		cbCategoria.getSelectionModel().select(null);
	}

	private void setDisableButtons(boolean value){
		btDelete.setDisable(value);
		btEdit.setDisable(value);
	}

	private void setDisableInputs(boolean value){
		btSave.setDisable(value);
		// tfId.setDisable(value);
		tfNome.setDisable(value);
		tfDescricao.setDisable(value);
		tfCodBarra.setDisable(value);
		tfPreco.setDisable(value);
		tfEstoqueMin.setDisable(value);
		tfUnidade.setDisable(value);

		cbCategoria.setDisable(value);
	}

	private boolean checkReturnSQL(String result){
		if(result.equals("ok")){
			return true;
		}else {
			alertValidate(result);
			return false;
		}
		
	}
	private void alertValidate(String value) {
		Alert alert = new Alert(AlertType.WARNING, value);
		alert.setHeaderText(null);
		alert.showAndWait();
	}

}