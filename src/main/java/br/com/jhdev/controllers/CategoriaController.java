package br.com.jhdev.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.jhdev.models.beans.Categoria;
import br.com.jhdev.models.dao.CategoriaDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author JHenrique
 */
public class CategoriaController implements Initializable {

    @FXML
    private VBox root;
    @FXML
    private TableView<Categoria> tblCategorias;
    @FXML
    private TableColumn<Categoria, Integer> colCod;
    @FXML
    private TableColumn<Categoria, String> colNome;
    @FXML
    private TextField tfCod;
    @FXML
    private TextField tfNome;
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

    private ObservableList<Categoria> obsCategoria;
    private boolean edit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        setDisableInputs(true);
        setDisableButtons(true);
        
        tfNome.focusedProperty().addListener((value, oldValue, newValue) -> {
            if(!newValue){
            }
        });

        btNew.setOnAction(e -> newCategoria());
        btCancel.setOnAction(e -> ((Stage)btCancel.getScene().getWindow()).close());
        tblCategorias.setOnMouseClicked(e -> {
            setInputs(tblCategorias.getSelectionModel().getSelectedItem());
            setDisableButtons(false);
            setDisableInputs(true);
        });

        btEdit.setOnAction(e -> editCategoria());
        btSave.setOnAction(e -> saveCategoria());
        btDelete.setOnAction(e -> deleteCategoria());
        
    }

    public void initTable(){
        obsCategoria = FXCollections.observableArrayList(new CategoriaDao().readAll());
        
        colCod.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        tblCategorias.setItems(obsCategoria);
    }
    
    public void setDisableButtons(boolean value){
        btDelete.setDisable(value);
        btEdit.setDisable(value);
    }
    
    private void setDisableInputs(boolean value){
        tfNome.setDisable(value);
        btSave.setDisable(value);
    }
    
    private void newCategoria(){
        edit = false;
        clearInputs();
        setDisableInputs(false);
        setDisableButtons(true);
        tfNome.requestFocus();
    }

    private void editCategoria(){
        edit = true;
        setDisableInputs(false);
        tfNome.requestFocus();
    }

    private void deleteCategoria(){
        edit = true;
        Alert alert = new Alert(AlertType.CONFIRMATION, "Deseja realmente excluir essa categoria: " + getInputs().getNome(), ButtonType.NO, ButtonType.YES);
        alert.setHeaderText(null);
        alert.showAndWait().ifPresent(response -> {
            if(response.equals(ButtonType.YES)){
                String result = new CategoriaDao().delete(getInputs().getId());
                if(result.equals("ok")){
                    Alert al = new Alert(AlertType.INFORMATION, "Categoria excluida com sucesso!");
                    al.setHeaderText(null);
                    al.showAndWait();
                    clearInputs();
                    initTable();
                } else if(result.contains("fk_categoria")) {
                    alertValidate("Esta categoria não pode ser excluida pois pertence á algum produto!");

                }else {
                    alertValidate(result);
                }
            }
        });
    }

    private void saveCategoria(){
        if(tfNome.getText().isEmpty()){
            alertValidate("Nome da categoria não pode esta vazia!");
            tfNome.requestFocus();
            return;
        }
        String result;
        if(edit){
            result = new CategoriaDao().update(getInputs());
        } else {
            result = new CategoriaDao().create(getInputs());
        }
        if(result.contains("unique")) {
            alertValidate("Categoria ja existe!");
            tfNome.requestFocus();
            return;
        }
        if(result.equals("ok")){
            clearInputs();
            Alert alert = new Alert(AlertType.INFORMATION, "");
            alert.setHeaderText(null);
            if(edit){
                alert.setContentText("Categoria editada com sucesso!");
            } else {
                alert.setContentText("Categoria criada com sucesso!");
            }
            alert.showAndWait();
            setDisableButtons(true);
            setDisableInputs(true);
            initTable();
        } else {
            alertValidate(result);
        }
    }
    private void setInputs(Categoria cat){
        tfCod.setText(Integer.toString(cat.getId()));
        tfNome.setText(cat.getNome());
    }

    private void alertValidate(String value) {
		Alert alert = new Alert(AlertType.WARNING, value);
		alert.setHeaderText(null);
		alert.showAndWait();
    }
    
    private Categoria getInputs(){
        Categoria cat = new Categoria();
        if(edit){
            cat.setId(Integer.parseInt(tfCod.getText()));
        }
        cat.setNome(tfNome.getText());
        return cat;
    }

    private void clearInputs(){
        tfCod.setText("");
        tfNome.setText("");
    }
}
