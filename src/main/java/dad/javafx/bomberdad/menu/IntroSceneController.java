package dad.javafx.bomberdad.menu;


import com.almasb.fxgl.app.IntroScene;
import com.almasb.fxgl.dsl.FXGL;

import javafx.animation.FadeTransition;
import javafx.util.Duration;

/**
 * Controlador para la intro del juego
 * @author Alejandro Arrocha Hdez, Rosmen Ramos Díaz, Cristian Abad de Vera, Pablo García Gómez
 *
 */

public class IntroSceneController extends IntroScene {
	@Override
	public void startIntro() {
		getContentRoot().setStyle("-fx-background-color: lightgray");
		IntroController intro = new IntroController();
		getContentRoot().getChildren().add(intro);
		FadeTransition transicionFade = new FadeTransition();
		transicionFade.setFromValue(1.0);
		transicionFade.setToValue(0.0);
		transicionFade.setRate(0.5);
		transicionFade.setNode(intro);
		FXGL.getEngineTimer().runOnceAfter(() -> {
			FXGL.getEngineTimer().runOnceAfter(() -> {
				transicionFade.playFromStart();
				FXGL.getEngineTimer().runOnceAfter(() -> {
					finishIntro();
				}, Duration.seconds(1));
			}, Duration.seconds(1.25));
		}, Duration.seconds(0.75));
	}

}
