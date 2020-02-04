package dad.javafx.bomberdad.menu.components;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class BackgroundController extends StackPane implements Initializable{

	@FXML
    private StackPane view;

    @FXML
    private MediaView mediaView;
	
	public BackgroundController() {
		super();
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BackgroundView.fxml"));
		loader.setController(this);
		loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Media media = new Media(new File(BackgroundController.class.getClassLoader().getResource("./media/gp.mp4").getFile()).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);  
        mediaView.setMediaPlayer(mediaPlayer);  
        mediaPlayer.setAutoPlay(true);  
	}
	
	public void setS(double w,double h) {
		setPrefSize(w,h);
		mediaView.setFitWidth(w);
		mediaView.setFitHeight(h);
	}

}
