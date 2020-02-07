package dad.javafx.bomberdad.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.TransformComponent;
import com.almasb.fxgl.entity.components.TypeComponent;
import dad.javafx.bomberdad.BombermanApp;
import dad.javafx.bomberdad.BombermanType;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class EnemyComponent extends Component {

	public TransformComponent position;

	private int maxBombs = 1;
	public int bombsPlaced = 0;
	private int vidas = 1;
	private String name;
	private int power = 0;

	public void increaseMaxBombs() {
		maxBombs++;
	}
	
	public void resetMaxBombs() {
		maxBombs = 1;
	}
	
	public void increasePower() {
		power = power + BombermanApp.TILE_SIZE;
	}
	
	public void resetPower() {
		power = 0;
	}

	public void placeBomb() {
		if (vidas >= 0) {
			if (bombsPlaced == maxBombs) {
				return;
			}

			bombsPlaced++;

			// TODO: double check
			int x = (int) position.getX() / BombermanApp.TILE_SIZE;
			int y = (int) position.getY() / BombermanApp.TILE_SIZE;
			Entity bomb = FXGL.getGameWorld().spawn("Bomb",
					new SpawnData(x * BombermanApp.TILE_SIZE, y * BombermanApp.TILE_SIZE).put("radius", (BombermanApp.TILE_SIZE / 2)+power));

			FXGL.getGameTimer().runOnceAfter(() -> {
				bomb.getComponent(BombComponent.class).explode(power);
				bombsPlaced--;
			}, Duration.seconds(2));
		}
	}

	public void moveRight() {
		if (vidas >= 0) {
			if (canMove(new Point2D(BombermanApp.TILE_SIZE, 0)))
				position.translateX(BombermanApp.TILE_SIZE);
		}
	}

	public void moveLeft() {
		if (vidas >= 0) {
			if (canMove(new Point2D(-BombermanApp.TILE_SIZE, 0)))
				position.translateX(-BombermanApp.TILE_SIZE);
		}
	}

	@Override
	public void onUpdate(double tpf) {
		// TODO Auto-generated method stub
		super.onUpdate(tpf);
		if (position.getX() == BombermanApp.player.getPosition().getX() && position.getY() == BombermanApp.player.getPosition().getY() 
				|| position.getX() == BombermanApp.player2.getPosition().getX() && position.getY() == BombermanApp.player2.getPosition().getY() 
				&& bombsPlaced == 0 ) {
			placeBomb();
		}
//		if (bombsPlaced == 0) {
//			placeBomb();
//		}
	}

	
	public void moveUp() {
		if (vidas >= 0) {
			if (canMove(new Point2D(0, -BombermanApp.TILE_SIZE)))
				position.translateY(-BombermanApp.TILE_SIZE);
		}
	}

	public void moveDown() {
		if (vidas >= 0) {
			if (canMove(new Point2D(0, BombermanApp.TILE_SIZE)))
				position.translateY(BombermanApp.TILE_SIZE);
		}
	}

	public boolean canMove(Point2D direction) {
		Point2D newPosition = position.getPosition().add(direction);

		return FXGL.getGameScene().getViewport().getVisibleArea().contains(newPosition)

				&&

				FXGL.getGameWorld().getEntitiesAt(newPosition).stream().filter(e -> e.hasComponent(TypeComponent.class))
						.map(e -> e.getComponent(TypeComponent.class))
						.noneMatch(type -> type.isType(BombermanType.BRICK) || type.isType(BombermanType.WALL)
								|| type.isType(BombermanType.BOMB));
	}

	// getters & setters
	
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

}
