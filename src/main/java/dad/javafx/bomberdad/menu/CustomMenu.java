package dad.javafx.bomberdad.menu;

import java.util.Random;

import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.particle.ParticleEmitter;
import com.almasb.fxgl.particle.ParticleEmitters;
import com.almasb.fxgl.particle.ParticleSystem;
import com.almasb.fxgl.texture.Texture;

import dad.javafx.bomberdad.menu.components.TitleController;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class CustomMenu extends FXGLMenu {

	private ParticleSystem particleSystem;
	private SimpleObjectProperty<Color> titleColor;
	private double t;

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
		return new Rectangle(width, height, Color.DARKGREEN);
	}

	@Override
	protected Node createTitleView(String title) {
		/*titleColor = new SimpleObjectProperty<Color>(Color.WHITE);

		Text text = FXGL.getUIFactory().newText(title.substring(0, 1), 50.0);
		text.setFill(null);
		text.setStroke(titleColor.get());
		text.setStrokeWidth(1.5);

		Text text2 = FXGL.getUIFactory().newText(title.substring(1, title.length()), 50.0);
		text2.setFill(null);
		text2.setStroke(titleColor.get());
		text2.setStrokeWidth(1.5);

		double textWidth = text.getLayoutBounds().getWidth() + text2.getLayoutBounds().getWidth();

		Rectangle bg = new Rectangle(textWidth + 30, 65.0, null);
		bg.setStroke(Color.WHITE);
		bg.setStrokeWidth(4.0);
		bg.setArcWidth(25.0);
		bg.setArcHeight(25.0);

		ParticleEmitter emitter = ParticleEmitters.newExplosionEmitter(50);

		Texture t = new Texture(new Image("particles/trace_horizontal.png"));
		Random r = new Random();
		emitter.setBlendMode(BlendMode.ADD);
		emitter.setSourceImage(t.getImage());
		emitter.setMaxEmissions(Integer.MAX_VALUE);
		emitter.setSize(18.0, 22.0);
		emitter.setNumParticles(2);
		emitter.setEmissionRate(0.2);
		emitter.setVelocityFunction(i -> {
			if (i % 2 == 0)
				new Point2D(r.nextInt(-10), 0);
			else
				new Point2D(r.nextInt(10), 0);
		});
		emitter.setExpireFunction(Duration.seconds((double) r.nextInt(6 - 4) + 4));
		emitter.setScaleFunction(new Point2D(-0.03, -0.03));
		emitter.setSpawnPointFunction(new Point2D((double) random(0, 0), (double) random(0, 0)));
		emitter.setAccelerationFunction(Point2D(random(-1, 1).toDouble(), random(0, 0).toDouble()));

		HBox box = new HBox(text, text2);
		box.setAlignment(Pos.CENTER);

		StackPane titleRoot = new StackPane();
		titleRoot.getChildren().addAll(bg, box);

		titleRoot.setTranslateX(FXGL.getAppWidth() / 2 - (textWidth + 30) / 2);
		titleRoot.setTranslateY(50.0);

		particleSystem = new ParticleSystem();
		particleSystem.addParticleEmitter(emitter, (double) (FXGL.getAppWidth() / 2 - 30),
				titleRoot.getTranslateY() + 34);
		 */
		
		return new TitleController();
	}

	@Override
	protected Node createVersionView(String version) {
		return new Text(version);
	}

	@Override
	protected Node createProfileView(String profileName) {
		return new Text(profileName);
	}

//	@Override
//	protected void onUpdate(double tpf) {
//		double frequency = 1.7;
//
//		t += tpf * frequency;
//
//		particleSystem.onUpdate(tpf);
//
//		Color color = Color.color(1.0, 1.0, 1.0 , FXGLMath.noise1D(t));
//		Color color = Color.DARKRED;
//		titleColor.set(color);
//	}
}
