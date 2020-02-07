package dad.javafx.bomberdad.menu.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.almasb.fxgl.dsl.FXGL;

import javafx.animation.FillTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class TitleController extends StackPane implements Initializable {

	// model

	private SimpleStringProperty text1 = new SimpleStringProperty();
	private SimpleStringProperty text2 = new SimpleStringProperty();
	private SimpleStringProperty textMiddle = new SimpleStringProperty();
	private SimpleStringProperty textLess = new SimpleStringProperty();

	// view

	@FXML
	private Label lbFirst, lbSec, lbMiddle, lbLess;

	@FXML
	private StackPane stackPane;

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
		lbFirst.textProperty().bind(text1);
//		lbSec.textProperty().bind(text2);
		lbMiddle.textProperty().bind(textMiddle);
		lbLess.textProperty().bind(textLess);

		lbSec.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
		lbSec.getStyleClass().add("oBomb");
		Circle cir = new Circle(30);
		FillTransition ft = new FillTransition(Duration.seconds(1), cir, Color.BLACK, Color.RED);
		stackPane.getChildren().add(cir);
		FXGL.getEngineTimer().runOnceAfter(() -> {
			ft.playFromStart();
			FXGL.getEngineTimer().runOnceAfter(() -> {
				stackPane.getChildren().clear();
				GifsController gifs = new GifsController();
				stackPane.getChildren().add(gifs);
				lbLess.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
				lbLess.getStyleClass().add("lbless");
				FXGL.getEngineTimer().runOnceAfter(() -> {
					gifs.getChildren().clear();
				}, Duration.seconds(0.75));
			}, Duration.seconds(1));
		}, Duration.seconds(5));

	}

	// getters & setters

	public void setW(double width) {
		this.setPrefWidth(width);
	}

	public void setH(double height) {
		this.setPrefHeight(height);
	}

	public StackPane getStackPane() {
		return stackPane;
	}

	public void setStackPane(StackPane stackPane) {
		this.stackPane = stackPane;
	}

	public final SimpleStringProperty text1Property() {
		return this.text1;
	}

	public final String getText1() {
		return this.text1Property().get();
	}

	public final void setText1(final String text1) {
		this.text1Property().set(text1);
	}

	public final SimpleStringProperty text2Property() {
		return this.text2;
	}

	public final String getText2() {
		return this.text2Property().get();
	}

	public final void setText2(final String text2) {
		this.text2Property().set(text2);
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

	public final SimpleStringProperty textMiddleProperty() {
		return this.textMiddle;
	}

	public final String getTextMiddle() {
		return this.textMiddleProperty().get();
	}

	public final void setTextMiddle(final String textMiddle) {
		this.textMiddleProperty().set(textMiddle);
	}

}
