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
import javafx.animation.TranslateTransition;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class CustomMenu extends FXGLMenu {

	private BackgroundController bg;
	private TitleController titleC;
	private MediaPlayer mediaPlayerMusic;
	private MenuBox menu = null;
	private TranslateTransition transicionTrans;
	private ScaleTransition transicionScale;
	private FadeTransition transicionFade, transicionFadeBG;
	boolean hidden = true;
	boolean showControls = true;

	public CustomMenu(MenuType type) {
		super(type);
		Media mediaMusic = new Media(
				new File(BackgroundController.class.getClassLoader().getResource("./media/musicedit.mp3").getFile())
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

		getMenuContentRoot().setTranslateX((FXGL.getAppWidth() - 500));
		getMenuContentRoot().setTranslateY(menuY);

		this.getContentRoot().getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
		this.getContentRoot().getStyleClass().add("root");

		getMenuRoot().getChildren().addAll(menu);

		activeProperty().addListener((observable, wasActive, isActive) -> {
			if (!isActive) {
				switchMenuTo(menu);
			}
		});

		transicionTrans.playFromStart();
		transicionScale.playFromStart();
		transicionFade.playFromStart();
		FXGL.getEngineTimer().runOnceAfter(() -> {
			bg.setImage(new Image("./imgs/crabrave.gif"));
			transicionFadeBG.playFromStart();
			titleC.setTextLess("CRAB");
		}, Duration.seconds(7));
	}

	@Override
	protected Node createBackground(double width, double height) {
		bg = new BackgroundController();
		bg.setS(FXGL.getAppWidth(), FXGL.getAppHeight());

		transicionFadeBG = new FadeTransition();
		transicionFadeBG.setFromValue(0.0);
		transicionFadeBG.setToValue(1.0);
		transicionFadeBG.setRate(1.0);
		transicionFadeBG.setNode(bg);
		transicionFadeBG.setInterpolator(Interpolator.LINEAR);

		return bg;
	}

	@Override
	protected Node createTitleView(String title) {
		titleC = new TitleController();
		titleC.setTextLess("MAN");
		titleC.setW(FXGL.getAppWidth());
		titleC.setTranslateY(FXGL.getAppHeight() / 2 - 100);

		transicionTrans = new TranslateTransition();
		transicionTrans.setDelay(Duration.seconds(2));
		transicionTrans.setDuration(Duration.seconds(3));
		transicionTrans.setFromY(FXGL.getAppHeight() / 2 - 100);
		transicionTrans.setToY(0);
		transicionTrans.setNode(titleC);
		transicionTrans.setInterpolator(Interpolator.EASE_BOTH);

		transicionScale = new ScaleTransition();
		transicionScale.setDelay(Duration.seconds(2));
		transicionScale.setDuration(Duration.seconds(3));
		transicionScale.setFromX(1);
		transicionScale.setToX(0.75);
		transicionScale.setFromY(1);
		transicionScale.setToY(0.75);
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
				return createContentControl(false);
			}
		};
		itemOptions.setMenuContent(s);
		itemOptions.getStyleClass().add("btn");
		box.getChildren().add(itemOptions);
		itemOptions.setOnAction(e -> {
			if (showControls) {
				showControls = false;
				switchMenuContentTo(createContentControl(false));
			} else {
				showControls = true;
				switchMenuContentTo(createContentControl(true));
			}
		});

		MenuButton itemThemes = new MenuButton("Temas");
		itemThemes.setOnAction(e -> switchMenuTo(createMenuThemes()));
		itemThemes.getStyleClass().add("btn");
		box.getChildren().add(itemThemes);
		
		MenuButton itemExit = new MenuButton("Salir");
		itemExit.setOnAction(e -> exit());
		itemExit.getStyleClass().add("btn");
		box.getChildren().add(itemExit);

		return box;
	}

	protected MenuContent createContentControlNull() {
		HBox hbox = new HBox();
		MenuContent f = new MenuContent(hbox);
		return f;
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

	protected MenuContent createContentControl(boolean opacity) {
		ControlsController controls = new ControlsController();
		controls.setAlignment(Pos.BOTTOM_RIGHT);
		controls.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
		controls.getStyleClass().add("controls");
		controls.setPrefWidth(FXGL.getAppWidth());
		controls.setPrefHeight(FXGL.getAppHeight());
		MenuContent f = new MenuContent(controls);
		controls.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
		controls.getStyleClass().add("controls");
		if (opacity) {
			controls.setOpacity(0);
		}
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
	
	private MenuBox createMenuThemes() {
		MenuBox box = new MenuBox();
		MenuButton itemBack = new MenuButton("Volver");
		itemBack.setOnAction(e -> switchMenuTo(createMenuBodyMainMenu()));
		box.getChildren().add(itemBack);

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
			this.setPrefWidth(FXGL.getAppWidth() / 3);
			this.setOpacity(0.0);
			this.setAlignment(Pos.CENTER_LEFT);
			transicionFade = new FadeTransition();
			transicionFade.setDelay(Duration.seconds(3));
			transicionFade.setFromValue(0.0);
			transicionFade.setToValue(1.0);
			transicionFade.setRate(2.0);
			transicionFade.setNode(this);
			transicionFade.setInterpolator(Interpolator.LINEAR);
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

		FXGLButton btn;

		String stringKey;

		public MenuButton(String stringKey) {
			this.setPrefWidth(FXGL.getAppWidth() / 3);
			btn = new FXGLButton();
			btn.setText(stringKey);
			btn.getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
			btn.getStyleClass().add("btn");
			btn.setAlignment(Pos.CENTER_LEFT);
			btn.setPrefWidth(FXGL.getAppWidth() / 3);

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
