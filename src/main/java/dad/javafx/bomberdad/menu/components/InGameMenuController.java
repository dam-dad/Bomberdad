package dad.javafx.bomberdad.menu.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.almasb.fxgl.dsl.FXGL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class InGameMenuController extends BorderPane implements Initializable {

	// model

	private double w, h;

	// view

	@FXML
	private BorderPane view;

	@FXML
	private ImageView imageView;

	@FXML
	private StackPane title;

	@FXML
	private Label lessText;

	@FXML
	private VBox vBoxBtns, conVbox;

	@FXML
	private Button nuevaPartidaBtn, opcionesBtn, themesBtn, exitBtn;

	public InGameMenuController(double w, double h) {
		super();
		try {
			this.w = w;
			this.h = h;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		view.setPrefSize(w, h);
		vBoxBtns.getChildren().clear();
		Button resumeBtn = new Button("Continuar");
		resumeBtn.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
		resumeBtn.setMaxSize(getMaxWidth(), getMaxHeight());
		resumeBtn.setOnAction(e -> FXGL.getGameController().gotoPlay());
		resumeBtn.setAlignment(Pos.TOP_LEFT);

		resumeBtn.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
		resumeBtn.getStyleClass().add("btn");

		Button backBtn = new Button("Volver al Menu");
		backBtn.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
		backBtn.setMaxSize(getMaxWidth(), getMaxHeight());
		backBtn.setOnAction(e -> {
			view.getChildren().clear();
			view.setCenter(new MenuController(w, h, true));
		});
		backBtn.setAlignment(Pos.TOP_LEFT);

		backBtn.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
		backBtn.getStyleClass().add("btn");

		vBoxBtns.getChildren().addAll(resumeBtn, backBtn);
	}

	@FXML
	void onStartAction(ActionEvent event) {

	}

	@FXML
	void onOptionsAction(ActionEvent event) {

	}

	@FXML
	void onThemesAction(ActionEvent event) {

	}

}
