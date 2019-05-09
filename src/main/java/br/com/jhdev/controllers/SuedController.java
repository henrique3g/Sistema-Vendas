package br.com.jhdev.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * SuedController
 */
public class SuedController implements Initializable {

	@FXML
	private Button btnPerg;
	@FXML
	private Label lblResp;
	@FXML
	private TextField txtPerg;

	private String pergunta = "Ola exelentissimo sued você poderia me dizer";
	private String resposta = "";
	private boolean ocultar = false;
	private boolean flag = false;
	private int count = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtPerg.requestFocus();

		txtPerg.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.SEMICOLON) {
				ocultar = !ocultar;
				flag = true;
			}

			if (e.getCode() == KeyCode.ENTER) {
				perguntar();
			}
			if (e.getCode() == KeyCode.BACK_SPACE && ocultar) {
				System.out.println("backspace");
				flag = true;
				count--;
				if (count < 1) {
					txtPerg.setText("");
					pergunta = "";
					count = 0;
					System.out.println("back count = 0");
				} else {
					txtPerg.setText(pergunta.substring(0, count+1));
					resposta = resposta.substring(0, count);
					txtPerg.positionCaret(count+1);
					System.out.println("back count > 1");
					
				}
			}
		});
		
		txtPerg.setOnKeyTyped(e -> {
			if (ocultar && !flag) {
				System.out.println("KeyTyped");
				count++;
				txtPerg.setText(pergunta.substring(0, count));
				
				txtPerg.positionCaret(count);
				
				resposta = resposta.concat(e.getCharacter());
				e.consume();
			}
			
			if (flag) {
				System.out.println("flag = true");
				e.consume();
				flag = false;
			}

		});

		btnPerg.setOnAction(e -> {
			perguntar();
		});

	}

	private void perguntar() {
		if (count >= 1) {
			lblResp.setText(resposta);
		} else {
			lblResp.setText("Não posso lhe responder");
		}

	}

}