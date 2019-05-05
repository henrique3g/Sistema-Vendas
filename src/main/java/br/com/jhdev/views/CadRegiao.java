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
public class CadRegiao extends Application {
	private static Stage stage;
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/RegioesN.fxml"));
		System.out.println("Executou CadRegiao.start()");
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Regiões");
        primaryStage.initModality(Modality.APPLICATION_MODAL); 
		primaryStage.setResizable(false);
		primaryStage.showAndWait();
	}

	public static Stage getStage() {
		return stage;
	}
	
}