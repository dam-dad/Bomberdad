package dad.javafx.bomberdad.menu;

import com.almasb.fxgl.app.IntroScene;
import com.almasb.fxgl.dsl.FXGL;

import javafx.util.Duration;

public class IntroSceneController extends IntroScene {

	@Override
	public void startIntro() {
		IntroController intro = new IntroController();
		getContentRoot().getChildren().add(intro);

		FXGL.getEngineTimer().runOnceAfter(() -> {
			finishIntro();
		}, Duration.seconds(3));
		
	}

}
