package dad.javafx.bomberdad.menu;

import com.almasb.fxgl.app.LoadingScene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.saving.DataFile;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Controlador para la pantalla de carga
 * @author Alejandro Arrocha Hdez, Rosmen Ramos Díaz, Cristian Abad de Vera, Pablo García Gómez
 *
 */

public class LoadingSceneController extends LoadingScene {

	private boolean loadingFinished = false;
	private DataFile dataFile = DataFile.getEMPTY();
	
	private Rectangle rec;

	public LoadingSceneController() {
		super();
//		getContentRoot().getChildren().addAll(vbox,progress,text);
		rec = (Rectangle) getContentRoot().getChildren().get(0);
		rec = new Rectangle(FXGL.getAppWidth(),FXGL.getAppHeight(),Color.GREY);
		getContentRoot().getChildren().set(0, rec);
	}

	@Override
	public void onCreate() {
		InitTaskLoading initTask = new InitTaskLoading(FXGL.getApp(), dataFile);
		initTask.setOnSucceeded(e -> loadingFinished = true);

		bind(initTask);

		FXGL.getExecutor().execute(initTask);
	}

	@Override
	protected void onUpdate(double tpf) {
		if (loadingFinished) {
			FXGL.getGameController().gotoPlay();
			loadingFinished = false;
		}
	}

}
