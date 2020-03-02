package dad.javafx.bomberdad.menu;


import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.MenuType;

import dad.javafx.bomberdad.menu.components.MenuController;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class CustomMenu extends FXGLMenu {

	public CustomMenu(MenuType type) {
        super(type);
        this.getContentRoot().getStylesheets().add(getClass().getResource("/css/MenuCSS.css").toExternalForm());
        this.getContentRoot().getStyleClass().add("root");
    }

    @Override
    protected Button createActionButton(String name, Runnable action) {
        return new Button(name);
    }

    @Override
    protected Button createActionButton(StringBinding name, Runnable action) {
        return new Button(name.get());
    }

    @Override
    protected Node createBackground(double width, double height) {
        return new MenuController(width,height);
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
