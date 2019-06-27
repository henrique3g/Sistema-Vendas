package br.com.jhdev.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import br.com.jhdev.models.beans.Caixa;
import br.com.jhdev.models.beans.Movimento;
import br.com.jhdev.models.dao.CaixaDao;
import br.com.jhdev.util.InfoSystem;
import br.com.jhdev.views.View;
import br.com.jhdev.views.View.ViewName;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * CaixaController
 */
public class CaixaController implements Initializable {

    @FXML
    private VBox root;
    @FXML
    private Button btLancamento;
    @FXML
    private Button btCancel;
    @FXML
    private Button btChangeCaixa;
    @FXML
    private DatePicker dpData;
    @FXML
    private TextField tfEntrada;
    @FXML
    private TextField tfSangrias;
    @FXML
    private TextField tfSuprimentos;
    @FXML
    private TextField tfSaldo;
    @FXML
    private Label lbStateCaixa;
    @FXML
    private TableView<Movimento> tblLancamentos;
    @FXML
    private TableColumn<Movimento, String> colTipo;
    @FXML
    private TableColumn<Movimento, String> colDesc;
    @FXML
    private TableColumn<Movimento, String> colValor;

	private ObservableList<Movimento> obsLancamentos;
    private Caixa caixa;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
        if(InfoSystem.getDataCaixaAtual() != null) {
            dpData.setValue(InfoSystem.getDataCaixaAtual());
            System.out.println(InfoSystem.getDataCaixaAtual());
        } else {
            dpData.setValue(LocalDate.now());
        }

        caixa = new CaixaDao().readFull(dpData.getValue());
        if(caixa != null) {
            setInputs(caixa);
        } else {
            clearInputs();
        }

		dpData.valueProperty().addListener((object, oldValue, newValue) -> {
            caixa = new CaixaDao().readFull(newValue);
            if(caixa != null){
                tfEntrada.setText(String.format("%,.2f", caixa.getEntrada()));
                setInputs(caixa);
            } else {
                clearInputs();
            }
        });
        
        btLancamento.setOnAction(e -> lancamentos());

        btChangeCaixa.setOnAction(e -> {
            if(caixa == null){
                abrirCaixa();
            } else if(caixa.getState()){
                fecharCaixa();
            } 
        });

        btCancel.setOnAction(e -> {
            closeStage();
        });
	}

	private void updateTable(){
        if(caixa != null) {
            obsLancamentos = FXCollections.observableArrayList(caixa.getMovimentos());
            colTipo.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getTipe()));
            colDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));
            colValor.setCellValueFactory(value -> new SimpleStringProperty(String.format("%,.2f", value.getValue().getValor())));
            tblLancamentos.setItems(obsLancamentos);
        }
    }
    
    private void changeState() {
        tfEntrada.setDisable(true);
        btLancamento.setDisable(true);
        btChangeCaixa.setDisable(false);
        if(caixa == null){
            btChangeCaixa.setText("Abrir caixa");
            lbStateCaixa.setText("");
            tfEntrada.setDisable(false);
            if(InfoSystem.getDataCaixaAtual() != null && !dpData.getValue().equals(InfoSystem.getDataCaixaAtual())) {
                btChangeCaixa.setDisable(true);
                tfEntrada.setDisable(true);

                System.out.println("ola");
            } 

        } else if(caixa.getState()){
            btChangeCaixa.setText("Fechar caixa");
            lbStateCaixa.setText("Caixa Aberto");
            btLancamento.setDisable(false);
        } else {
            btChangeCaixa.setDisable(true);
            btChangeCaixa.setText("Abrir caixa");
            lbStateCaixa.setText("Caixa Fechado");
        }
    }

    private void lancamentos() {
        new View("Lançamentos", ViewName.LANCAMENTO);
        updateTable();
    }

    private void abrirCaixa() {
        if(tfEntrada.getText().isEmpty()){
            alertValidate("Digite a entrada do caixa!");
            tfEntrada.requestFocus();
            return;
        }
        if(Double.parseDouble(tfEntrada.getText().replace(',', '.')) < 0){
            alertValidate("A entrada não pode ser menor que 0!");
            tfEntrada.requestFocus();
            return;
        }
        Caixa cx = new Caixa(dpData.getValue(), Double.parseDouble(tfEntrada.getText().replace(',', '.')));
        String result = new CaixaDao().abrirCaixa(cx);
        if(result.equals("ok")) {
            Alert a = new Alert(AlertType.INFORMATION, "Caixa aberto");
            a.setHeaderText(null);
            a.showAndWait();
            caixa = new CaixaDao().read(dpData.getValue());
            InfoSystem.updateDataCaixaAtual();
            changeState();
        } else {
            alertValidate(result);
        }
    }
    
    private void fecharCaixa(){
       /*  Alert a = new Alert(AlertType.CONFIRMATION, "Deseja realmente fechar o caixa atual?", ButtonType.YES, ButtonType.NO);
        a.setHeaderText(null);
        a.showAndWait().ifPresent(response -> {
            if(response.equals(ButtonType.YES)) {
 */
                TextInputDialog input = new TextInputDialog();
                input.setContentText("Mensagem do caixa");
                input.getEditor().setPromptText("Digite uma mensagem para o caixa");
                input.getEditor().setPrefWidth(300);
                input.getEditor().setPadding(new Insets(10));

                input.setHeaderText(null);
                input.showAndWait().ifPresent(msg -> {
                    if(msg.isEmpty()){
                        alertValidate("Mensagem não pode estar vazia");
                        return;
                    }
                    caixa.setDescricao(msg);
                    
                    String result = new CaixaDao().fecharCaixa(caixa);
                    if(result.equals("ok")) {
                        caixa.setState(false);
                        Alert alert = new Alert(AlertType.INFORMATION, "Caixa fechado!");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                        InfoSystem.updateDataCaixaAtual();
                        changeState();
                    } else {
                        alertValidate(result);
                    }
                });

                
            // }
        // });
    }
    
    private void setInputs(Caixa caixa){
        tfEntrada.setText(String.format("%,.2f", caixa.getEntrada()));
        tfSuprimentos.setText(String.format("%,.2f", caixa.getSuprimentos()));
        tfSangrias.setText(String.format("%,.2f", caixa.getSangrias()));
        tfSaldo.setText(String.format("%,.2f", caixa.getSaldo()));
        updateTable();
        changeState();

    }

    private void clearInputs() {
        tfEntrada.clear();
        tfSuprimentos.clear();
        tfSangrias.clear();
        tfSaldo.clear();
        
        if(obsLancamentos != null) {
            obsLancamentos.clear();
        }

        changeState();
    }
    private void closeStage() {
		((Stage)root.getScene().getWindow()).close();
    }

    private void alertValidate(String value) {
		Alert alert = new Alert(AlertType.WARNING, value);
		alert.setHeaderText(null);
		alert.showAndWait();
	}
}