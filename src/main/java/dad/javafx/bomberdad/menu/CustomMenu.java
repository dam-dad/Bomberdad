package dad.javafx.bomberdad.menu;

import java.io.File;
import java.util.Optional;

import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.MenuType;
import com.almasb.fxgl.core.util.Supplier;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.FXGLButton;

import dad.javafx.bomberdad.menu.components.BackgroundController;
import dad.javafx.bomberdad.menu.components.ControlsController;
import dad.javafx.bomberdad.menu.components.TitleController;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class CustomMenu extends FXGLMenu {

	TitleController titleC;
	MediaPlayer mediaPlayerMusic;
	MenuBox menu = null;
	TranslateTransition transicionTrans;
	ScaleTransition transicionScale;

	public CustomMenu(MenuType type) {
		super(type);
		Media mediaMusic = new Media(
				new File(BackgroundController.class.getClassLoader().getResource("./media/musicMenu.mp3").getFile())
						.toURI().toString());
		mediaPlayerMusic = new MediaPlayer(mediaMusic);
		if (type == MenuType.MAIN_MENU) {
			menu = createMenuBodyMainMenu();
			mediaPlayerMusic.play();
		} else
			menu = createMenuBodyGameMenu();

		double menuX = 50.0;
		double menuY = FXGL.getAppHeight() / 2 - menu.getLayoutY() / 2;

		getMenuRoot().setTranslateX(menuX);
		getMenuRoot().setTranslateY(menuY);

		getMenuRoot().getBackground();
		
		getMenuContentRoot().setTranslateX((FXGL.getAppWidth() - 500));
		getMenuContentRoot().setTranslateY(menuY);

		this.getContentRoot().getStylesheets().add(getClass().getResource("/css/TitleCSS.css").toExternalForm());
		this.getContentRoot().getStyleClass().add("root");

		getMenuRoot().getChildren().addAll(menu);

		activeProperty().addListener((observable, wasActive, isActive) -> {
			if (!isActive) {
				switchMenuTo(menu);
			}
		});
		
		transicionTrans.playFromStart();
		transicionScale.playFromStart();
	}

	@Override
	protected Node createBackground(double width, double height) {
		BackgroundController bg = new BackgroundController();
		bg.setS(FXGL.getAppWidth(), FXGL.getAppHeight());
		return bg;
	}

	@Override
	protected Node createTitleView(String title) {
		titleC = new TitleController();
		titleC.setText(title);
		titleC.setW(FXGL.getAppWidth());
		transicionTrans = new TranslateTransition(); 
		transicionTrans.setAutoReverse(true);
		transicionTrans.setCycleCount(Transition.INDEFINITE);
		transicionTrans.setDelay(Duration.ZERO);
		transicionTrans.setDuration(Duration.seconds(0.50));
		transicionTrans.setFromX(-200.0);
		transicionTrans.setToX(200);
		transicionTrans.setFromY(-100.0);
		transicionTrans.setToY(100);
		transicionTrans.setNode(titleC);
		transicionTrans.setInterpolator(Interpolator.EASE_BOTH);
		
		transicionScale = new ScaleTransition(); 
		transicionScale.setAutoReverse(true);
		transicionScale.setCycleCount(Transition.INDEFINITE);
		transicionScale.setDelay(Duration.ZERO);
		transicionScale.setDuration(Duration.seconds(0.50));
		transicionScale.setFromX(1);
		transicionScale.setToX(0.25);
		transicionScale.setFromY(1);
		transicionScale.setToY(0.25);
		transicionScale.setNode(titleC);
		transicionScale.setInterpolator(Interpolator.EASE_BOTH);
		
		return titleC;
	}

	@Override
	protected Node createVersionView(String version) {
		Node view = FXGL.getUIFactory().newText(version);
		view.setTranslateY((FXGL.getAppHeight() - 2));
		return view;
	}

	@Override
	protected Node createProfileView(String profileName) {
		Node view = FXGL.getUIFactory().newText(profileName);
		view.setTranslateY((FXGL.getAppHeight() - 2));
		view.setTranslateX(FXGL.getAppWidth() - view.getLayoutBounds().getWidth());
		return view;
	}

	private MenuBox createMenuBodyMainMenu() {

		MenuBox box = new MenuBox();

		MenuButton itemNewGame = new MenuButton("Nueva Partida");
		itemNewGame.setOnAction(e -> {
			mediaPlayerMusic.stop();
			fireNewGame();
		});
		itemNewGame.getStyleClass().add("btn");
		box.getChildren().add(itemNewGame);

		MenuButton itemOptions = new MenuButton("Controles");
		Supplier<MenuContent> s = new Supplier<FXGLMenu.MenuContent>() {

			@Override
			public MenuContent get() {

				return createContentControl();
			}
		};
		itemOptions.setMenuContent(s);
		itemOptions.getStyleClass().add("btn");
		box.getChildren().add(itemOptions);

		MenuButton itemExit = new MenuButton("Salir");
		itemExit.setOnAction(e -> exit());
		itemExit.getStyleClass().add("btn");
		box.getChildren().add(itemExit);

		return box;
	}

	private void exit() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmacion");
		alert.setContentText("¿Está seguro de querer salir?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			getController().exit();
		}
	}

	protected MenuContent createContentControl() {
		ControlsController controls = new ControlsController();
//    	controls.setW(FXGL.getAppWidth()/10);
		controls.setAlignment(Pos.TOP_RIGHT);
		MenuContent f = new MenuContent(controls);
//    	f.setAlignment(Pos.BOTTOM_RIGHT);
		return f;
	}

	private MenuBox createMenuBodyGameMenu() {
		MenuBox box = new MenuBox();
		MenuButton itemResume = new MenuButton("Continuar");
		itemResume.setOnAction(e -> fireResume());
		box.getChildren().add(itemResume);

		MenuButton itemExit = new MenuButton("Menú Principal");
		itemExit.setOnAction(e -> {
			goToMenu();
		});
		box.getChildren().add(itemExit);

		return box;
	}
	
	private void goToMenu() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmacion");
		alert.setContentText("¿Quiere volver al Menú Principal?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			getController().gotoMainMenu();
			mediaPlayerMusic.play();
		}
	}

	@Override
	protected void switchMenuTo(Node menuBox) {
		Node oldMenu = getMenuRoot().getChildren().get(0);

		FadeTransition ft = new FadeTransition(Duration.seconds(0.33), oldMenu);
		ft.setToValue(0.0);
		ft.setOnFinished(e -> {
			menuBox.setOpacity(0.0);
			getMenuRoot().getChildren().set(0, menuBox);
			oldMenu.setOpacity(1.0);

			FadeTransition ft2 = new FadeTransition(Duration.seconds(0.33), menuBox);
			ft2.setToValue(1.0);
			ft2.play();
		});
		ft.play();
	}

	@Override
	protected void switchMenuContentTo(Node content) {
		getContentRoot().getChildren().set(0, content);
	}

	public class MenuBox extends VBox {
		double layoutHeight;

		public MenuBox() {
		}

		public double get() {
			return (10 * getChildren().size());
		}

		public MenuBox(MenuButton[] items) {
			for (MenuButton item : items) {
				add(item);
			}
		}

		public void add(MenuButton item) {
			item.setParent(this);
			getChildren().addAll(item);
		}
	}

	public class MenuButton extends Pane {

		private MenuBox parent = null;
		private MenuContent cachedContent = null;

//		private Polygon p = new Polygon(0.0, 0.0, 220.0, 0.0, 250.0, 35.0, 0.0, 35.0);

		FXGLButton btn;

		String stringKey;

		public MenuButton(String stringKey) {
			btn = new FXGLButton();
			btn.setText(stringKey);
			btn.getStylesheets().add(getClass().getResource("/css/TitleCSS.css").toExternalForm());
			btn.getStyleClass().add("btn");
			btn.setAlignment(Pos.CENTER_LEFT);
//			btn.setStyle("-fx-background-color: transparent");
//
//			p.setMouseTransparent(true);
//
//			LinearGradient g = new LinearGradient(0.0, 1.0, 1.0, 0.2, true, CycleMethod.NO_CYCLE,
//					new Stop(0.6, Color.color(1.0, 0.8, 0.0, 0.34)), new Stop(0.85, Color.color(1.0, 0.8, 0.0, 0.74)),
//					new Stop(1.0, Color.WHITE));
//
//			p.fillProperty().bind(
//					Bindings.when(btn.pressedProperty()).then((Paint) Color.color(1.0, 0.8, 0.0, 0.75)).otherwise(g));
//
//			p.setStroke(Color.color(0.1, 0.1, 0.1, 0.15));
//			p.setEffect(new GaussianBlur());
//
//			p.visibleProperty().bind(btn.hoverProperty());

			getChildren().addAll(btn);
		}

		private void setOnAction(EventHandler<ActionEvent> e) {
			btn.setOnAction(e);
		}

		private void setParent(MenuBox menu) {
			parent = menu;
		}

		private void setMenuContent(Supplier<MenuContent> contentSupplier) {

			btn.addEventHandler(ActionEvent.ACTION, event -> {
				if (cachedContent == null)
					cachedContent = contentSupplier.get();

				switchMenuContentTo(cachedContent);
			});
		}

		@SuppressWarnings("unused")
		private void setChild(MenuBox menu) {
			MenuButton back = new MenuButton("menu.back");
			menu.getChildren().add(0, back);
			back.addEventHandler(ActionEvent.ACTION, event -> {
				switchMenuTo(this.parent);
			});
			btn.addEventHandler(ActionEvent.ACTION, event -> {
				switchMenuTo(menu);
			});
		}

		public FXGLButton getBtn() {
			return btn;
		}
	}

	@Override
	protected Button createActionButton(String name, Runnable action) {
		MenuButton btn = new MenuButton(name);
		btn.addEventHandler(ActionEvent.ACTION, event -> action.run());
		return btn.getBtn();
	}

	@Override
	protected Button createActionButton(StringBinding name, Runnable action) {
		MenuButton btn = new MenuButton(name.get());
		btn.addEventHandler(ActionEvent.ACTION, event -> action.run());
		return btn.getBtn();
	}

}
