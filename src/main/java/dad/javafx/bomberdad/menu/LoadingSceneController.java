package dad.javafx.bomberdad.menu;

import com.almasb.fxgl.app.LoadingScene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.saving.DataFile;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class LoadingSceneController extends LoadingScene {

	private boolean loadingFinished = false;
	private DataFile dataFile = DataFile.getEMPTY();
	
	private ProgressBar progress;
	protected Text text;
	private Rectangle rec;

	public LoadingSceneController() {
		super();
//		progress = (ProgressBar) getContentRoot().getChildren().get(1);
//		text = (Text) getContentRoot().getChildren().get(2);
//		getContentRoot().getChildren().clear();
//		VBox vbox = new VBox();
//		vbox.setPrefSize(200, 200);
//		vbox.getChildren().add(new Rectangle(FXGL.getAppWidth(),FXGL.getAppHeight(),Color.DARKRED));
//		getContentRoot().getChildren().addAll(vbox,progress,text);
//		rec = (Rectangle) getContentRoot().getChildren().get(0);
//		rec = new Rectangle(FXGL.getAppWidth(),FXGL.getAppHeight(),Color.DARKRED);
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
