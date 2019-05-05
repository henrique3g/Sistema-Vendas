package br.com.jhdev.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.jhdev.models.beans.Cliente;
import br.com.jhdev.models.beans.Regiao;
import br.com.jhdev.models.dao.ClienteDao;
import br.com.jhdev.views.View;
import br.com.jhdev.views.View.ViewName;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * FXML Controller class
 *
 * @author JHenrique
 */
public class ListClientesController implements Initializable {
    
    @FXML
    private TableColumn<Cliente, String> colRegiao;

    @FXML
    private Button btnEdit;

    @FXML
    private TableColumn<Cliente, String> colEndereco;

    @FXML
    private TableColumn<Cliente, String> colCpf;

    @FXML
    private TableColumn<Cliente, String> colTelefone2;

    @FXML
    private TableColumn<Cliente, String> colTelefone1;

    @FXML
    private TableView<Cliente> tblClientes;

    @FXML
    private ToggleGroup tipoBusca;

    @FXML
    private TableColumn<Cliente, String> colNome;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField txtBusca;

    @FXML
    private TableColumn<Cliente, String> colRg;

    @FXML
    private Button btnNew;

    @FXML
    private Button btnDelete;

    @FXML
    private TableColumn<Cliente, String> colApelido;

    @FXML
    private TableColumn<Cliente, Integer> colId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnNew.setOnAction(e -> {
            new View("Clientes", ViewName.DADOS_CLIENTES);
        });

        tblClientes.setOnMouseClicked(e -> {
            btnEdit.setDisable(false);
            btnDelete.setDisable(false);
            if(e.getButton().equals(e.getButton().PRIMARY)){

                if(e.getClickCount() > 1){
                    System.out.println(tblClientes.getSelectionModel().getSelectedItem().getApelido());
                }
            }
        });
        updateTable();
    }

    private void updateTable(){
        ObservableList<Cliente> obs = FXCollections.observableArrayList(new ClienteDao().readAll());

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colApelido.setCellValueFactory(new PropertyValueFactory<>("apelido"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colRg.setCellValueFactory(new PropertyValueFactory<>("rg"));
        colTelefone1.setCellValueFactory(new PropertyValueFactory<>("telefone1"));
        colTelefone2.setCellValueFactory(new PropertyValueFactory<>("telefone2"));
        colRegiao.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getRegiao().getNome()));
        
        tblClientes.setItems(obs);

        // obs.forEach(e -> System.out.println(e.getRegiao().getNome()));
    }
}