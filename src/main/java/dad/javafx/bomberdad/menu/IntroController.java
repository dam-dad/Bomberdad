package dad.javafx.bomberdad.menu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.almasb.fxgl.dsl.FXGL;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class IntroController extends VBox implements Initializable {
	
	@FXML
	private VBox view;
	
	@FXML
	private Label label;

	public IntroController() {
		super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/IntroView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		view.setPrefSize(FXGL.getAppWidth(), FXGL.getAppHeight());
		
		FadeTransition transicionFadeBGBack = new FadeTransition();
		transicionFadeBGBack.setFromValue(0.0);
		transicionFadeBGBack.setToValue(1);
		transicionFadeBGBack.setRate(0.5);
		transicionFadeBGBack.setNode(label);
		
		transicionFadeBGBack.playFromStart();
	}

}
