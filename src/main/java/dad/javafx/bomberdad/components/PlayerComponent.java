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

public class PlayerComponent extends Component {

	private TransformComponent position;

	private int maxBombs = 1;
	private int bombsPlaced = 0;
	private int vidas = 3;
	private String name;

	public void increaseMaxBombs() {
		maxBombs++;
	}

	public void placeBomb() {
		if (bombsPlaced == maxBombs) {
			return;
		}

		bombsPlaced++;

		// TODO: double check
		int x = (int) position.getX() / BombermanApp.TILE_SIZE;
		int y = (int) position.getY() / BombermanApp.TILE_SIZE;

		Entity bomb = FXGL.getGameWorld().spawn("Bomb",
				new SpawnData(x * 40, y * 40).put("radius", BombermanApp.TILE_SIZE / 2));

		FXGL.getGameTimer().runOnceAfter(() -> {
			bomb.getComponent(BombComponent.class).explode();
			bombsPlaced--;
		}, Duration.seconds(2));
	}

	public void moveRight() {
		System.out.println(name);
		if (canMove(new Point2D(40, 0)))
			position.translateX(BombermanApp.TILE_SIZE);
	}

	public void moveLeft() {
		if (canMove(new Point2D(-40, 0)))
			position.translateX(-BombermanApp.TILE_SIZE);
	}

	public void moveUp() {
		if (canMove(new Point2D(0, -40)))
			position.translateY(-BombermanApp.TILE_SIZE);
	}

	public void moveDown() {
		if (canMove(new Point2D(0, 40)))
			position.translateY(BombermanApp.TILE_SIZE);
	}

	private boolean canMove(Point2D direction) {
		Point2D newPosition = position.getPosition().add(direction);

		return FXGL.getGameScene().getViewport().getVisibleArea().contains(newPosition)

				&&

				FXGL.getGameWorld().getEntitiesAt(newPosition).stream().filter(e -> e.hasComponent(TypeComponent.class))
						.map(e -> e.getComponent(TypeComponent.class))
						.noneMatch(type -> type.isType(BombermanType.BRICK) || type.isType(BombermanType.WALL)
								|| type.isType(BombermanType.BOMB));
	}
	
	//getters & setters
	
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
