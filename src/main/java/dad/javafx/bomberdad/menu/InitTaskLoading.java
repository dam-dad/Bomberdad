package dad.javafx.bomberdad.menu;

import java.util.HashMap;
import java.util.Map;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.saving.DataFile;

import dad.javafx.bomberdad.BombermanApp;
import javafx.concurrent.Task;
import javafx.util.Duration;

public class InitTaskLoading extends Task<Void> {

	private BombermanApp app;
	private DataFile dataFile;

	public InitTaskLoading(GameApplication app, DataFile dataFile) {
		this.app = (BombermanApp) app;
		this.dataFile = dataFile;
	}

	@Override
	protected Void call() throws Exception {
		long start = System.nanoTime();
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
		update("Comienza el dududud duelo", 0);

		HashMap<String, Object> vars = new HashMap<String, Object>();
		app.initGameVars(vars);

		vars.forEach((name, value) -> {
			FXGL.getGameState().setValue(name, value);
		});

		if (dataFile == DataFile.getEMPTY()) {
			app.initGame();
		} else {
			app.loadState(dataFile);
		}
	}

	private void initPhysics() {
		update("Initializing Physics", 1);
		app.initPhysics();
	}

	private void initUI() {
		update("Initializing UI", 2);
		app.initUI();
	}

	private void initComplete() {
		update("Initialization Complete", 3);
		FXGL.getGameController().onGameReady(FXGL.getGameState().getProperties());
	}

	private void update(String message, int step) {
		// log.debug(message);
		FXGL.getEngineTimer().runOnceAfter(() -> {
		}, Duration.seconds(5));
		updateMessage(message);
		updateProgress((long) step, 3);
	}

//	@Override
//	protected void failed() {
//		Thread.getDefaultUncaughtExceptionHandler()
//		.uncaughtException(Thread.currentThread(), exception ?: RuntimeException("Initialization failed"));
//	}

}
