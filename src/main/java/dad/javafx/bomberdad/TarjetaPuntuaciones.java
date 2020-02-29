package dad.javafx.bomberdad;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TarjetaPuntuaciones extends StackPane implements Initializable {
	// view

	@FXML
	private StackPane stackPane;

	@FXML
	private ImageView backgroundImage;

	@FXML
	private VBox vbox;

	@FXML
	private Label playerNameLb;

	@FXML
	private ImageView heartpos1, heartpos2, heartpos3, heartpos4;

	@FXML
	private ImageView imageViewPower1;

	@FXML
	private ProgressBar powerBarpos1, powerBarpos2, powerBarpos3, powerBarpos4, powerBarpos5;

	@FXML
	private ImageView imageViewMax1;

	@FXML
	private ProgressBar maxBBarpos1, maxBBarpos2, maxBBarpos3, maxBBarpos4, maxBBarpos5;

	@FXML
	private Label pointsTextLb, numberPointsLb;

	public TarjetaPuntuaciones() {
		super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TarjetaPuntuacionesView.fxml"));
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

	public ImageView getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(ImageView backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public Label getPlayerNameLb() {
		return playerNameLb;
	}

	public void setPlayerNameLb(Label playerNameLb) {
		this.playerNameLb = playerNameLb;
	}

	public ImageView getImageViewPower1() {
		return imageViewPower1;
	}

	public void setImageViewPower1(ImageView imageViewPower1) {
		this.imageViewPower1 = imageViewPower1;
	}

	public ProgressBar getPowerBarpos1() {
		return powerBarpos1;
	}

	public void setPowerBarpos1(ProgressBar powerBarpos1) {
		this.powerBarpos1 = powerBarpos1;
	}

	public ProgressBar getPowerBarpos2() {
		return powerBarpos2;
	}

	public void setPowerBarpos2(ProgressBar powerBarpos2) {
		this.powerBarpos2 = powerBarpos2;
	}

	public ProgressBar getPowerBarpos3() {
		return powerBarpos3;
	}

	public void setPowerBarpos3(ProgressBar powerBarpos3) {
		this.powerBarpos3 = powerBarpos3;
	}

	public ProgressBar getPowerBarpos4() {
		return powerBarpos4;
	}

	public void setPowerBarpos4(ProgressBar powerBarpos4) {
		this.powerBarpos4 = powerBarpos4;
	}

	public ProgressBar getPowerBarpos5() {
		return powerBarpos5;
	}

	public void setPowerBarpos5(ProgressBar powerBarpos5) {
		this.powerBarpos5 = powerBarpos5;
	}

	public ImageView getImageViewMax1() {
		return imageViewMax1;
	}

	public void setImageViewMax1(ImageView imageViewMax1) {
		this.imageViewMax1 = imageViewMax1;
	}

	public ProgressBar getMaxBBarpos1() {
		return maxBBarpos1;
	}

	public void setMaxBBarpos1(ProgressBar maxBBarpos1) {
		this.maxBBarpos1 = maxBBarpos1;
	}

	public ProgressBar getMaxBBarpos2() {
		return maxBBarpos2;
	}

	public void setMaxBBarpos2(ProgressBar maxBBarpos2) {
		this.maxBBarpos2 = maxBBarpos2;
	}

	public ProgressBar getMaxBBarpos3() {
		return maxBBarpos3;
	}

	public void setMaxBBarpos3(ProgressBar maxBBarpos3) {
		this.maxBBarpos3 = maxBBarpos3;
	}

	public ProgressBar getMaxBBarpos4() {
		return maxBBarpos4;
	}

	public void setMaxBBarpos4(ProgressBar maxBBarpos4) {
		this.maxBBarpos4 = maxBBarpos4;
	}

	public ProgressBar getMaxBBarpos5() {
		return maxBBarpos5;
	}

	public void setMaxBBarpos5(ProgressBar maxBBarpos5) {
		this.maxBBarpos5 = maxBBarpos5;
	}

	public Label getNumberPointsLb() {
		return numberPointsLb;
	}

	public void setNumberPointsLb(Label numberPointsLb) {
		this.numberPointsLb = numberPointsLb;
	}

	public StackPane getStackPane() {
		return stackPane;
	}

	public void setStackPane(StackPane stackPane) {
		this.stackPane = stackPane;
	}

	public VBox getVbox() {
		return vbox;
	}

	public void setVbox(VBox vbox) {
		this.vbox = vbox;
	}

	public ImageView getHeartpos1() {
		return heartpos1;
	}

	public void setHeartpos1(ImageView heartpos1) {
		this.heartpos1 = heartpos1;
	}

	public ImageView getHeartpos2() {
		return heartpos2;
	}

	public void setHeartpos2(ImageView heartpos2) {
		this.heartpos2 = heartpos2;
	}

	public ImageView getHeartpos3() {
		return heartpos3;
	}

	public void setHeartpos3(ImageView heartpos3) {
		this.heartpos3 = heartpos3;
	}

	public ImageView getHeartpos4() {
		return heartpos4;
	}

	public void setHeartpos4(ImageView heartpos4) {
		this.heartpos4 = heartpos4;
	}

	public Label getPointsTextLb() {
		return pointsTextLb;
	}

	public void setPointsTextLb(Label pointsTextLb) {
		this.pointsTextLb = pointsTextLb;
	}

}
