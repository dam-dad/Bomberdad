package dad.javafx.bomberdad.online;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

/**
 * Componente para pedir datos al usuario
 * @author Alejandro Arrocha Hdez, Rosmen Ramos Díaz, Cristian Abad de Vera, Pablo García Gómez
 *
 */

public class PedirDatosComponent extends VBox implements Initializable {

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
		// TODO Auto-generated method stub

	}

}
