package dad.javafx.bomberdad.menu;

import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.MenuType;
import com.almasb.fxgl.dsl.FXGL;

import javafx.beans.binding.StringBinding;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class CustomMenu extends FXGLMenu {

	public CustomMenu(MenuType type) {
		super(type);

	}

	@Override
	protected Button createActionButton(String name, Runnable action) {
		return new Button(name);
	}

	@Override
	protected Button createActionButton(StringBinding name, Runnable action) {
		Button btn = new Button(name.get());
		btn.setOnAction(e -> System.exit(0));
		return btn;
	}

	@Override
	protected Node createBackground(double width, double height) {
		return new Rectangle(width, height, Color.BROWN);
	}

	@Override
	protected Node createTitleView(String title) {

		Text text = FXGL.getUIFactory().newText(title.substring(0, 1), 50.0);
		text.setFill(null);
		text.setStroke(Color.WHITE);
		text.setStrokeWidth(1.5);

		Text text2 = FXGL.getUIFactory().newText(title.substring(1, title.length()), 50.0);
		text2.setFill(null);
		text2.setStroke(Color.WHITE);
		text2.setStrokeWidth(1.5);

		double textWidth = text.getLayoutBounds().getWidth() + text2.getLayoutBounds().getWidth();

		Rectangle bg = new Rectangle(textWidth + 30, 65.0, null);
		bg.setStroke(Color.WHITE);
		bg.setStrokeWidth(4.0);
		bg.setArcWidth(25.0);
		bg.setArcHeight(25.0);

		HBox box = new HBox(text,text2);
		box.setAlignment(Pos.CENTER);
		
		StackPane titleRoot = new StackPane();
		titleRoot.getChildren().addAll(bg,box);
		
		titleRoot.setTranslateX(FXGL.getAppWidth() / 2 - (textWidth + 30) / 2);
		titleRoot.setTranslateY(50.0);

		return titleRoot;
	}

	@Override
	protected Node createVersionView(String version) {
		return new Text(version);
	}

	@Override
	protected Node createProfileView(String profileName) {
		return new Text(profileName);
	}

}
