package dad.javafx.bomberdad.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.TransformComponent;
import dad.javafx.bomberdad.BombermanApp;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
//import static dad.javafx.bomberdad.components.MoveDirection.*;

@Required(AStarMoveComponent.class)
public class PlayerComponent extends Component {

	public TransformComponent position;

	private int maxBombs = 1;
	private int bombsPlaced = 0;

	private int vidas = 3;
	private String name;
	private int power = 0;

	private AStarMoveComponent astar;

	public void up() {
			astar.moveToUpCell();
	}

	public void down() {
			astar.moveToDownCell();
	}

	public void left() {
			astar.moveToLeftCell();
	}

	public void right() {
			astar.moveToRightCell();
	}

	public void increaseMaxBombs() {
		maxBombs++;
	}

	public void resetMaxBombs() {
		maxBombs = 1;
	}

	public void increasePower() {
		if (power <= 45) {
			power = power + BombermanApp.TILE_SIZE;
		}
	}

	public void resetPower() {
		power = 0;
	}

	public AStarMoveComponent getAstar() {
		return astar;
	}

	public void setAstar(AStarMoveComponent astar) {
		this.astar = astar;
	}

	public void placeBomb() {
		if (vidas >= 0) {
			if (bombsPlaced == maxBombs) {
				return;
			}

			bombsPlaced++;

			int x = (int) position.getX() / BombermanApp.TILE_SIZE;
			int y = (int) position.getY() / BombermanApp.TILE_SIZE;
			Entity bomb = FXGL.getGameWorld().spawn("Bomb",
					new SpawnData(x * BombermanApp.TILE_SIZE, y * BombermanApp.TILE_SIZE).put("radius",
							(BombermanApp.TILE_SIZE / 2) + power));

			FXGL.getGameTimer().runOnceAfter(() -> {
				bomb.getComponent(BombComponent.class).explode(power, this);
				bombsPlaced--;
			}, Duration.seconds(2));

		}
	}

	// getters & setters
	public void playFadeAnimation() {

		FadeTransition ft = new FadeTransition(Duration.seconds(0.5),
				this.getEntity().getViewComponent().getChildren().get(0));
		ft.setFromValue(1);
		ft.setToValue(0);
		ft.setAutoReverse(true);
		ft.setCycleCount(4);
		ft.play();

	}

	public int getVidas() {
		return vidas;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPower() {
		return power;
	}

}
