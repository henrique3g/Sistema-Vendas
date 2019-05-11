package br.com.jhdev.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import br.com.jhdev.models.beans.Regiao;
import br.com.jhdev.models.dao.RegiaoDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author JHenrique
 */
public class RegioesController implements Initializable {
    
    @FXML
    private TableView<Regiao> tblRegioes;

    @FXML
    private TableColumn<Regiao, Integer> colId;

    @FXML
    private TableColumn<Regiao, String> colNome;

    @FXML
    private TableColumn<Regiao, String> colPovoados;

    @FXML
    private Button btnEdit;
    
    @FXML
    private TextArea txtPovoados;

    @FXML
    private TextField txtNome;
    
    @FXML
    private TextField txtId;

    @FXML
    private TextField txtFind;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnNew;

    @FXML
    private Button btnDelete;

    @FXML
    private TabPane tabPane;

    @FXML
    private ToggleGroup tipoPesquisa;

    private int stateSave;

    private ObservableList<Regiao> obsReg = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        btnSave.setOnAction(e -> {
            Regiao regiao = getInputs();
            if(checkData(regiao)){

                if(stateSave == 1) {
                    System.out.println(new RegiaoDao().create(regiao));
                }else if(stateSave == 2) {
                    System.out.println(new RegiaoDao().update(regiao));
                }
                clearInputs();
                
                txtNome.requestFocus();
                initTable();
                updateTable(txtFind.getText());
            }
            
        });
        
        btnNew.setOnAction(e -> {
            tabPane.getTabs().get(1).setDisable(false);
            tabPane.getSelectionModel().select(1);
            setDisableEdidAndDelete(true);
            txtNome.requestFocus();
            stateSave = 1;
        });

        btnEdit.setOnAction(e -> {
            Regiao rg = tblRegioes.getSelectionModel().getSelectedItem();
            stateSave = 2;
            setInputs(rg);
            tabPane.getTabs().get(1).setDisable(false);
            tabPane.getSelectionModel().select(1);
            txtNome.requestFocus();
        });

        btnDelete.setOnAction(e -> {
            Regiao rg = tblRegioes.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deseja realmente excluir a região: " +rg.getNome(), ButtonType.YES, ButtonType.NO);
            alert.setHeaderText(null);
            alert.showAndWait().ifPresent(b -> {
                if(b == ButtonType.YES){
                    new RegiaoDao().delete(rg.getId());
                    updateTable(txtFind.getText());
                }
            });
        });
        
        tblRegioes.setOnMouseClicked(e -> {
            setDisableEdidAndDelete(false);
        });
        
               
        btnCancel.setOnAction(e -> {
            ((Stage)btnCancel.getScene().getWindow()).close();
        });
        
        txtFind.setOnKeyReleased(e -> {
            if(e.getCode().isLetterKey() || e.getCode() == KeyCode.BACK_SPACE){
                updateTable(txtFind.getText());
            }
        });

        initTable();
    }

    private boolean checkData(Regiao regiao){
        if(regiao.getNome().isEmpty()){
            alertValidate("Digite o nome da região!");
            txtNome.requestFocus();
            return false;
        }
        if(regiao.getPovoados().isEmpty()){
            alertValidate("Digite os povoados da região!");
            txtPovoados.requestFocus();
            return false;
        }
        return true;
    }

    private void alertValidate(String value){
        Alert alert = new Alert(AlertType.WARNING, value);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    
    private void setInputs(Regiao regiao){
        if(stateSave == 2){
            txtId.setText(Integer.toString(regiao.getId()));
        }
        txtNome.setText(regiao.getNome());
        txtPovoados.setText(regiao.getPovoados());
        
    }

    private Regiao getInputs(){
        Regiao regiao = new Regiao();
        if(stateSave == 2){
            regiao.setId(Integer.parseInt(txtId.getText()));
        }    
        regiao.setNome(txtNome.getText());
        regiao.setPovoados(txtPovoados.getText());
        return regiao;
    }

    private void updateTable(String value){
        FilteredList<Regiao> regioes = new FilteredList<>(obsReg);
        Predicate<Regiao> filter = null;
        RadioButton radio = (RadioButton)tipoPesquisa.getSelectedToggle();
        
        
        
        if(radio.getText().equals("Nome")){
            filter = i -> i.getNome().toUpperCase().contains(value.toUpperCase());
        }else{
            filter = i -> i.getPovoados().toUpperCase().contains(value.toUpperCase());
        }


        regioes.setPredicate(filter);
        tblRegioes.setItems(regioes);
    }
    

    private void initTable(){
        obsReg = FXCollections.observableArrayList(new RegiaoDao().readAll());
        
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPovoados.setCellValueFactory(new PropertyValueFactory<>("povoados"));
        tblRegioes.setItems(obsReg);
        
        setDisableEdidAndDelete(true);
    }

    private void clearInputs(){
        txtId.setText("");
        txtNome.setText("");
        txtPovoados.setText("");
    }

    private void setDisableEdidAndDelete(boolean value){
        btnDelete.setDisable(value);
        btnEdit.setDisable(value);
    }
}