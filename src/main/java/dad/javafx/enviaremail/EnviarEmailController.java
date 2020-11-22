package dad.javafx.enviaremail;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class EnviarEmailController implements Initializable {

	@FXML
	private GridPane view;

	@FXML
	private TextField ipServidorText;

	@FXML
	private TextField puertoText;

	@FXML
	private TextField emailRemitenteText;

	@FXML
	private TextField asuntoText;

	@FXML
	private CheckBox conexionSSLCheck;

	@FXML
	private PasswordField passwordText;

	@FXML
	private TextField emailDestinatarioText;

	@FXML
	private TextArea mensajeTextArea;

	@FXML
	private Button enviarButton;

	@FXML
	private Button vaciarButton;

	@FXML
	private Button cerrarButton;

	private EnviarEmailModel model;

	public EnviarEmailController() throws IOException {
		model = new EnviarEmailModel();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EnviarEmailView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Bindings
		ipServidorText.textProperty().bindBidirectional(model.ipServidorProperty());
		puertoText.textProperty().bindBidirectional(model.puertoProperty(), new NumberStringConverter());
		conexionSSLCheck.selectedProperty().bindBidirectional(model.conexionSSLProperty());
		emailRemitenteText.textProperty().bindBidirectional(model.remitenteProperty());
		passwordText.textProperty().bindBidirectional(model.passwordProperty());
		emailDestinatarioText.textProperty().bindBidirectional(model.destinatarioProperty());
		asuntoText.textProperty().bindBidirectional(model.asuntoProperty());
		mensajeTextArea.textProperty().bindBidirectional(model.mensajeProperty());

	}

	@FXML
	void onCerrarButtonAction(ActionEvent event) {
		App.getPrimaryStage().close();
	}

	@FXML
	void onEnviarButtonAction(ActionEvent event) {
		try {			
			Email email = new SimpleEmail();
			email.setHostName(model.getIpServidor());
			email.setSmtpPort(model.getPuerto());
			email.setAuthenticator(new DefaultAuthenticator(model.getRemitente(), model.getPassword()));
			email.setSSLOnConnect(model.isConexionSSL());
			email.setFrom(model.getRemitente());
			email.setSubject(model.getAsunto());
			email.setMsg(model.getMensaje());
			email.addTo(model.getDestinatario());
			email.send();
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Mensaje enviado");
			alert.setHeaderText("Mensaje enviado con éxito a '"+model.getDestinatario()+"'.");
			// PONER ICONO DEL PADRE
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().setAll(App.getPrimaryStage().getIcons());
			
			alert.showAndWait();
			
		} catch (EmailException e) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No se pudo enviar el email.");
			alert.setContentText("Invalid message supplied");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().setAll(App.getPrimaryStage().getIcons());
			alert.showAndWait();
		} catch (RuntimeException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("El puerto no puede estar vacio o el numero es incorrecto");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().setAll(App.getPrimaryStage().getIcons());
			alert.showAndWait();
		}
	}

	@FXML
	void onVaciarButtonAction(ActionEvent event) {
		model.setIpServidor("");
		model.setPuerto(0);
		model.setConexionSSL(false);
		model.setRemitente("");
		model.setPassword("");
		model.setDestinatario("");
		model.setAsunto("");
		model.setMensaje("");
	}

	public GridPane getView() {
		return view;
	}
}
