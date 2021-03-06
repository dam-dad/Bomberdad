package dad.javafx.bomberdad;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.UIController;

import dad.javafx.bomberdad.ratings.TarjetaPuntuaciones;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Controlador de la interfaz de usuario
 * 
 * @author Alejandro Arrocha Hdez, Rosmen Ramos Díaz, Cristian Abad de Vera,
 *         Pablo García Gómez
 *
 */

public class BombermanAppUIController implements UIController {

	// model

	private int maxBombspl1, maxBombspl2, maxBombspl3, maxBombspl4, maxExplosionpl1, maxExplosionpl2, maxExplosionpl3,
			maxExplosionpl4;

	// view

	@FXML
	private HBox view;

	@FXML
	private VBox vboxLeft, vboxRight, vboxpl1, vboxpl4, vboxpl3, vboxpl2;

	@FXML
	private TarjetaPuntuaciones tarjetapl1, tarjetapl4, tarjetapl3, tarjetapl2;

	@FXML
	private ImageView bgLeft, bgRight;

	@FXML
	private HBox hboxApp;

	/**
	 * Inicializa la interfaz de usuario con las puntuaciones
	 */

	@Override
	public void init() {
		maxBombspl1 = 1;
		maxBombspl2 = 1;
		maxBombspl3 = 1;
		maxBombspl4 = 1;
		maxExplosionpl1 = 1;
		maxExplosionpl2 = 1;
		maxExplosionpl3 = 1;
		maxExplosionpl4 = 1;

		vboxLeft.setPrefWidth(BombermanApp.UI_SIZE);
		vboxRight.setPrefWidth(BombermanApp.UI_SIZE);
		bgLeft.setFitWidth(BombermanApp.UI_SIZE);
		bgRight.setFitWidth(BombermanApp.UI_SIZE);
		bgLeft.setFitHeight(FXGL.getAppHeight());
		bgRight.setFitHeight(FXGL.getAppHeight());
		bgLeft.setImage(
				new Image(getClass().getResource("/assets/textures/bg" + BombermanApp.theme + "UI.gif").toString()));
		bgRight.setImage(
				new Image(getClass().getResource("/assets/textures/bg" + BombermanApp.theme + "UI.gif").toString()));
		vboxLeft.setPrefHeight(FXGL.getAppHeight());
		vboxRight.setPrefHeight(FXGL.getAppHeight());
		hboxApp.setPrefWidth(FXGL.getAppWidth() - BombermanApp.UI_SIZE * 3);
		tarjetapl1.getNumberPointsLb().setText(BombermanApp.ratings.getPoints().get(0).get(1));
		tarjetapl2.getNumberPointsLb().setText(BombermanApp.ratings.getPoints().get(1).get(1));
		tarjetapl1.getPlayerNameLb().setText(BombermanApp.ratings.getPoints().get(0).get(0));
		tarjetapl2.getPlayerNameLb().setText(BombermanApp.ratings.getPoints().get(1).get(0));
		if (BombermanApp.numberPlayers >= 3)
			tarjetapl3.getNumberPointsLb().setText(BombermanApp.ratings.getPoints().get(2).get(0));
		if (BombermanApp.numberPlayers >= 4)
			tarjetapl4.getNumberPointsLb().setText(BombermanApp.ratings.getPoints().get(3).get(0));
		setPointsLbl(BombermanApp.ratings.getPoints().get(0).get(1), 0);
		setPointsLbl(BombermanApp.ratings.getPoints().get(1).get(1), 1);
		if (BombermanApp.numberPlayers >= 3)
			setPointsLbl(BombermanApp.ratings.getPoints().get(2).get(1), 2);
		if (BombermanApp.numberPlayers >= 4)
			setPointsLbl(BombermanApp.ratings.getPoints().get(3).get(1), 3);

		vboxpl3.setOpacity(0);
		vboxpl4.setOpacity(0);

	}

	/**
	 * Edita el label con la puntuación dependiendo del jugador
	 * 
	 * @param txt puntos para editar el Label
	 * @param id  del jugador
	 */
	public void setPointsLbl(String txt, int id) {
		if (id == 0) {
			tarjetapl1.getNumberPointsLb().setText(txt);
		} else if (id == 1) {
			tarjetapl2.getNumberPointsLb().setText(txt);
		} else if (id == 2) {
			tarjetapl3.getNumberPointsLb().setText(txt);
		} else if (id == 3) {
			tarjetapl4.getNumberPointsLb().setText(txt);
		}
	}

	/**
	 * Edita el label con las vidas dependiendo del jugador
	 * 
	 * @param lifes vidas para editar el Label
	 * @param id    del jugador
	 */
	public void setLifesLbl(String lifes, int id) {
		if (id == 0) {
			if (lifes.equals("3")) {
				tarjetapl1.getHeartpos4().setImage(new Image(getClass().getResource("/imgs/lessheart.gif").toString()));
			} else if (lifes.equals("2")) {
				tarjetapl1.getHeartpos3().setImage(new Image(getClass().getResource("/imgs/lessheart.gif").toString()));
			} else if (lifes.equals("1")) {
				tarjetapl1.getHeartpos2().setImage(new Image(getClass().getResource("/imgs/lessheart.gif").toString()));
			}
		} else if (id == 1) {
			if (lifes.equals("3")) {
				tarjetapl2.getHeartpos4().setImage(new Image(getClass().getResource("/imgs/lessheart.gif").toString()));
			} else if (lifes.equals("2")) {
				tarjetapl2.getHeartpos3().setImage(new Image(getClass().getResource("/imgs/lessheart.gif").toString()));
			} else if (lifes.equals("1")) {
				tarjetapl2.getHeartpos2().setImage(new Image(getClass().getResource("/imgs/lessheart.gif").toString()));
			}
		} else if (id == 2) {
			if (lifes.equals("3")) {
				tarjetapl3.getHeartpos4().setImage(new Image(getClass().getResource("/imgs/lessheart.gif").toString()));
			} else if (lifes.equals("2")) {
				tarjetapl3.getHeartpos3().setImage(new Image(getClass().getResource("/imgs/lessheart.gif").toString()));
			} else if (lifes.equals("1")) {
				tarjetapl3.getHeartpos2().setImage(new Image(getClass().getResource("/imgs/lessheart.gif").toString()));
			}
		} else if (id == 3) {
			if (lifes.equals("3")) {
				tarjetapl4.getHeartpos4().setImage(new Image(getClass().getResource("/imgs/lessheart.gif").toString()));
			} else if (lifes.equals("2")) {
				tarjetapl4.getHeartpos3().setImage(new Image(getClass().getResource("/imgs/lessheart.gif").toString()));
			} else if (lifes.equals("1")) {
				tarjetapl4.getHeartpos2().setImage(new Image(getClass().getResource("/imgs/lessheart.gif").toString()));
			}
		}
	}

	/**
	 * Edita barra de progreción con los Power Ups dependiendo del jugador
	 * 
	 * @param powerup para distinguir del Power Up rojo o amarillo
	 * @param id      del jugador
	 */
	public void setAddProgress(BombermanType powerup, int id) {
		if (powerup == BombermanType.UPPOWER) {
			if (id == 0) {
				if (maxExplosionpl1 == 1)
					tarjetapl1.getPowerBarpos1().setProgress(1);
				else if (maxExplosionpl1 == 2)
					tarjetapl1.getPowerBarpos2().setProgress(1);
				else if (maxExplosionpl1 == 3)
					tarjetapl1.getPowerBarpos3().setProgress(1);
				else if (maxExplosionpl1 == 4)
					tarjetapl1.getPowerBarpos4().setProgress(1);
				else if (maxExplosionpl1 == 5)
					tarjetapl1.getPowerBarpos5().setProgress(1);
				maxExplosionpl1++;
			} else if (id == 1) {
				if (maxExplosionpl2 == 1)
					tarjetapl2.getPowerBarpos1().setProgress(1);
				else if (maxExplosionpl2 == 2)
					tarjetapl2.getPowerBarpos2().setProgress(1);
				else if (maxExplosionpl2 == 3)
					tarjetapl2.getPowerBarpos3().setProgress(1);
				else if (maxExplosionpl2 == 4)
					tarjetapl2.getPowerBarpos4().setProgress(1);
				else if (maxExplosionpl2 == 5)
					tarjetapl2.getPowerBarpos5().setProgress(1);
				maxExplosionpl2++;
			} else if (id == 2) {
				if (maxExplosionpl3 == 1)
					tarjetapl3.getPowerBarpos1().setProgress(1);
				else if (maxExplosionpl3 == 2)
					tarjetapl3.getPowerBarpos2().setProgress(1);
				else if (maxExplosionpl3 == 3)
					tarjetapl3.getPowerBarpos3().setProgress(1);
				else if (maxExplosionpl3 == 4)
					tarjetapl3.getPowerBarpos4().setProgress(1);
				else if (maxExplosionpl3 == 5)
					tarjetapl3.getPowerBarpos5().setProgress(1);
				maxExplosionpl3++;
			} else if (id == 3) {
				if (maxExplosionpl4 == 1)
					tarjetapl4.getPowerBarpos1().setProgress(1);
				else if (maxExplosionpl4 == 2)
					tarjetapl4.getPowerBarpos2().setProgress(1);
				else if (maxExplosionpl4 == 3)
					tarjetapl4.getPowerBarpos3().setProgress(1);
				else if (maxExplosionpl4 == 4)
					tarjetapl4.getPowerBarpos4().setProgress(1);
				else if (maxExplosionpl4 == 5)
					tarjetapl4.getPowerBarpos5().setProgress(1);
				maxExplosionpl4++;
			}
		} else if (powerup == BombermanType.UPMAXBOMBS) {
			if (id == 0) {
				if (maxBombspl1 == 1)
					tarjetapl1.getMaxBBarpos1().setProgress(1);
				else if (maxBombspl1 == 2)
					tarjetapl1.getMaxBBarpos2().setProgress(1);
				else if (maxBombspl1 == 3)
					tarjetapl1.getMaxBBarpos3().setProgress(1);
				else if (maxBombspl1 == 4)
					tarjetapl1.getMaxBBarpos4().setProgress(1);
				else if (maxBombspl1 == 5)
					tarjetapl1.getMaxBBarpos5().setProgress(1);
				maxBombspl1++;
			} else if (id == 1) {
				if (maxBombspl2 == 1)
					tarjetapl2.getMaxBBarpos1().setProgress(1);
				else if (maxBombspl2 == 2)
					tarjetapl2.getMaxBBarpos2().setProgress(1);
				else if (maxBombspl2 == 3)
					tarjetapl2.getMaxBBarpos3().setProgress(1);
				else if (maxBombspl2 == 4)
					tarjetapl2.getMaxBBarpos4().setProgress(1);
				else if (maxBombspl2 == 5)
					tarjetapl2.getMaxBBarpos5().setProgress(1);
				maxBombspl2++;
			} else if (id == 2) {
				if (maxBombspl3 == 1)
					tarjetapl3.getMaxBBarpos1().setProgress(1);
				else if (maxBombspl3 == 2)
					tarjetapl3.getMaxBBarpos2().setProgress(1);
				else if (maxBombspl3 == 3)
					tarjetapl3.getMaxBBarpos3().setProgress(1);
				else if (maxBombspl3 == 4)
					tarjetapl3.getMaxBBarpos4().setProgress(1);
				else if (maxBombspl3 == 5)
					tarjetapl3.getMaxBBarpos5().setProgress(1);
				maxBombspl3++;
			} else if (id == 3) {
				if (maxBombspl4 == 1)
					tarjetapl4.getMaxBBarpos1().setProgress(1);
				else if (maxBombspl4 == 2)
					tarjetapl4.getMaxBBarpos2().setProgress(1);
				else if (maxBombspl4 == 3)
					tarjetapl4.getMaxBBarpos3().setProgress(1);
				else if (maxBombspl4 == 4)
					tarjetapl4.getMaxBBarpos4().setProgress(1);
				else if (maxBombspl4 == 5)
					tarjetapl4.getMaxBBarpos5().setProgress(1);
				maxBombspl4++;
			}
		}

	}

	public void resetPowerUps(int id) {
		if (id == 0) {
			tarjetapl1.getPowerBarpos1().setProgress(0);
			tarjetapl1.getPowerBarpos2().setProgress(0);
			tarjetapl1.getPowerBarpos3().setProgress(0);
			tarjetapl1.getPowerBarpos4().setProgress(0);
			tarjetapl1.getPowerBarpos5().setProgress(0);
			maxExplosionpl1 = 1;
		} else if (id == 1) {
			tarjetapl2.getPowerBarpos1().setProgress(0);
			tarjetapl2.getPowerBarpos2().setProgress(0);
			tarjetapl2.getPowerBarpos3().setProgress(0);
			tarjetapl2.getPowerBarpos4().setProgress(0);
			tarjetapl2.getPowerBarpos5().setProgress(0);
			maxExplosionpl2 = 1;
		} else if (id == 2) {
			tarjetapl3.getPowerBarpos1().setProgress(0);
			tarjetapl3.getPowerBarpos2().setProgress(0);
			tarjetapl3.getPowerBarpos3().setProgress(0);
			tarjetapl3.getPowerBarpos4().setProgress(0);
			tarjetapl3.getPowerBarpos5().setProgress(0);
			maxExplosionpl3 = 1;
		} else if (id == 3) {
			tarjetapl4.getPowerBarpos1().setProgress(0);
			tarjetapl4.getPowerBarpos2().setProgress(0);
			tarjetapl4.getPowerBarpos3().setProgress(0);
			tarjetapl4.getPowerBarpos4().setProgress(0);
			tarjetapl4.getPowerBarpos5().setProgress(0);
			maxExplosionpl4 = 1;
		}
		if (id == 0) {
			tarjetapl1.getMaxBBarpos1().setProgress(0);
			tarjetapl1.getMaxBBarpos2().setProgress(0);
			tarjetapl1.getMaxBBarpos3().setProgress(0);
			tarjetapl1.getMaxBBarpos4().setProgress(0);
			tarjetapl1.getMaxBBarpos5().setProgress(0);
			maxBombspl1 = 1;
		} else if (id == 1) {
			tarjetapl2.getMaxBBarpos1().setProgress(0);
			tarjetapl2.getMaxBBarpos2().setProgress(0);
			tarjetapl2.getMaxBBarpos3().setProgress(0);
			tarjetapl2.getMaxBBarpos4().setProgress(0);
			tarjetapl2.getMaxBBarpos5().setProgress(0);
			maxBombspl2 = 1;
		} else if (id == 2) {
			tarjetapl3.getMaxBBarpos1().setProgress(0);
			tarjetapl3.getMaxBBarpos2().setProgress(0);
			tarjetapl3.getMaxBBarpos3().setProgress(0);
			tarjetapl3.getMaxBBarpos4().setProgress(0);
			tarjetapl3.getMaxBBarpos5().setProgress(0);
			maxBombspl3 = 1;
		} else if (id == 3) {
			tarjetapl4.getMaxBBarpos1().setProgress(0);
			tarjetapl4.getMaxBBarpos2().setProgress(0);
			tarjetapl4.getMaxBBarpos3().setProgress(0);
			tarjetapl4.getMaxBBarpos4().setProgress(0);
			tarjetapl4.getMaxBBarpos5().setProgress(0);
			maxBombspl4 = 1;
		}
	}

}
