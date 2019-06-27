package br.com.jhdev.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.jhdev.models.beans.Movimento;
import br.com.jhdev.models.dao.MovimentoDao;
import br.com.jhdev.util.InfoSystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * LancamentoController
 */
public class LancamentoController implements Initializable {

	@FXML
    private DatePicker dpData;
    @FXML
    private ComboBox<String> cbTipoMovimento;
    @FXML
    private TextField tfValor;
    @FXML
    private TextArea tfDescricao;
    @FXML
	private Button btLancar;
	
	private ObservableList<String> obsTipoMovimento;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initComboBox();
		dpData.setValue(InfoSystem.getDataCaixaAtual());
		btLancar.setOnAction(e -> lancar());
	}

	private void initComboBox(){
		obsTipoMovimento = FXCollections.observableArrayList("Entrada","Saída");
		cbTipoMovimento.setItems(obsTipoMovimento);
	}

	private void lancar() {
		Movimento mov = getInputs();
		if(mov != null){
			String result = new MovimentoDao().create(mov);
			if(result.equals("ok")){
				Alert a = new Alert(AlertType.INFORMATION, "Lançamento efetuado com sucesso!");
				a.setHeaderText(null);
				a.showAndWait();
				clearInputs();
			} else {
				alertValidate(result);
			}

		}
	}

	private Movimento getInputs(){
		if(!checkData()){
			return null;
		}

		Movimento mov = new Movimento();
		mov.setDataCaixa(dpData.getValue());
		mov.setDescricao(tfDescricao.getText());
		char tipo;
		if(cbTipoMovimento.getSelectionModel().getSelectedItem().equals("Entrada")){
			tipo = 'E';
		} else {
			tipo = 'S';
		}
		mov.setTipo(tipo);
		mov.setValor(Double.parseDouble(tfValor.getText()));

		return mov;
	}

	private void clearInputs(){
		cbTipoMovimento.getSelectionModel().clearSelection();
		tfValor.clear();
		tfDescricao.clear();
		cbTipoMovimento.requestFocus();
	}
	private boolean checkData(){
		if(cbTipoMovimento.getSelectionModel().getSelectedItem() == null){
			alertValidate("Escola o tipo do lançamento!");
			cbTipoMovimento.requestFocus();
			return false;
		}
		if(tfValor.getText().isEmpty()){
			alertValidate("Digite o valor do lançamento!");
			tfValor.requestFocus();
			return false;
		}
		if(Double.parseDouble(tfValor.getText()) <= 0){
			alertValidate("o valor não pode ser negativo!");
			tfValor.requestFocus();
			return false;
		}
		if(tfDescricao.getText().isEmpty()){
			alertValidate("Digite uma descrição!");
			tfDescricao.requestFocus();
			return false;
		}
		return true;
	}

	private void alertValidate(String value) {
		Alert alert = new Alert(AlertType.WARNING, value);
		alert.setHeaderText(null);
		alert.showAndWait();
	}
	
}