package br.com.jhdev.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import br.com.jhdev.models.beans.Produto;
import br.com.jhdev.models.dao.ProdutoDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * ListProdutoController
 */
public class ListProdutoController implements Initializable {

	@FXML
    private VBox root;
    @FXML
    private TextField tfFind;
    @FXML
    private ToggleGroup tgOpcao;
    @FXML
    private TableView<Produto> tblProdutos;
    @FXML
    private TableColumn<Produto, Integer> colId;
    @FXML
    private TableColumn<Produto, String> colNome;
    @FXML
    private TableColumn<Produto, String> colDesc;
    @FXML
    private TableColumn<Produto, String> colPreco;
    @FXML
    private TableColumn<Produto, String> colUnidade;
    @FXML
    private TableColumn<Produto, Integer> colEstoque;
    @FXML
    private TableColumn<Produto, String> colCodBarra;
    @FXML
    private TableColumn<Produto, Integer> colEstoqueMin;
    @FXML
	private TableColumn<Produto, String> colCategoria;

	private ObservableList<Produto> obsProdutos;
	public static Produto prod;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
        initTable();
        prod = null;
        tfFind.textProperty().addListener((value, oldValue, newValue) -> {
            updateTable(newValue);
        });

        root.setOnKeyPressed(e -> {
            if(e.getCode().isLetterKey() || e.getCode() == KeyCode.BACK_SPACE){
                if(e.getCode() == KeyCode.BACK_SPACE){
                    tfFind.setText(tfFind.getText().substring(0, tfFind.getText().length()-1));
                }
                tfFind.requestFocus();
                
                tfFind.positionCaret(tfFind.getText().length());
            }
        });
        root.setOnKeyReleased(e -> {
            
            if(e.getCode() == KeyCode.DOWN){
                tblProdutos.requestFocus();
                tblProdutos.getSelectionModel().select(0);
            }
            if(e.getCode() == KeyCode.ESCAPE){
                prod = null;
                closeStage();
            }
        });
        tblProdutos.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                prod = tblProdutos.getSelectionModel().getSelectedItem();
                closeStage();
            }
        });
        tblProdutos.setOnMouseClicked(e -> {
            if(e.getButton().equals(MouseButton.PRIMARY)){
                if(e.getClickCount() > 1){
                    prod = tblProdutos.getSelectionModel().getSelectedItem();
                    closeStage();
                }

            }
        });
    }

	private void initTable(){
        obsProdutos = FXCollections.observableArrayList(new ProdutoDao().readAll());

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colCodBarra.setCellValueFactory(new PropertyValueFactory<>("codigoBarras"));
        colUnidade.setCellValueFactory(new PropertyValueFactory<>("unidade"));
        colEstoqueMin.setCellValueFactory(new PropertyValueFactory<>("estoqueMinimo"));
        colPreco.setCellValueFactory(value -> new SimpleStringProperty(String.format("%,.2f",value.getValue().getPrecoVenda())));
        colCategoria.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCategoria().getNome()));

        tblProdutos.setItems(obsProdutos);
	}

	private void updateTable(String value){
        FilteredList<Produto> produtos = new FilteredList<>(obsProdutos);
        Predicate<Produto> filter = null;
        RadioButton radio = (RadioButton) tgOpcao.getSelectedToggle();
        if(radio.getText().equals("Nome")){
            filter = i -> i.getNome().toUpperCase().contains(value.toUpperCase());
        } else {
            filter = i -> i.getDescricao().toUpperCase().contains(value.toUpperCase());

        }
        produtos.setPredicate(filter);
        tblProdutos.setItems(produtos);
    }

	private void closeStage(){
        ((Stage)tblProdutos.getScene().getWindow()).close();
    }
}