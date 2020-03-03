package dad.javafx.bomberdad.menu.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.almasb.fxgl.dsl.FXGL;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * Controlador para el titulo del menu
 * @author Alejandro Arrocha Hdez, Rosmen Ramos Díaz, Cristian Abad de Vera, Pablo García Gómez
 *
 */

public class TitleController extends StackPane implements Initializable {

	// model
	private SimpleStringProperty textLess = new SimpleStringProperty();

	// view

	@FXML
	private Label lbLess;

	@FXML
	private ImageView image;

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
		lbLess.textProperty().bind(textLess);
		animation(5);
	}

	// getters & setters

	public void animation(int time) {
		FXGL.getEngineTimer().runOnceAfter(() -> {
			image.setImage(new Image("./assets/textures/Title.gif"));
			FXGL.getEngineTimer().runOnceAfter(() -> {
				textLess.set(null);
				image.setImage(new Image("./imgs/transparentBum.gif"));
				lbLess.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
				lbLess.getStyleClass().add("lbless");
				FXGL.getEngineTimer().runOnceAfter(() -> {
					image.setImage(new Image("./assets/textures/TitleStatic.png"));
				}, Duration.seconds(1));
			}, Duration.seconds(1));
		}, Duration.seconds(time));
	}

	public void setW(double width) {
		this.setPrefWidth(width);
	}

	public void setH(double height) {
		this.setPrefHeight(height);
	}

	public final SimpleStringProperty textLessProperty() {
		return this.textLess;
	}

	public final String getTextLess() {
		return this.textLessProperty().get();
	}

	public final void setTextLess(final String textLess) {
		this.textLessProperty().set(textLess);
	}

}
