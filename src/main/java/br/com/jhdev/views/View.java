package br.com.jhdev.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * PrincipalView
 */
public class View extends Application {
	private String title;
	private String view;

	public enum ViewName {
		REGIOES("Regioes"), LIST_CLIENTES("ListClientesN"), DADOS_CLIENTES("DadosClientesN");
		
		public String valor;

		ViewName(String valor){
			this.valor = valor;
		}
	}
	
	public View(String title, String view) {
		this.title = title;
		this.view = view;
		try {
			this.start(new Stage());
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao Abrir a view : " + this.view);
			alert.showAndWait();
			System.err.println(e.getMessage());
		}
	}
	
	public View(String title, ViewName view){
		this(title, view.valor);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/" + view + ".fxml"));
		// System.out.println("Executou " + view + ".start()");
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle(title);
        primaryStage.initModality(Modality.APPLICATION_MODAL); 
		primaryStage.setResizable(false);
		primaryStage.showAndWait();
	}

	
}