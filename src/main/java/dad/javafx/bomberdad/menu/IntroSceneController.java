package dad.javafx.bomberdad.menu;

import java.io.File;

import com.almasb.fxgl.app.IntroScene;
import com.almasb.fxgl.dsl.FXGL;

import javafx.animation.FadeTransition;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
		Media mainmusic = new Media(new File(
				IntroSceneController.class.getClassLoader().getResource("./assets/music/punch_sound.wav").getFile())
						.toURI().toString());
		MediaPlayer player = new MediaPlayer(mainmusic);
		FadeTransition transicionFade = new FadeTransition();
		transicionFade.setFromValue(1.0);
		transicionFade.setToValue(0.0);
		transicionFade.setRate(0.5);
		transicionFade.setNode(intro);
		FXGL.getEngineTimer().runOnceAfter(() -> {
			player.play();
			FXGL.getEngineTimer().runOnceAfter(() -> {
				transicionFade.playFromStart();
				FXGL.getEngineTimer().runOnceAfter(() -> {
					finishIntro();
				}, Duration.seconds(1));
			}, Duration.seconds(1.25));
		}, Duration.seconds(0.75));
	}

}
