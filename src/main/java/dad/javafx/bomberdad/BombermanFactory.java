package dad.javafx.bomberdad;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.geto;
import static com.almasb.fxgl.dsl.FXGL.texture;

import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.physics.BoundingShape;
//import com.sun.javafx.geom.Point2D;
import com.almasb.fxgl.physics.HitBox;

import dad.javafx.bomberdad.components.BombComponent;
import dad.javafx.bomberdad.components.EnemyComponent;
import dad.javafx.bomberdad.components.PlayerComponent;
import dad.javafx.bomberdad.ia.AvoidBombs;
import dad.javafx.bomberdad.ia.ChasePlayer;
//----
import dad.javafx.bomberdad.ia.DefendZone;
import javafx.geometry.Point2D;
//---
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class BombermanFactory implements EntityFactory {

	private String theme = "crab";

	public BombermanFactory(String theme) {
		this.theme = theme;
	}

	/**
	 * Entidad del suelo y su textura
	 */
	@Spawns("f")
	public Entity newBackground(SpawnData data) {
		return FXGL.entityBuilder().type(BombermanType.FLOOR).from(data).opacity(0)
				.viewWithBBox(FXGL.getAssetLoader().loadTexture("bg" + theme + ".gif", BombermanApp.TILE_SIZE,
						BombermanApp.TILE_SIZE))
				.with(new CellMoveComponent(30, 30, 0))
				.with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid")))).build();
	}

	/**
	 * Entidad del muro y su textura
	 */
	@Spawns("w")
	public Entity newWall(SpawnData data) {
		return FXGL.entityBuilder().type(BombermanType.WALL).from(data).viewWithBBox(FXGL.getAssetLoader()
				.loadTexture("rock" + theme + ".png", BombermanApp.TILE_SIZE, BombermanApp.TILE_SIZE)).build();
	}

	/**
	 * Entidad del bloque y su textura
	 */
	@Spawns("b")
	public Entity newBrick(SpawnData data) {
		return FXGL.entityBuilder().type(BombermanType.BRICK).from(data)
				.viewWithBBox(FXGL.getAssetLoader().loadTexture("brick" + theme + ".png", BombermanApp.TILE_SIZE,
						BombermanApp.TILE_SIZE))
				.with(new CellMoveComponent(30, 30, 0))
				.with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid")))).build();
	}

	/**
	 * Entidad del bloque que genera powerup rojo y su textura
	 */
	@Spawns("r")
	public Entity newBrickRed(SpawnData data) {
		return FXGL.entityBuilder().type(BombermanType.BRICKRED).from(data)
				.viewWithBBox(FXGL.getAssetLoader().loadTexture("brick" + theme + ".png", BombermanApp.TILE_SIZE,
						BombermanApp.TILE_SIZE))
				.with(new CellMoveComponent(30, 30, 0))
				.with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid")))).build();
	}

	/**
	 * Entidad del bloque que genera un powerup amarillo y su textura
	 */
	@Spawns("y")
	public Entity newBrickYellow(SpawnData data) {
		return FXGL.entityBuilder().type(BombermanType.BRICKYELLOW).from(data)
				.viewWithBBox(FXGL.getAssetLoader().loadTexture("brick" + theme + ".png", BombermanApp.TILE_SIZE,
						BombermanApp.TILE_SIZE))
				.with(new CellMoveComponent(30, 30, 0))
				.with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid")))).build();
	}

	/**
	 * Entidad del jugador y su textura
	 */
	@Spawns("Player")
	public Entity newPlayer(SpawnData data) {
		Entity e = entityBuilder().from(data).type(BombermanType.PLAYER)
				.bbox(new HitBox(new Point2D(2, 2), BoundingShape.box(30, 30)))
				.viewWithBBox(FXGL.getAssetLoader().loadTexture("py" + theme + ".gif", BombermanApp.TILE_SIZE,
						BombermanApp.TILE_SIZE))
				.with(new CollidableComponent(true)).with(new CellMoveComponent(30, 30, 175))
				.with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid")))).with(new PlayerComponent()).build();
		e.getTransformComponent().setScaleOrigin(new Point2D(0, 0));

		return e;
	}

	/**
	 * Entidad de la bomba y su textura
	 */
	@Spawns("Bomb")
	public Entity newBomb(SpawnData data) {
		Entity bombBuilder = entityBuilder().type(BombermanType.BOMB).from(data)
				.viewWithBBox(texture("bomb.png").toAnimatedTexture(14, Duration.seconds(2.0)).play())
				.with(new CellMoveComponent(30, 30, 0))
				.with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid"))))
				.with(new BombComponent(data.get("radius"))).build();

		return bombBuilder;
	}

	/**
	 * Entidad del powerup de maximo de bombas y su textura
	 */
	@Spawns("PUMaxBombs")
	public Entity newMaxBomsUp(SpawnData data) {
		return FXGL.entityBuilder().type(BombermanType.UPMAXBOMBS).from(data)
				.viewWithBBox(texture("MoreBombsBottle.gif")).with(new CollidableComponent(true)).build();
	}

	/**
	 * Entidad del power de aumento de radio de explosión y su textura
	 */
	@Spawns("PUPower")
	public Entity newPower(SpawnData data) {
		return FXGL.entityBuilder().type(BombermanType.UPPOWER).from(data).viewWithBBox(texture("PowerBottle.gif"))
				.with(new CollidableComponent(true)).build();
	}

	/**
	 * Entidad de la explosión y su textura
	 */
	@Spawns("explosion")
	public Entity newExplosion(SpawnData data) {
		return FXGL.entityBuilder().type(BombermanType.EXPLOSION).from(data)
				.view(texture("explosion" + BombermanApp.TILE_SIZE + ".png")
						.toAnimatedTexture(16, Duration.seconds(0.66)).play())
				.with(new ExpireCleanComponent(Duration.seconds(0.66))).build();
	}

	/**
	 * Entidad del enemy que persigue y su textura
	 */
	@Spawns("e")
	public Entity newEnemy(SpawnData data) {
		Entity enemy = entityBuilder().from(data).type(BombermanType.ENEMY)
				.bbox(new HitBox(new Point2D(2, 2), BoundingShape.box(30, 30)))
				.viewWithBBox(new Rectangle(BombermanApp.TILE_SIZE, BombermanApp.TILE_SIZE, Color.DARKRED))
				.with(new CellMoveComponent(30, 30, 125))
				.with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid")))).with(new EnemyComponent())
				.with(new ChasePlayer()).with(new AvoidBombs()).build();

		enemy.getTransformComponent().setScaleOrigin(new Point2D(0, 0));

		return enemy;
	}

	/**
	 * Entidad del enemy que defiende una zona y su textura
	 */
	@Spawns("d")
	public Entity newEnemyZone(SpawnData data) {
		Entity enemy = entityBuilder().from(data).type(BombermanType.ENEMY)
				.bbox(new HitBox(new Point2D(2, 2), BoundingShape.box(30, 30)))
				.viewWithBBox(new Rectangle(BombermanApp.TILE_SIZE, BombermanApp.TILE_SIZE, Color.DARKRED))
				.with(new CellMoveComponent(30, 30, 125))
				.with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid")))).with(new EnemyComponent())
				.with(new DefendZone()).build();

		enemy.getTransformComponent().setScaleOrigin(new Point2D(0, 0));

		return enemy;
	}
}
