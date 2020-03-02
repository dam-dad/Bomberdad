package dad.javafx.bomberdad.menu.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.almasb.fxgl.dsl.FXGL;

import dad.javafx.bomberdad.BombermanApp;
import dad.javafx.bomberdad.online.Server;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuController extends BorderPane implements Initializable {

	// model

	private double w, h;
	private FadeTransition transicionFadeBGBack;
	private FadeTransition transicionFadeVBoxBtns;
	private FadeTransition transicionFadeBG;
	private Timeline timeline;

	private String ip = "192.168.1.14";

	private int port = 5555;

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

	public MenuController(double w, double h) {
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
//		Media mainmusic = new Media(
//				new File(GenerateMap.class.getClassLoader().getResource("./assets/music/MainMusicdad.mp3").getFile())
//						.toURI().toString());
//		MediaPlayer player = new MediaPlayer(mainmusic);
		view.setPrefSize(w, h);
		vBoxBtns.setOpacity(0);
		conVbox.getChildren().add(new ControlsController());

		TranslateTransition transicionTrans = new TranslateTransition();
		transicionTrans.setDuration(Duration.seconds(3));
		transicionTrans.setFromY(FXGL.getAppHeight() / 2 - 100);
		transicionTrans.setToY(0);
		transicionTrans.setNode(title);
		transicionTrans.setInterpolator(Interpolator.EASE_BOTH);

		ScaleTransition transicionScale = new ScaleTransition();
		transicionScale.setDuration(Duration.seconds(3));
		transicionScale.setFromX(1);
		transicionScale.setToX(0.75);
		transicionScale.setFromY(1);
		transicionScale.setToY(0.75);
		transicionScale.setNode(title);
		transicionScale.setInterpolator(Interpolator.EASE_BOTH);

		transicionFadeBG = new FadeTransition();
		transicionFadeBG.setFromValue(1.0);
		transicionFadeBG.setToValue(0.0);
		transicionFadeBG.setRate(1.5);
		transicionFadeBG.setNode(view);

		transicionFadeBGBack = new FadeTransition();
		transicionFadeBGBack.setFromValue(0.0);
		transicionFadeBGBack.setToValue(1);
		transicionFadeBGBack.setRate(1);
		transicionFadeBGBack.setNode(view);

		transicionFadeVBoxBtns = new FadeTransition();
		transicionFadeVBoxBtns.setFromValue(0.0);
		transicionFadeVBoxBtns.setToValue(1);
		transicionFadeVBoxBtns.setRate(0.1);
		transicionFadeVBoxBtns.setNode(vBoxBtns);
		
		title.setTranslateY(FXGL.getAppHeight() / 2 - 100);

		FXGL.getEngineTimer().runOnceAfter(() -> {
//			player.play();
			transicionTrans.playFromStart();
			transicionScale.playFromStart();
			FXGL.getEngineTimer().runOnceAfter(() -> {
				imageView.setImage(new Image("./assets/textures/Title.gif"));
				FXGL.getEngineTimer().runOnceAfter(() -> {
					transicionFadeBG.playFromStart();
					view.setStyle("-fx-background-color: white;");
					lessText.getStyleClass().add("finalText");
					FXGL.getEngineTimer().runOnceAfter(() -> {
						lessText.setText(BombermanApp.theme.toUpperCase());
						lessText.getStyleClass().add("lbless");
						imageView.setImage(new Image("./assets/textures/TitleStatic.png"));
						view.setStyle("-fx-background-color: gray;");
						transicionFadeBGBack.playFromStart();
						transicionFadeVBoxBtns.playFromStart();
					}, Duration.seconds(0.5));
				}, Duration.seconds(1));
			}, Duration.seconds(3));
		}, Duration.seconds(4));
	}

	@FXML
	void onStartAction(ActionEvent event) {
		RotateTransition flipping = flip(vBoxBtns);
		flipping.playFromStart();
		FXGL.getEngineTimer().runOnceAfter(() -> {
			vBoxBtns.getChildren().clear();
			FXGL.getEngineTimer().runOnceAfter(() -> {
				Button backBtn = new Button("Volver");
				backBtn.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
				backBtn.setMaxSize(getMaxWidth(), getMaxHeight());
				backBtn.setOnAction(e -> backtoMainMenu());
				backBtn.setAlignment(Pos.TOP_LEFT);

				backBtn.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
				backBtn.getStyleClass().add("btn");

				Button offlineModeBtn = new Button("Offline (1vs1)");
				offlineModeBtn.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
				offlineModeBtn.setMaxSize(getMaxWidth(), getMaxHeight());
				offlineModeBtn.setOnAction(e -> FXGL.getGameController().startNewGame());
				offlineModeBtn.setAlignment(Pos.TOP_LEFT);

				offlineModeBtn.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
				offlineModeBtn.getStyleClass().add("btn");

				Button onlineServerModeBtn = new Button("Online (Crear Servidor)");
				onlineServerModeBtn.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
				onlineServerModeBtn.setMaxSize(getMaxWidth(), getMaxHeight());
				onlineServerModeBtn.setOnAction(e -> {
					createServer();
				});
				onlineServerModeBtn.setAlignment(Pos.TOP_LEFT);

				onlineServerModeBtn.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
				onlineServerModeBtn.getStyleClass().add("btn");

				Button onlineClientMode = new Button("Online (Buscar Servidor)");
				onlineClientMode.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
				onlineClientMode.setMaxSize(getMaxWidth(), getMaxHeight());
				onlineClientMode.setOnAction(e -> {
					buscarServer();
				});
				onlineClientMode.setAlignment(Pos.TOP_LEFT);

				onlineClientMode.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
				onlineClientMode.getStyleClass().add("btn");

				vBoxBtns.getChildren().addAll(backBtn, offlineModeBtn, onlineServerModeBtn, onlineClientMode);
			}, Duration.millis(125));
		}, Duration.millis(200));
	}

	private void buscarServer() {
		RotateTransition flipping = flip(vBoxBtns);
		flipping.playFromStart();
		FXGL.getEngineTimer().runOnceAfter(() -> {
			vBoxBtns.getChildren().clear();
			FXGL.getEngineTimer().runOnceAfter(() -> {
				Button backBtn = new Button("Volver");
				backBtn.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
				backBtn.setMaxSize(getMaxWidth(), getMaxHeight());
				backBtn.setOnAction(e -> backtoMainMenu());
				backBtn.setAlignment(Pos.TOP_LEFT);

				backBtn.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
				backBtn.getStyleClass().add("btn");

				Button buscar = new Button("Conectarse al Servidor");
				buscar.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
				buscar.setMaxSize(getMaxWidth(), getMaxHeight());
				buscar.setOnAction(e -> {
					BombermanApp.multiplayer = true;
					BombermanApp.ip = ip;
					BombermanApp.port = port;
					FXGL.getGameController().startNewGame();
				});
				buscar.setAlignment(Pos.TOP_LEFT);

				buscar.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
				buscar.getStyleClass().add("btn");

				vBoxBtns.getChildren().addAll(backBtn, buscar);
			}, Duration.millis(125));
		}, Duration.millis(200));
	}

	private void createServer() {
		RotateTransition flipping = flip(vBoxBtns);
		flipping.playFromStart();
		FXGL.getEngineTimer().runOnceAfter(() -> {
			vBoxBtns.getChildren().clear();
			FXGL.getEngineTimer().runOnceAfter(() -> {
				Button backBtn = new Button("Volver");
				backBtn.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
				backBtn.setMaxSize(getMaxWidth(), getMaxHeight());
				backBtn.setOnAction(e -> backtoMainMenu());
				backBtn.setAlignment(Pos.TOP_LEFT);

				backBtn.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
				backBtn.getStyleClass().add("btn");

				Button playersNum4 = new Button("4 Jugadores");
				playersNum4.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
				playersNum4.setMaxSize(getMaxWidth(), getMaxHeight());
				playersNum4.setOnAction(e -> {
					Server server = new Server(4);
					server.start();
					BombermanApp.multiplayer = true;
					BombermanApp.ip = ip;
					BombermanApp.port = port;
					FXGL.getGameController().startNewGame();
				});
				playersNum4.setAlignment(Pos.TOP_LEFT);

				playersNum4.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
				playersNum4.getStyleClass().add("btn");

				Button playersNum3 = new Button("3 Jugadores");
				playersNum3.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
				playersNum3.setMaxSize(getMaxWidth(), getMaxHeight());
				playersNum3.setOnAction(e -> {
					Server server = new Server(3);
					server.start();
					BombermanApp.multiplayer = true;
					BombermanApp.ip = ip;
					BombermanApp.port = port;
					FXGL.getGameController().startNewGame();
				});
				playersNum3.setAlignment(Pos.TOP_LEFT);

				playersNum3.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
				playersNum3.getStyleClass().add("btn");

				Button playersNum2 = new Button("2 Jugadores");
				playersNum2.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
				playersNum2.setMaxSize(getMaxWidth(), getMaxHeight());
				playersNum2.setOnAction(e -> {
					Server server = new Server(2);
					server.start();
					BombermanApp.multiplayer = true;
					BombermanApp.ip = ip;
					BombermanApp.port = port;
					FXGL.getGameController().startNewGame();
				});
				playersNum2.setAlignment(Pos.TOP_LEFT);

				playersNum2.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
				playersNum2.getStyleClass().add("btn");

				vBoxBtns.getChildren().addAll(backBtn, playersNum2, playersNum3, playersNum4);
			}, Duration.millis(125));
		}, Duration.millis(200));
	}

	@FXML
	void onThemesAction(ActionEvent event) {
		RotateTransition flipping = flip(vBoxBtns);
		flipping.playFromStart();
		FXGL.getEngineTimer().runOnceAfter(() -> {
			vBoxBtns.getChildren().clear();
			FXGL.getEngineTimer().runOnceAfter(() -> {
				Button backBtn = new Button("Volver");
				backBtn.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
				backBtn.setMaxSize(getMaxWidth(), getMaxHeight());
				backBtn.setOnAction(e -> backtoMainMenu());
				backBtn.setAlignment(Pos.TOP_LEFT);

				backBtn.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
				backBtn.getStyleClass().add("btn");

				Button crabThemeBtn = new Button("BomberCRAB");
				crabThemeBtn.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
				crabThemeBtn.setMaxSize(getMaxWidth(), getMaxHeight());
				crabThemeBtn.setOnAction(e -> titleAnimation("crab"));
				crabThemeBtn.setAlignment(Pos.TOP_LEFT);

				crabThemeBtn.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
				crabThemeBtn.getStyleClass().add("btn");

				Button fireThemeBtn = new Button("BomberFIRE");
				fireThemeBtn.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
				fireThemeBtn.setMaxSize(getMaxWidth(), getMaxHeight());
				fireThemeBtn.setOnAction(e -> titleAnimation("fire"));
				fireThemeBtn.setAlignment(Pos.TOP_LEFT);

				fireThemeBtn.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
				fireThemeBtn.getStyleClass().add("btn");

				Button dadThemeBtn = new Button("BomberDAD");
				dadThemeBtn.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
				dadThemeBtn.setMaxSize(getMaxWidth(), getMaxHeight());
				dadThemeBtn.setOnAction(e -> titleAnimation("dad"));
				dadThemeBtn.setAlignment(Pos.TOP_LEFT);

				dadThemeBtn.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
				dadThemeBtn.getStyleClass().add("btn");

				vBoxBtns.getChildren().addAll(backBtn, crabThemeBtn, fireThemeBtn, dadThemeBtn);
			}, Duration.millis(125));
		}, Duration.millis(200));
	}

	@FXML
	void onOptionsAction(ActionEvent event) {
		RotateTransition flipping = flip(vBoxBtns);
		flipping.playFromStart();
		FXGL.getEngineTimer().runOnceAfter(() -> {
			vBoxBtns.getChildren().clear();
			FXGL.getEngineTimer().runOnceAfter(() -> {
				Button backBtn = new Button("Volver");
				backBtn.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
				backBtn.setMaxSize(getMaxWidth(), getMaxHeight());
				backBtn.setOnAction(e -> {
					conVbox.setOpacity(0);
					backtoMainMenu();
				});
				backBtn.setAlignment(Pos.TOP_LEFT);

				backBtn.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
				backBtn.getStyleClass().add("btn");

				Button verControlesBtn = new Button("Ver Controles");
				verControlesBtn.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
				verControlesBtn.setMaxSize(getMaxWidth(), getMaxHeight());
				verControlesBtn.setOnAction(e -> verControles());
				verControlesBtn.setAlignment(Pos.TOP_LEFT);

				verControlesBtn.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
				verControlesBtn.getStyleClass().add("btn");

				Button fullscreenBtn = new Button("Pantalla Completa");
				fullscreenBtn.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
				fullscreenBtn.setMaxSize(getMaxWidth(), getMaxHeight());
				fullscreenBtn.setOnAction(e -> {
					Stage stage = (Stage) getScene().getWindow();
					if (!stage.isFullScreen()) {
						stage.setFullScreen(true);
					} else {
						stage.setFullScreen(false);
					}
				});
				fullscreenBtn.setAlignment(Pos.TOP_LEFT);

				fullscreenBtn.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
				fullscreenBtn.getStyleClass().add("btn");

				vBoxBtns.getChildren().addAll(backBtn, verControlesBtn, fullscreenBtn);
			}, Duration.millis(125));
		}, Duration.millis(200));
	}

	private void titleAnimation(String theme) {
		BombermanApp.theme = theme;
		imageView.setImage(new Image("./assets/textures/Title.gif"));
		explosionAni();
		timeline.play();
		FXGL.getEngineTimer().runOnceAfter(() -> {
			transicionFadeBG.playFromStart();
			view.setStyle("-fx-background-color: white;");
			lessText.getStyleClass().add("finalText");
			FXGL.getEngineTimer().runOnceAfter(() -> {
				lessText.setText(theme.toUpperCase());
				if (theme.equals("crab")) {
					this.getStylesheets().clear();
					this.getStylesheets().add(getClass().getResource("/css/MenuCSSCrab.css").toExternalForm());
				}
				if (theme.equals("fire")) {
					this.getStylesheets().clear();
					this.getStylesheets().add(getClass().getResource("/css/MenuCSSFire.css").toExternalForm());
				}
				if (theme.equals("dad")) {
					this.getStylesheets().clear();
					this.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
				}
				imageView.setImage(new Image("./assets/textures/TitleStatic.png"));
				view.setStyle("-fx-background-color: gray;");
				transicionFadeBGBack.playFromStart();
				transicionFadeVBoxBtns.playFromStart();
			}, Duration.seconds(0.5));
		}, Duration.seconds(1));
	}

	private void backtoMainMenu() {
		RotateTransition flipping = flip(vBoxBtns);
		flipping.playFromStart();
		FXGL.getEngineTimer().runOnceAfter(() -> {
			vBoxBtns.getChildren().clear();
			FXGL.getEngineTimer().runOnceAfter(() -> {
				vBoxBtns.getChildren().addAll(nuevaPartidaBtn, opcionesBtn, themesBtn, exitBtn);
			}, Duration.millis(125));
		}, Duration.millis(200));
	}

	private RotateTransition flip(Node node) {
		RotateTransition rotator = new RotateTransition(Duration.millis(500), node);
		rotator.setAxis(Rotate.Y_AXIS);
		rotator.setFromAngle(0);
		rotator.setToAngle(360);
		rotator.setInterpolator(Interpolator.LINEAR);
		rotator.setCycleCount(1);
		return rotator;
	}

	private void verControles() {
		if (conVbox.getOpacity() != 1) {
			conVbox.setOpacity(1);
		} else {
			conVbox.setOpacity(0);
		}
	}

	private void explosionAni() {
		KeyFrame kf1 = new KeyFrame(Duration.ZERO,
				new KeyValue(vBoxBtns.getChildren().get(0).translateXProperty(), 10.0),
				new KeyValue(vBoxBtns.getChildren().get(0).translateYProperty(), 5.0),
				new KeyValue(vBoxBtns.getChildren().get(1).translateXProperty(), -10.0),
				new KeyValue(vBoxBtns.getChildren().get(1).translateYProperty(), -5.0),
				new KeyValue(vBoxBtns.getChildren().get(2).translateXProperty(), 4.0),
				new KeyValue(vBoxBtns.getChildren().get(2).translateYProperty(), -7.0),
				new KeyValue(vBoxBtns.getChildren().get(3).translateXProperty(), -10.0),
				new KeyValue(vBoxBtns.getChildren().get(3).translateYProperty(), -5.0));

		KeyFrame kf2 = new KeyFrame(Duration.seconds(0.2),
				new KeyValue(vBoxBtns.getChildren().get(0).translateXProperty(), 0.0),
				new KeyValue(vBoxBtns.getChildren().get(0).translateYProperty(), 0.0),
				new KeyValue(vBoxBtns.getChildren().get(1).translateXProperty(), 0.0),
				new KeyValue(vBoxBtns.getChildren().get(1).translateYProperty(), 0.0),
				new KeyValue(vBoxBtns.getChildren().get(2).translateXProperty(), 0.0),
				new KeyValue(vBoxBtns.getChildren().get(2).translateYProperty(), 0.0),
				new KeyValue(vBoxBtns.getChildren().get(3).translateXProperty(), 0.0),
				new KeyValue(vBoxBtns.getChildren().get(3).translateYProperty(), 0.0));

		KeyFrame kf3 = new KeyFrame(Duration.seconds(0.4),
				new KeyValue(vBoxBtns.getChildren().get(0).translateXProperty(), 10.0),
				new KeyValue(vBoxBtns.getChildren().get(0).translateYProperty(), 5.0),
				new KeyValue(vBoxBtns.getChildren().get(1).translateXProperty(), -10.0),
				new KeyValue(vBoxBtns.getChildren().get(1).translateYProperty(), -5.0),
				new KeyValue(vBoxBtns.getChildren().get(2).translateXProperty(), 4.0),
				new KeyValue(vBoxBtns.getChildren().get(2).translateYProperty(), -7.0),
				new KeyValue(vBoxBtns.getChildren().get(3).translateXProperty(), -10.0),
				new KeyValue(vBoxBtns.getChildren().get(3).translateYProperty(), -5.0));

		KeyFrame kf4 = new KeyFrame(Duration.seconds(0.6),
				new KeyValue(vBoxBtns.getChildren().get(0).translateXProperty(), 0.0),
				new KeyValue(vBoxBtns.getChildren().get(0).translateYProperty(), 0.0),
				new KeyValue(vBoxBtns.getChildren().get(1).translateXProperty(), 0.0),
				new KeyValue(vBoxBtns.getChildren().get(1).translateYProperty(), 0.0),
				new KeyValue(vBoxBtns.getChildren().get(2).translateXProperty(), 0.0),
				new KeyValue(vBoxBtns.getChildren().get(2).translateYProperty(), 0.0),
				new KeyValue(vBoxBtns.getChildren().get(3).translateXProperty(), 0.0),
				new KeyValue(vBoxBtns.getChildren().get(3).translateYProperty(), 0.0));

		KeyFrame kf5 = new KeyFrame(Duration.seconds(2),
				new KeyValue(vBoxBtns.getChildren().get(0).translateXProperty(), -1000.0),
				new KeyValue(vBoxBtns.getChildren().get(0).translateYProperty(), 5000.0),
				new KeyValue(vBoxBtns.getChildren().get(1).translateXProperty(), -5000.0),
				new KeyValue(vBoxBtns.getChildren().get(1).translateYProperty(), 5000.0),
				new KeyValue(vBoxBtns.getChildren().get(2).translateXProperty(), -8000.0),
				new KeyValue(vBoxBtns.getChildren().get(2).translateYProperty(), 5000.0),
				new KeyValue(vBoxBtns.getChildren().get(3).translateXProperty(), -8000.0),
				new KeyValue(vBoxBtns.getChildren().get(3).translateYProperty(), 5000.0));

		KeyFrame kf6 = new KeyFrame(Duration.seconds(2.40),
				new KeyValue(vBoxBtns.getChildren().get(0).translateXProperty(), 0.0),
				new KeyValue(vBoxBtns.getChildren().get(0).translateYProperty(), 0.0),
				new KeyValue(vBoxBtns.getChildren().get(1).translateXProperty(), 0.0),
				new KeyValue(vBoxBtns.getChildren().get(1).translateYProperty(), 0.0),
				new KeyValue(vBoxBtns.getChildren().get(2).translateXProperty(), 0.0),
				new KeyValue(vBoxBtns.getChildren().get(2).translateYProperty(), 0.0),
				new KeyValue(vBoxBtns.getChildren().get(3).translateXProperty(), 0.0),
				new KeyValue(vBoxBtns.getChildren().get(3).translateYProperty(), 0.0));

		timeline = new Timeline();
		timeline.setAutoReverse(true);
		timeline.setCycleCount(1);
		timeline.getKeyFrames().addAll(kf1, kf2, kf3, kf4, kf5, kf6);
	}

}
