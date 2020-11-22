package dad.javafx.enviaremail;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
	
	private static Stage primaryStage;
	
	private EnviarEmailController controller;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		App.primaryStage = primaryStage;
		controller = new EnviarEmailController();
		
		Scene scene = new Scene(controller.getView());
		primaryStage.setTitle("Enviar email");
		primaryStage.getIcons().add(new Image("/icons/email-send-icon-32x32.png"));
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
