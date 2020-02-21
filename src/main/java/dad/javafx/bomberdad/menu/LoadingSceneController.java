package dad.javafx.bomberdad.menu;

import com.almasb.fxgl.app.LoadingScene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.saving.DataFile;
import com.almasb.fxgl.ui.ProgressBar;

import javafx.scene.text.Text;

public class LoadingSceneController extends LoadingScene {

	private ProgressBar progress = new ProgressBar();
	private Text text = new Text();

	private boolean loadingFinished = false;
	private DataFile dataFile = DataFile.getEMPTY();

	public LoadingSceneController() {
		super();
//			    	GameSettings settings = FXGL.getSettings();
//
//			        with(progress) {
//			            setPrefSize(settings.width - 200.0, 10.0)
//			            translateX = 100.0
//			            translateY = settings.height - 100.0
//			        }
//
//			        with(text) {
//			            if (!settings.isExperimentalNative) {
//			                font = FXGL.getUIFactory().newFont(24.0)
//			            }
//
//			            fill = Color.WHITE
//			        }
//
//			        FXGL.centerTextBind(
//			                text,
//			                settings.width / 2.0,
//			                settings.height * 4 / 5.0
//			        )
//
//			        contentRoot.children.addAll(
//			                Rectangle(settings.width.toDouble(),
//			                        settings.height.toDouble(),
//			                        Color.rgb(0, 0, 10)),
//			                progress, text)
//			    }
//
//			    open fun
//
//	bind(task: Task<*>) {
//			        progress.progressProperty().bind(task.progressProperty())
//			        text.textProperty().bind(task.messageProperty())
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
