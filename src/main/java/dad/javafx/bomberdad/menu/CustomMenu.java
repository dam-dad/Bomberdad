package dad.javafx.bomberdad.menu;

import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.MenuType;

import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class CustomMenu extends FXGLMenu {

//	private String title;
//	private String version;
//	private String profileName;
//	private int width, height;

	public CustomMenu(MenuType type) {//, String title, String version, String profileName, int width, int height) {
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
		return new Rectangle(width, height, Color.GRAY);
	}

	@Override
	protected Node createTitleView(String title) {
		return new Text(title);
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
