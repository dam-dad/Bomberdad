package dad.javafx.bomberdad.menu.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Controlador para el fondo de pantalla del menu personalizado
 * @author Alejandro Arrocha Hdez, Rosmen Ramos Díaz, Cristian Abad de Vera, Pablo García Gómez
 *
 */

public class BackgroundController extends StackPane implements Initializable{

	//model
	
	private SimpleObjectProperty<Image> image = new SimpleObjectProperty<Image>();
	
	//view
	
	@FXML
    private StackPane view;

    @FXML
    private ImageView imageView;
	
	public BackgroundController() {
		super();
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BackgroundView.fxml"));
		loader.setController(this);
		loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		imageView.imageProperty().bind(image);
	}
	
	public void setS(double w,double h) {
		setPrefSize(w,h);
		imageView.setFitHeight(h);
		imageView.setFitWidth(w);
	}

	public final SimpleObjectProperty<Image> imageProperty() {
		return this.image;
	}
	

	public final Image getImage() {
		return this.imageProperty().get();
	}
	

	public final void setImage(final Image image) {
		this.imageProperty().set(image);
	}
	
}
