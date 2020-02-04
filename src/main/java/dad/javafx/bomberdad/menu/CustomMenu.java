package dad.javafx.bomberdad.menu;

import java.net.URL;
import java.util.ResourceBundle;

import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.MenuType;
import com.almasb.fxgl.core.util.Supplier;
import com.almasb.fxgl.app.FXGLDefaultMenu.MenuBox;
import com.almasb.fxgl.app.FXGLMenu.MenuContent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.FXGLButton;

import dad.javafx.bomberdad.menu.components.TitleController;
import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class CustomMenu extends FXGLMenu {

	public CustomMenu(MenuType type) {
		super(type);

	}

	@Override
	protected Node createBackground(double width, double height) {
		return new Rectangle(width, height, Color.DARKGREEN);
	}

	@Override
	protected Node createTitleView(String title) {
		TitleController titleC = new TitleController();
		titleC.setText(title);
		titleC.setW(FXGL.getAppWidth());
		return titleC;
	}

	@Override
	protected Node createVersionView(String version) {
		return new Text(version);
	}

	@Override
	protected Node createProfileView(String profileName) {
		return new Text(profileName);
	}

	private MenuBox createExtraMenu() {
		MenuButton itemAchievements = new MenuButton("menu.trophies");
		Supplier<MenuContent> s = new Supplier<FXGLMenu.MenuContent>() {

			@Override
			public MenuContent get() {

				return createContentAchievements();
			}
		};
		itemAchievements.setMenuContent(s);
//		itemAchievements.setMenuContent( Supplier<MenuContent>(this.createContentAchievements()) );
		MenuButton itemCredits = new MenuButton("menu.credits");
		Supplier<MenuContent> s2 = new Supplier<FXGLMenu.MenuContent>() {

			@Override
			public MenuContent get() {

				return createContentCredits();
			}
		};
		itemCredits.setMenuContent(s2);
//		itemCredits.setMenuContent(Supplier { this.createContentCredits() });

		MenuButton itemFeedback = new MenuButton("menu.feedback");
		Supplier<MenuContent> s3 = new Supplier<FXGLMenu.MenuContent>() {

			@Override
			public MenuContent get() {

				return createContentFeedback();
			}
		};
		itemFeedback.setMenuContent(s3);
//        itemFeedback.setMenuContent(Supplier<MenuContent> { this.createContentFeedback() });

		MenuButton[] listMenuButton = { itemAchievements, itemCredits, itemFeedback };

		return new MenuBox(listMenuButton);

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

		private Polygon p = new Polygon(0.0, 0.0, 220.0, 0.0, 250.0, 35.0, 0.0, 35.0);

		FXGLButton btn;

		String stringKey;

		private boolean isAnimating = false;

		public MenuButton(String stringKey) {
			btn = new FXGLButton();
			btn.setAlignment(Pos.CENTER_LEFT);
			btn.setStyle("-fx-background-color: transparent");
			btn.textProperty().bind(FXGL.localizedStringProperty(stringKey));

			p.setMouseTransparent(true);

			LinearGradient g = new LinearGradient(0.0, 1.0, 1.0, 0.2, true, CycleMethod.NO_CYCLE,
					new Stop(0.6, Color.color(1.0, 0.8, 0.0, 0.34)), new Stop(0.85, Color.color(1.0, 0.8, 0.0, 0.74)),
					new Stop(1.0, Color.WHITE));

			p.fillProperty().bind(
					Bindings.when(btn.pressedProperty()).then((Paint) Color.color(1.0, 0.8, 0.0, 0.75)).otherwise(g));

			p.setStroke(Color.color(0.1, 0.1, 0.1, 0.15));
			p.setEffect(new GaussianBlur());

			p.visibleProperty().bind(btn.hoverProperty());

			getChildren().addAll(btn, p);
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
