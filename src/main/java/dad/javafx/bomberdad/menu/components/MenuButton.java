package dad.javafx.bomberdad.menu.components;

import java.net.URL;
import java.util.ResourceBundle;

import com.almasb.fxgl.app.FXGLDefaultMenu.MenuBox;
import com.almasb.fxgl.app.FXGLMenu.MenuContent;
import com.almasb.fxgl.core.util.Supplier;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.FXGLButton;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;

public class MenuButton extends Pane implements Initializable {

	private MenuBox parent = null;
	private MenuContent cachedContent = null;

	private Polygon p = new Polygon(0.0, 0.0, 220.0, 0.0, 250.0, 35.0, 0.0, 35.0);
	
	FXGLButton btn;
	
	String stringKey;

	private boolean isAnimating = false;

	public MenuButton(String stringKey) {
		this.stringKey = stringKey;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btn = new FXGLButton();
		btn.setAlignment(Pos.CENTER_LEFT);
		btn.setStyle("-fx-background-color: transparent");
		btn.textProperty().bind(FXGL.localizedStringProperty(stringKey));
		
		p.setMouseTransparent(true);
		
		LinearGradient g = new LinearGradient(0.0, 1.0, 1.0, 0.2, true, CycleMethod.NO_CYCLE,
                new Stop(0.6, Color.color(1.0, 0.8, 0.0, 0.34)),
                new Stop(0.85, Color.color(1.0, 0.8, 0.0, 0.74)),
                new Stop(1.0, Color.WHITE));
		
		p.fillProperty().bind(
				Bindings.when(btn.pressedProperty()).then((Paint)Color.color(1.0, 0.8, 0.0, 0.75)).otherwise(g)
				);
		
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

//	private void setMenuContent(Supplier<MenuContent> contentSupplier) {
//
//        btn.addEventHandler(ActionEvent.ACTION, event -> {
//            if (cachedContent == null)
//                cachedContent = contentSupplier.get();
//
//            switchMenuContentTo(cachedContent);
//        });
//    }

//	private void setChild(MenuBox menu) {
//		MenuButton back = new MenuButton("menu.back");
//		menu.getChildren().add(0, back);
//		back.addEventHandler(ActionEvent.ACTION, event -> { switchMenuTo(this.MenuButton.parent); });
//		btn.addEventHandler(ActionEvent.ACTION, event -> { switchMenuTo(menu); });
//    }
	
	public FXGLButton getBtn() {
		return btn;
	}

}
