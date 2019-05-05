package br.com.jhdev.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.jhdev.models.beans.Regiao;
import br.com.jhdev.models.dao.DaoRegiao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


/**
 * FXML Controller class
 *
 * @author JHenrique
 */
public class DadosClientesController implements Initializable {
    
    @FXML
    private TextField txtId;

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextField txtTelefone1;

    @FXML
    private ComboBox<Regiao> cbRegiao;

    @FXML
    private TextField txtApelido;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTelefone2;

    @FXML
    private TextField txtCpf;

    @FXML
    private TextField txtRg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateComboBox();
        txtNome.requestFocus();
    }

    public void updateComboBox(){
        ObservableList obs = FXCollections.observableArrayList(new DaoRegiao().readAll());

        cbRegiao.setItems(obs);
    }

}