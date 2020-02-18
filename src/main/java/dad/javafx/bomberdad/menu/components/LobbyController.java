package dad.javafx.bomberdad.menu.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import dad.javafx.bomberdad.online.ClienteTCP;
import dad.javafx.bomberdad.online.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class LobbyController extends GridPane implements Initializable {

	//model
	
	private Server server;
	private ClienteTCP cliente;
	
	// view

	@FXML
	private TextArea inChat;

	@FXML
	private TextField outChat;

	@FXML
	private Label ipPlayer1, ipPlayer2, ipPlayer3, ipPlayer4;

	@FXML
	private ImageView imgPlayer1, imgPlayer2, imgPlayer3, imgPlayer4;

	@FXML
	private JFXCheckBox checkPlayer1, checkPlayer2, checkPlayer3, checkPlayer4;

	@FXML
	private JFXButton startBtn;

	public LobbyController(Server server) {
		super();
		try {
			this.server = server;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LobbyView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cliente = new ClienteTCP();
		if (cliente.recibir.id == 1) {
			ipPlayer1.setText("IP: " + cliente.addr.getAddress());
			imgPlayer1.setImage(new Image("./assets/textures/pycrab.gif"));
		} else if (cliente.recibir.id == 2) {
			ipPlayer1.setText("IP: " + server.clientes.get(1).client.getPort());
			imgPlayer1.setImage(new Image("./assets/textures/pycrab.gif"));
		} else if (cliente.recibir.id == 3) {
			ipPlayer1.setText("IP: " + cliente.addr.getAddress());
			imgPlayer1.setImage(new Image("./assets/textures/pycrab.gif"));
		} else if (cliente.recibir.id == 4) {
			ipPlayer1.setText("IP: " + cliente.addr.getAddress());
			imgPlayer1.setImage(new Image("./assets/textures/pycrab.gif"));
		}
		
		
		startBtn.disableProperty().bind(checkPlayer1.selectedProperty().not());
		startBtn.disableProperty().bind(checkPlayer2.selectedProperty().not());
		startBtn.disableProperty().bind(checkPlayer3.selectedProperty().not());
		startBtn.disableProperty().bind(checkPlayer4.selectedProperty().not());
	}
	
	@FXML
    void onStartGameAction(ActionEvent event) {

    }

}
