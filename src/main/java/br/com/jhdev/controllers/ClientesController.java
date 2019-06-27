package br.com.jhdev.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.jhdev.models.beans.Cliente;
import br.com.jhdev.models.beans.Regiao;
import br.com.jhdev.models.dao.ClienteDao;
import br.com.jhdev.models.dao.RegiaoDao;
import br.com.jhdev.util.Formatter;
import br.com.jhdev.util.TextFieldFormatter;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author JHenrique
 */
public class ClientesController implements Initializable {

	@FXML
	private VBox root;
	@FXML
	private ComboBox<Regiao> cbRegiao;
	@FXML
	private VBox vb;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnSave;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnFind;
	@FXML
	private Button btnNew;
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtApelido;
	@FXML
	private TextField txtEndereco;
	@FXML
	private TextField txtCpf;
	@FXML
	private TextField txtRg;
	@FXML
	private TextField txtTelefone1;
	@FXML
	private TextField txtTelefone2;

	private boolean edit;
	private final String UNIQUE_RG = "unique_rg";
	private final String UNIQUE_CPF = "unique_cpf";

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		root.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.ESCAPE) {
				((Stage) root.getScene().getWindow()).close();
			}
			if (e.getCode() == KeyCode.PAGE_DOWN) {
				find();
			}
		});

		TextFieldFormatter.mascaraTelefone(txtTelefone1);
		TextFieldFormatter.mascaraTelefone(txtTelefone2);

		TextFieldFormatter.mascaraCPF(txtCpf);

		setDisableInputs(true);
		setDisableButtons(true);
		// btnSave.setDisable(true);

		btnNew.setOnAction(e -> newCliente());
		btnNew.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				newCliente();
			}
		});

		btnFind.setOnAction(e -> {
			find();
		});

		btnCancel.setOnAction(e -> {
			((Stage) btnCancel.getScene().getWindow()).close();
		});

		btnSave.setOnAction(e -> save());
		btnSave.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				save();
			}
		});

		btnEdit.setOnAction(e -> {
			edit = true;
			setDisableInputs(false);
			txtNome.requestFocus();
		});

		btnEdit.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				edit = true;
				setDisableInputs(false);
				txtNome.requestFocus();
			}
		});

		btnDelete.setOnAction(e -> delete());
		btnDelete.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				delete();
			}
		});
		initComboBox();
		txtNome.requestFocus();
	}

	private void initComboBox() {
		ObservableList<Regiao> obs = FXCollections.observableArrayList(new RegiaoDao().readAll());

		cbRegiao.setItems(obs);
	}

	private void setDisableInputs(boolean value) {
		// txtId.setDisable(value);
		txtNome.setDisable(value);
		txtApelido.setDisable(value);
		txtEndereco.setDisable(value);
		txtCpf.setDisable(value);
		txtRg.setDisable(value);
		txtTelefone1.setDisable(value);
		txtTelefone2.setDisable(value);
		cbRegiao.setDisable(value);
		btnSave.setDisable(value);
	}

	private void setDisableButtons(boolean value) {
		btnEdit.setDisable(value);
		btnDelete.setDisable(value);
		// btnSave.setDisable(value);
	}

	private void newCliente() {
		setDisableInputs(false);
		clearInputs();
		txtNome.requestFocus();
		edit = false;
	}

	private void delete() {
		Cliente cliente = getInputs();
		cliente.setId(Integer.parseInt(txtId.getText()));
		Alert alert = new Alert(AlertType.CONFIRMATION, "Deseja realmente excluir o cliente: " + cliente.getApelido(),
				ButtonType.YES, ButtonType.NO);
		alert.setHeaderText(null);
		alert.showAndWait().ifPresent(response -> {
			if (response == ButtonType.YES) {
				String result = new ClienteDao().delete(cliente.getId());
				System.out.println(result);
				if (result.equals("ok")) {
					Alert a = new Alert(AlertType.INFORMATION, "Cliente removido com sucesso!", ButtonType.OK);
					a.setHeaderText(null);
					a.showAndWait();
					clearInputs();
					setDisableButtons(true);
					setDisableInputs(true);
				} else {
					Alert a = new Alert(AlertType.ERROR, "Erro ao excluir: " + result, ButtonType.OK);
					a.setHeaderText(null);
					a.showAndWait();
				}
			}
		});
	}

	private void save() {
		Cliente cliente = getInputs();

		if (checkData(cliente)) {
			Alert alert = new Alert(AlertType.INFORMATION, "");
			alert.setHeaderText(null);
			String codeSql;
			if (edit) {
				codeSql = new ClienteDao().update(cliente);
			} else {
				codeSql = new ClienteDao().create(cliente);
			}
			if (checkReturnSQL(codeSql)) {
				if (edit) {
					alert.setContentText("Cliente alterado com sucesso");
				} else {
					alert.setContentText("Cliente Cadastrado com sucesso");

				}
				alert.showAndWait();
				clearInputs();
				setDisableInputs(true);
				setDisableButtons(true);
			}
		}
	}

	private void find() {
		new View("Clientes", ViewName.LIST_CLIENTES);
		if (ListClientesController.cliente != null) {
			setInputs(ListClientesController.cliente);
			setDisableButtons(false);
			btnEdit.requestFocus();
			setDisableInputs(true);
		}
	}

	private void clearInputs() {
		txtId.setText("");
		txtNome.setText("");
		txtApelido.setText("");
		txtCpf.setText("");
		txtRg.setText("");
		txtEndereco.setText("");
		txtTelefone1.setText("");
		txtTelefone2.setText("");
		cbRegiao.getSelectionModel().select(null);
	}

	private Cliente getInputs() {
		Cliente cliente = new Cliente();
		if (edit) {
			cliente.setId(Integer.parseInt(txtId.getText()));
		}
		cliente.setNome(txtNome.getText());
		cliente.setApelido(txtApelido.getText());
		cliente.setRg(txtRg.getText());
		cliente.setCpf(txtCpf.getText().replaceAll("[\\.-]", ""));
		cliente.setEndereco(txtEndereco.getText());
		cliente.setTelefone1(txtTelefone1.getText().replaceAll("[\\s-()]", ""));
		cliente.setTelefone2(txtTelefone2.getText().replaceAll("[\\s-()]", ""));
		cliente.setRegiao(cbRegiao.getSelectionModel().getSelectedItem());
		return cliente;
	}

	private void setInputs(Cliente cliente) {
		txtId.setText(Integer.toString(cliente.getId()));

		txtNome.setText(cliente.getNome());
		txtApelido.setText(cliente.getApelido());
		txtCpf.setText(Formatter.formatCpf(cliente.getCpf()));
		txtRg.setText(cliente.getRg());
		txtEndereco.setText(cliente.getEndereco());
		txtTelefone1.setText(Formatter.formatTelefone(cliente.getTelefone1()));
		if (!cliente.getTelefone2().isEmpty()) {
			txtTelefone2.setText(Formatter.formatTelefone(cliente.getTelefone2()));
		}
		cbRegiao.getSelectionModel().select(cliente.getRegiao());
	}

	private boolean checkData(Cliente cliente) {

		if (cliente.getNome().isEmpty()) {
			alertValidate("Nome não pode ficar vazio!");
			txtNome.requestFocus();
			return false;
		}
		if (cliente.getApelido().isEmpty()) {
			alertValidate("Apelido não pode estar vazio!");
			txtApelido.requestFocus();
			return false;
		}
		if (cliente.getCpf().length() < 11) {
			alertValidate("CPF invalido!");
			txtCpf.requestFocus();
			return false;
		}
		if (cliente.getRg().isEmpty()) {
			alertValidate("RG não pode estar vazio!");
			txtRg.redo();
			return false;
		}
		if (cliente.getEndereco().isEmpty()) {
			alertValidate("Endereço não pode estar vazio!");
			txtEndereco.requestFocus();
			return false;
		}
		if (cliente.getTelefone1().length() < 11) {
			alertValidate("Telefone invalido!");
			txtTelefone1.requestFocus();
			return false;
		}
		if (!cliente.getTelefone2().isEmpty() && cliente.getTelefone2().length() < 11) {
			alertValidate("Telefone opcional invalido!");
			txtTelefone2.requestFocus();
			return false;
		}
		if (cliente.getRegiao() == null) {
			alertValidate("Escolha uma região!");
			cbRegiao.requestFocus();
			return false;
		}
		return true;
	}

	private void alertValidate(String value) {
		Alert alert = new Alert(AlertType.WARNING, value);
		alert.setHeaderText(null);
		alert.showAndWait();
	}

	private boolean checkReturnSQL(String code) {
		System.out.println("codeSQL: " + code);
		if (code.contains("ok")) {
			return true;
		}
		if (code.contains(UNIQUE_CPF)) {
			alertValidate("Esse CPF ja foi cadastrado!");
			txtCpf.requestFocus();
			return false;
		}
		if (code.contains(UNIQUE_RG)) {
			alertValidate("Esse RG ja foi cadastrado!");
			txtRg.requestFocus();
			return false;
		} else {
			alertValidate("value");
		}
		
		return false;
	}
}