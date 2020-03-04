package dad.javafx.bomberdad.menu;

import java.util.HashMap;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.dsl.FXGL;

import dad.javafx.bomberdad.BombermanApp;
import javafx.concurrent.Task;

/**
 * Tarea para la pantalla de carga
 * 
 * @author Alejandro Arrocha Hdez, Rosmen Ramos Díaz, Cristian Abad de Vera,
 *         Pablo García Gómez
 *
 */

public class InitTaskLoading extends Task<Void> {

	private BombermanApp app;

	public InitTaskLoading(GameApplication app) {
		this.app = (BombermanApp) app;
	}

	@Override
	protected Void call() throws Exception {
		clearPreviousGame();

		initGame();
		initPhysics();
		initUI();
		initComplete();
		return null;

	}

	private void clearPreviousGame() {
		// log.debug("Clearing previous game");
		FXGL.getGameWorld().clear();
		FXGL.getPhysicsWorld().clear();
		FXGL.getPhysicsWorld().clearCollisionHandlers();
		FXGL.getGameScene().clear();
		FXGL.getGameState().clear();
		FXGL.getGameTimer().clear();
	}

	private void initGame() {
		if (BombermanApp.multiplayer) {
			update("Esperando jugadores", 0);
		} else {
			update("Iniciando partida", 0);
		}

		HashMap<String, Object> vars = new HashMap<String, Object>();
		app.initGameVars(vars);

		vars.forEach((name, value) -> {
			FXGL.getGameState().setValue(name, value);
		});

		app.initGame();
	}

	private void initPhysics() {
		update("Inciando físicas", 1);
		app.initPhysics();
	}

	private void initUI() {
		update("Iniciando UI", 2);
		app.initUI();
	}

	private void initComplete() {
		update("Carga completada", 3);
		FXGL.getGameController().onGameReady(FXGL.getGameState().getProperties());
	}

	private void update(String message, int step) {
		updateMessage(message);
		updateProgress((long) step, 3);
	}

}
