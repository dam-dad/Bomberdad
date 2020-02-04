package dad.javafx.bomberdad.menu.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class ControlsController extends StackPane implements Initializable{

	//model
	
	private SimpleStringProperty text = new SimpleStringProperty(); 
	
	//view
	
	@FXML
    private Label lbTitle;
	
	public ControlsController() {
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
		lbTitle.textProperty().bind(text);
	}

	public final SimpleStringProperty textProperty() {
		return this.text;
	}
	
	//getters & setters
	
	public final String getText() {
		return this.textProperty().get();
	}
	

	public final void setText(final String text) {
		this.textProperty().set(text);
	}

	public void setW(double width) {
		this.setPrefWidth(width);
	}
	
//	public void setBG(Color color) {
//		this.b
//	}

}
