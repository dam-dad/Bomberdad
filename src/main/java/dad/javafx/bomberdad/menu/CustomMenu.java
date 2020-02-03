package dad.javafx.bomberdad.menu;


import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.MenuType;
import com.almasb.fxgl.particle.ParticleSystem;
import com.almasb.fxgl.ui.FXGLButton;

import dad.javafx.bomberdad.menu.components.MenuButton;
import dad.javafx.bomberdad.menu.components.TitleController;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
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
		MenuButton btn = new MenuButton(name);
		btn.addEventHandler(ActionEvent.ACTION, event->action.run());
		return btn.getBtn();
	}

	@Override
	protected Button createActionButton(StringBinding name, Runnable action) {
		MenuButton btn = new MenuButton(name.get());
		btn.addEventHandler(ActionEvent.ACTION, event->action.run());
		return btn.getBtn();
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
	
	@Override
	protected void switchMenuContentTo(Node content) {
		// TODO Auto-generated method stub
		super.switchMenuContentTo(content);
	}
	

}
