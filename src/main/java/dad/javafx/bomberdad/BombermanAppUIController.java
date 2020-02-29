package dad.javafx.bomberdad;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.UIController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BombermanAppUIController implements UIController {

	// model

	private int maxBombs, maxExplosion;

	// view

	@FXML
	private HBox view, hboxApp;

	@FXML
	private VBox vbox, vbox1, vbox3, vbox4;

	@FXML
	private Label player1Lbl, points1Lbl, player4Lbl, points4Lbl, vidas4Lbl, player3Lbl, points3Lbl, vidas3Lbl,
			player2Lbl, points2Lbl, vidas2Lbl;

	@FXML
	private ImageView imgPlayer1, imgPlayer2, imgPlayer3, imgPlayer4, imageViewPower1, imageViewMax1, imageViewPower2,
			imageViewMax2, heartpl11, heartpl12, heartpl13, heartpl14;

	@FXML
	private ProgressBar powerBarpl11, powerBarpl12, powerBarpl13, powerBarpl14, powerBarpl15, maxbBarpl11, maxbBarpl12,
			maxbBarpl13, maxbBarpl14, maxbBarpl15, powerBar2, maxbBar2;

	@Override
	public void init() {
		maxBombs = 1;
		maxExplosion = 1;

		vbox.setPrefWidth(BombermanApp.UI_SIZE);
		vbox1.setPrefWidth(BombermanApp.UI_SIZE);
		hboxApp.setPrefWidth(FXGL.getAppWidth() - BombermanApp.UI_SIZE * 2);
		player1Lbl.setText(BombermanApp.ratings.getPoints().get(0).get(0));
		player2Lbl.setText(BombermanApp.ratings.getPoints().get(1).get(0));
		player3Lbl.setText(BombermanApp.ratings.getPoints().get(2).get(0));
		player4Lbl.setText(BombermanApp.ratings.getPoints().get(3).get(0));
		setPointsLbl(BombermanApp.ratings.getPoints().get(0).get(1), 0);
		setPointsLbl(BombermanApp.ratings.getPoints().get(1).get(1), 1);
		setPointsLbl(BombermanApp.ratings.getPoints().get(2).get(1), 2);
		setPointsLbl(BombermanApp.ratings.getPoints().get(3).get(1), 3);
		vidas2Lbl.setText("4");
		vidas3Lbl.setText("4");
		vidas4Lbl.setText("4");

		imgPlayer1.setImage(new Image("./imgs/tarjeta.png"));
		imgPlayer2.setImage(new Image("./imgs/tarjeta.png"));
		imgPlayer3.setImage(new Image("./imgs/tarjeta.png"));
		imgPlayer4.setImage(new Image("./imgs/tarjeta.png"));
		imageViewPower1.setImage(new Image("./assets/textures/PowerBottle.gif"));
		imageViewMax1.setImage(new Image("./assets/textures/MoreBombsBottle.gif"));
		imageViewPower2.setImage(new Image("./assets/textures/PowerBottle.gif"));
		imageViewMax2.setImage(new Image("./assets/textures/MoreBombsBottle.gif"));
		heartpl11.setImage(new Image("./imgs/heart.png"));
		heartpl12.setImage(new Image("./imgs/heart.png"));
		heartpl13.setImage(new Image("./imgs/heart.png"));
		heartpl14.setImage(new Image("./imgs/heart.png"));

		if (!BombermanApp.multiplayer) {
			vbox3.setOpacity(0);
			vbox4.setOpacity(0);
		}
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

	public void setLifesLbl(String lifes, int id) {
		if (id == 0) {
			if (lifes.equals("3")) {
				heartpl14.setImage(new Image("./imgs/lessheart.gif"));
			} else if (lifes.equals("2")) {
				heartpl13.setImage(new Image("./imgs/lessheart.gif"));
			} else if (lifes.equals("1")) {
				heartpl12.setImage(new Image("./imgs/lessheart.gif"));
			}
		} else if (id == 1) {
			vidas2Lbl.setText(lifes);
		} else if (id == 2) {
			vidas3Lbl.setText(lifes);
		} else if (id == 3) {
			vidas4Lbl.setText(lifes);
		}
	}

	public void setAddProgress(BombermanType powerup, int id) {
		if (powerup == BombermanType.UPPOWER) {
			if (id == 0) {
				if (maxExplosion == 1)
					powerBarpl11.setProgress(1);
				else if (maxExplosion == 2)
					powerBarpl12.setProgress(1);
				else if (maxExplosion == 3)
					powerBarpl13.setProgress(1);
				else if (maxExplosion == 4)
					powerBarpl14.setProgress(1);
				else if (maxExplosion == 5)
					powerBarpl15.setProgress(1);
				maxExplosion++;
			} else if (id == 1) {
				powerBar2.setProgress(powerBar2.getProgress() + 0.2);
			}
//			else if (id == 2) {
//				vidas3Lbl.setText(lifes);
//			} else if (id == 3) {
//				vidas4Lbl.setText(lifes);
//			}
		} else if (powerup == BombermanType.UPMAXBOMBS) {
			if (id == 0) {
				if (maxBombs == 1)
					maxbBarpl11.setProgress(1);
				else if (maxBombs == 2)
					maxbBarpl12.setProgress(1);
				else if (maxBombs == 3)
					maxbBarpl13.setProgress(1);
				else if (maxBombs == 4)
					maxbBarpl14.setProgress(1);
				else if (maxBombs == 5)
					maxbBarpl15.setProgress(1);
				maxBombs++;
			} else if (id == 1) {
				maxbBar2.setProgress(maxbBar2.getProgress() + 0.2);
			}
		}

	}

}
