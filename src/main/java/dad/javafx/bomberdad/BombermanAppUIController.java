package dad.javafx.bomberdad;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.UIController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BombermanAppUIController implements UIController {

    @FXML
    private HBox view;

    @FXML
    private VBox vbox;

    @FXML
    private Label player1Lbl;

    @FXML
    private Label points1Lbl;

    @FXML
    private Label vidas1Lbl;

    @FXML
    private Label player4Lbl;

    @FXML
    private Label points4Lbl;

    @FXML
    private Label vidas4Lbl;

    @FXML
    private HBox hboxApp;

    @FXML
    private VBox vbox1;

    @FXML
    private Label player3Lbl;

    @FXML
    private Label points3Lbl;

    @FXML
    private Label vidas3Lbl;

    @FXML
    private Label player2Lbl;

    @FXML
    private Label points2Lbl;

    @FXML
    private Label vidas2Lbl;
	
	@Override
	public void init() {
		vbox.setPrefWidth(BombermanApp.UI_SIZE);
		vbox1.setPrefWidth(BombermanApp.UI_SIZE);
		hboxApp.setPrefWidth(FXGL.getAppWidth());
		player1Lbl.setText(BombermanApp.ratings.getPoints().get(0).get(0));
		player2Lbl.setText(BombermanApp.ratings.getPoints().get(1).get(0));
		player3Lbl.setText(BombermanApp.ratings.getPoints().get(2).get(0));
		player4Lbl.setText(BombermanApp.ratings.getPoints().get(3).get(0));
		setPointsLbl(BombermanApp.ratings.getPoints().get(0).get(1), 0);
		setPointsLbl(BombermanApp.ratings.getPoints().get(1).get(1), 1);
		setPointsLbl(BombermanApp.ratings.getPoints().get(2).get(1), 2);
		setPointsLbl(BombermanApp.ratings.getPoints().get(3).get(1), 3);
	}

	
	public void setPointsLbl(String txt, int id) {
		if (id == 0) {
			points1Lbl.setText(txt);
		} else if (id == 1) {
			points2Lbl.setText(txt);
		} else if (id == 2) {
			points3Lbl.setText(txt);
		} else if (id == 3) {
			points4Lbl.setText(txt);
		}
	}

}
