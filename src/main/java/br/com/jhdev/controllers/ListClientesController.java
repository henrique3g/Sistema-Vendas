package br.com.jhdev.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import br.com.jhdev.models.beans.Cliente;
import br.com.jhdev.models.dao.ClienteDao;
import br.com.jhdev.util.Formatter;
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
 * FXML Controller class
 *
 * @author JHenrique
 */
public class ListClientesController implements Initializable {
    
    @FXML
    private TableView<Cliente> tblClientes;
    @FXML
    private TableColumn<Cliente, Integer> colId;
    @FXML
    private TableColumn<Cliente, String> colRegiao;
    @FXML
    private TableColumn<Cliente, String> colEndereco;
    @FXML
    private TableColumn<Cliente, String> colCpf;
    @FXML
    private TableColumn<Cliente, String> colTelefone2;
    @FXML
    private TableColumn<Cliente, String> colTelefone1;
    @FXML
    private TableColumn<Cliente, String> colNome;
    @FXML
    private TableColumn<Cliente, String> colRg;
    @FXML
    private TableColumn<Cliente, String> colApelido;
    @FXML
    private ToggleGroup tipoBusca;
    @FXML
    private TextField txtBusca;
    @FXML
    private VBox root;

    private ObservableList<Cliente> obs = null;
    public static Cliente cliente;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cliente = null;
        tblClientes.setOnMouseClicked(e -> {
            if(e.getButton().equals(MouseButton.PRIMARY)){
                if(e.getClickCount() > 1){
                    cliente = tblClientes.getSelectionModel().getSelectedItem();
                    
                    // System.out.println(tblClientes.getSelectionModel().getSelectedItem().getApelido());
                    ((Stage)tblClientes.getScene().getWindow()).close();
                }
            }
        });
        tblClientes.setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.ENTER){
                cliente = tblClientes.getSelectionModel().getSelectedItem();
                ((Stage)tblClientes.getScene().getWindow()).close();
            }
        });

        txtBusca.setOnKeyReleased(e -> {
            if(e.getCode().isLetterKey() || e.getCode() == KeyCode.BACK_SPACE){
                updateTable(txtBusca.getText());
            }
            if(e.getCode() == KeyCode.DOWN){
                tblClientes.requestFocus();
                tblClientes.getSelectionModel().select(0);
            }
            
        });
        
        root.setOnKeyPressed(e -> {
            if(e.getCode().isLetterKey() || e.getCode() == KeyCode.BACK_SPACE){
                txtBusca.requestFocus();
                txtBusca.positionCaret(txtBusca.getText().length());

            }
        });
        root.setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.ESCAPE){
                ((Stage)root.getScene().getWindow()).close();
            }
        });

        initTable();
    }

    public void updateTable(String value){
        FilteredList<Cliente> clientes = new FilteredList<>(obs);
        Predicate<Cliente> filter = null;
        RadioButton radio = (RadioButton)tipoBusca.getSelectedToggle();
        if(radio.getText().equals("Nome")){
            filter = i -> i.getNome().toUpperCase().contains(value.toUpperCase());

        }else{
            filter = i -> i.getApelido().toUpperCase().contains(value.toUpperCase());

        }
        clientes.setPredicate(filter);
        tblClientes.setItems(clientes);

    }

    private void initTable() {
        obs = FXCollections.observableArrayList(new ClienteDao().readAll());

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colApelido.setCellValueFactory(new PropertyValueFactory<>("apelido"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));

        colCpf.setCellValueFactory((param) -> new SimpleStringProperty(Formatter.formatCpf(param.getValue().getCpf())));
            
        colRg.setCellValueFactory((param) -> new SimpleStringProperty(Formatter.formatRg(param.getValue().getRg())));
            
        colTelefone1.setCellValueFactory((param) -> new SimpleStringProperty(Formatter.formatTelefone(param.getValue().getTelefone1())));
        colTelefone2.setCellValueFactory((param) -> new SimpleStringProperty(Formatter.formatTelefone(param.getValue().getTelefone2())));
            
        colRegiao.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getRegiao().getNome()));
        
        tblClientes.setItems(obs);

    }
}