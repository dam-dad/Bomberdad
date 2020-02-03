package dad.javafx.bomberdad.menu.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class TitleController extends StackPane implements Initializable{

	@FXML
    private Label lbTitle;
	
	public TitleController() {
		super();
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TitleView.fxml"));
		loader.setController(this);
		loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
