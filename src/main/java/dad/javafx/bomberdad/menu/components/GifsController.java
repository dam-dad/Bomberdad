package dad.javafx.bomberdad.menu.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

public class GifsController extends StackPane implements Initializable{
	
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
	public void initialize(URL location, ResourceBundle resources) { }

	//getters & setters
	
	public void setW(double width) {
		this.setPrefWidth(width);
	}
	
	public void setH(double height) {
		this.setPrefHeight(height);
	}
	
}
