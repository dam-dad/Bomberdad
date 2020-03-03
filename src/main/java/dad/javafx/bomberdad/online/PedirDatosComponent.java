package dad.javafx.bomberdad.online;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PedirDatosComponent extends GridPane implements Initializable {

	// model

	private SimpleStringProperty ip = new SimpleStringProperty();
	private SimpleStringProperty port = new SimpleStringProperty();
	private SimpleStringProperty nickname = new SimpleStringProperty();

	// view

	@FXML
	private GridPane view;

	@FXML
	private TextField nicknameTxt, portTxt, ipTxt;

	public PedirDatosComponent() {
		super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PedirDatosView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ip.bind(ipTxt.textProperty());
		port.bind(portTxt.textProperty());
		nickname.bind(nicknameTxt.textProperty());
	}

	public final SimpleStringProperty ipProperty() {
		return this.ip;
	}
	

	public final String getIp() {
		return this.ipProperty().get();
	}
	

	public final void setIp(final String ip) {
		this.ipProperty().set(ip);
	}
	

	public final SimpleStringProperty portProperty() {
		return this.port;
	}
	

	public final String getPort() {
		return this.portProperty().get();
	}
	

	public final void setPort(final String port) {
		this.portProperty().set(port);
	}
	

	public final SimpleStringProperty nicknameProperty() {
		return this.nickname;
	}
	

	public final String getNickname() {
		return this.nicknameProperty().get();
	}
	

	public final void setNickname(final String nickname) {
		this.nicknameProperty().set(nickname);
	}

}
