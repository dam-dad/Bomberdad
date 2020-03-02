package dad.javafx.bomberdad.components;

import java.util.ArrayList;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.entity.components.TransformComponent;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;

import dad.javafx.bomberdad.BombermanApp;
import dad.javafx.bomberdad.BombermanType;
import javafx.util.Duration;

@Required(AStarMoveComponent.class)
public class EnemyComponent extends Component {

	public TransformComponent position;
	private int vida = 1;
	private ArrayList<Entity> playerCerca;

	public void setVida(int vida) {
		this.vida = vida;
	}

	public void placeBomb() {

		if (vida != 0) {
			int x = (int) position.getX() / BombermanApp.TILE_SIZE;
			int y = (int) position.getY() / BombermanApp.TILE_SIZE;
			Entity bomb = FXGL.getGameWorld().spawn("Bomb",
					new SpawnData(x * BombermanApp.TILE_SIZE, y * BombermanApp.TILE_SIZE).put("radius",
							(BombermanApp.TILE_SIZE / 2)));

			FXGL.getGameTimer().runOnceAfter(() -> {
				bomb.getComponent(BombComponent.class).explode(0);
			}, Duration.seconds(2));
		}

	}

	@Override
	public void onUpdate(double tpf) {

		playerCerca = new ArrayList<Entity>(FXGL.getGameWorld().getEntitiesAt(this.getEntity().getPosition()));
		for (int i = 0; i < playerCerca.size(); i++) {
			if (playerCerca.get(i).isType(BombermanType.PLAYER)) {
				placeBomb();
			}
		}

	}

}
