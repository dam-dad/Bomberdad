package dad.javafx.bomberdad.menu.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Controlador para el contenido del boton "Controles"
 * @author Alejandro Arrocha Hdez, Rosmen Ramos Díaz, Cristian Abad de Vera, Pablo García Gómez
 *
 */

public class ControlsController extends VBox implements Initializable{
	
	
	@FXML
    private Label lbTitle;
	
	public ControlsController() {
		super();
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ControlsView.fxml"));
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


	public void setW(double width) {
		this.setPrefWidth(width);
	}
	

}
