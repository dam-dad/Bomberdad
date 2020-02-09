package dad.javafx.bomberdad.menu.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class GifsController extends StackPane implements Initializable {

	@FXML
	private StackPane stackPane;

	@FXML
	private ImageView img1, img2, img3, img4;

	public GifsController() {
		super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GifsView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	// getters & setters

	public void setW(double width) {
		this.setPrefWidth(width);
	}

	public void setH(double height) {
		this.setPrefHeight(height);
	}

}
