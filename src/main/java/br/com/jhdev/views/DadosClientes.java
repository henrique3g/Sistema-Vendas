package br.com.jhdev.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * PrincipalView
 */
public class DadosClientes extends Application {
	private static Stage stage;
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/DadosClientes.fxml"));
		System.out.println("Executou DadosClientes.start()");
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Clientes");
        primaryStage.initModality(Modality.APPLICATION_MODAL); 
		primaryStage.setResizable(false);
		primaryStage.showAndWait();
	}

	public static Stage getStage() {
		return stage;
	}
	
}