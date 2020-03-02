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
	private int bombsPlaced = 0;
	private ArrayList<Entity> playerCerca;

	public void setVida(int vida) {
		this.vida = vida;
	}

	/**
	 * Permite generar una bomba en la posicion actual del enemy si tiene mas de 0 vidas
	*/
	public void placeBomb() {

		if (vida != 0) {
			int x = (int) position.getX() / BombermanApp.TILE_SIZE;
			int y = (int) position.getY() / BombermanApp.TILE_SIZE;
			Entity bomb = FXGL.getGameWorld().spawn("Bomb",
					new SpawnData(x * BombermanApp.TILE_SIZE, y * BombermanApp.TILE_SIZE).put("radius",
							(BombermanApp.TILE_SIZE / 2)));

			FXGL.getGameTimer().runOnceAfter(() -> {
				bomb.getComponent(BombComponent.class).explode(0, null);
				bombsPlaced--;
			}, Duration.seconds(2));
		}

	}

	/**
	 * @param tpf Tiempo por frame
	 * Si en la misma posición que el enemy hay un player, pone una bomba
	*/
	@Override
	public void onUpdate(double tpf) {

		playerCerca = new ArrayList<Entity>(FXGL.getGameWorld().getEntitiesAt(this.getEntity().getPosition()));
		for (int i = 0; i < playerCerca.size(); i++) {
			if (playerCerca.get(i).isType(BombermanType.PLAYER) & bombsPlaced==0) {
				placeBomb();
				bombsPlaced++;
			}
		}

	}

}
