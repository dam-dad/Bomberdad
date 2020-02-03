package dad.javafx.bomberdad.menu;


import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.MenuType;
import com.almasb.fxgl.dsl.FXGL;

import dad.javafx.bomberdad.menu.components.TitleController;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class CustomMenu extends FXGLMenu {
	
	public CustomMenu(MenuType type) {
		super(type);

	}

	@Override
	protected Button createActionButton(String name, Runnable action) {
		Button btn = new Button(name);
		btn.setOnAction(e -> System.exit(0));
		return btn;
	}

	@Override
	protected Button createActionButton(StringBinding name, Runnable action) {
		Button btn = new Button(name.get());
		btn.setOnAction(e -> System.exit(0));
		return btn;
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

}
