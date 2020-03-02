package dad.javafx.bomberdad;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.geto;
import static com.almasb.fxgl.dsl.FXGL.texture;

import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
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
import dad.javafx.bomberdad.components.StaticComponent;
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

	@Spawns("f")
	public Entity newBackground(SpawnData data) {
		return FXGL.entityBuilder().type(BombermanType.FLOOR).from(data).opacity(0)
				.viewWithBBox(FXGL.getAssetLoader().loadTexture("bg" + theme + ".gif", BombermanApp.TILE_SIZE,
						BombermanApp.TILE_SIZE))
				.with(new CellMoveComponent(30, 30, 0))
				.with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid")))).build();
	}

	@Spawns("w")
	public Entity newWall(SpawnData data) {
		return FXGL.entityBuilder().type(BombermanType.WALL).from(data).viewWithBBox(FXGL.getAssetLoader()
				.loadTexture("rock" + theme + ".png", BombermanApp.TILE_SIZE, BombermanApp.TILE_SIZE)).build();
	}

	@Spawns("b")
	public Entity newBrick(SpawnData data) {
		return FXGL.entityBuilder().type(BombermanType.BRICK).from(data)
				.viewWithBBox(FXGL.getAssetLoader().loadTexture("brick" + theme + ".png", BombermanApp.TILE_SIZE,
						BombermanApp.TILE_SIZE))
				.with(new CellMoveComponent(30, 30, 0))
				.with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid")))).build();
	}

	@Spawns("r")
	public Entity newBrickRed(SpawnData data) {
		return FXGL.entityBuilder().type(BombermanType.BRICKRED).from(data)
				.viewWithBBox(FXGL.getAssetLoader().loadTexture("brick" + theme + ".png", BombermanApp.TILE_SIZE,
						BombermanApp.TILE_SIZE))
				.with(new CellMoveComponent(30, 30, 0))
				.with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid")))).build();
	}

	@Spawns("y")
	public Entity newBrickYellow(SpawnData data) {
		return FXGL.entityBuilder().type(BombermanType.BRICKYELLOW).from(data)
				.viewWithBBox(FXGL.getAssetLoader().loadTexture("brick" + theme + ".png", BombermanApp.TILE_SIZE,
						BombermanApp.TILE_SIZE))
				.with(new CellMoveComponent(30, 30, 0))
				.with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid")))).build();
	}

//OLD PLAYER
//    @Spawns("Player")
//    public Entity newPlayer(SpawnData data) {
//        return FXGL.entityBuilder()
//                .type(BombermanType.PLAYER)
//                .from(data)
//                .viewWithBBox(new Rectangle(BombermanApp.TILE_SIZE, BombermanApp.TILE_SIZE, Color.BLUE))
//                .with(new CollidableComponent(true))
//                .with(new PlayerComponent())
//                .build();
//    }
	// PRUEBAS
	@Spawns("Player")
	public Entity newPlayer(SpawnData data) {
		Entity e = entityBuilder().from(data).type(BombermanType.PLAYER)
				.bbox(new HitBox(new Point2D(2, 2), BoundingShape.box(30, 30)))
				.viewWithBBox(FXGL.getAssetLoader().loadTexture("py" + theme + ".gif", BombermanApp.TILE_SIZE,
						BombermanApp.TILE_SIZE))
				.with(new CollidableComponent(true)).with(new CellMoveComponent(30, 30, 175))
				.with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid")))).with(new PlayerComponent()).build();

//        e.getTransformComponent().setRotationOrigin(new Point2D(35 / 2.0, 40 / 2.0));
		e.getTransformComponent().setScaleOrigin(new Point2D(0, 0));

		return e;
	}

	@Spawns("Bomb")
	public Entity newBomb(SpawnData data) {
		Entity bombBuilder = entityBuilder().type(BombermanType.BOMB).from(data)
				.viewWithBBox(texture("bomb.png").toAnimatedTexture(14, Duration.seconds(2.0)).play())
				.with(new CellMoveComponent(30, 30, 0))
				.with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid"))))
				.with(new BombComponent(data.get("radius"))).build();

		return bombBuilder;
	}

	@Spawns("PUMaxBombs")
	public Entity newMaxBomsUp(SpawnData data) {
		return FXGL.entityBuilder().type(BombermanType.UPMAXBOMBS).from(data)
				.viewWithBBox(texture("MoreBombsBottle.gif")).with(new CollidableComponent(true)).build();
	}

	@Spawns("PUPower")
	public Entity newPower(SpawnData data) {
		return FXGL.entityBuilder().type(BombermanType.UPPOWER).from(data).viewWithBBox(texture("PowerBottle.gif"))
				.with(new CollidableComponent(true)).build();
	}

	@Spawns("explosion")
	public Entity newExplosion(SpawnData data) {
		return FXGL.entityBuilder().type(BombermanType.EXPLOSION).from(data)
				.view(texture("explosion" + BombermanApp.TILE_SIZE + ".png")
						.toAnimatedTexture(16, Duration.seconds(0.66)).play())
				.with(new ExpireCleanComponent(Duration.seconds(0.66))).build();
	}

	//////
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

	@Spawns("s")
	public Entity newStaticEnemy(SpawnData data) {
		Entity enemy = entityBuilder().from(data).type(BombermanType.ENEMY)
				.bbox(new HitBox(new Point2D(2, 2), BoundingShape.box(30, 30)))
				.viewWithBBox(new Rectangle(BombermanApp.TILE_SIZE, BombermanApp.TILE_SIZE, Color.DARKRED))
				.with(new StaticComponent()).build();

		enemy.getTransformComponent().setScaleOrigin(new Point2D(0, 0));

		return enemy;
	}

	@Spawns("Bullet")
	public Entity newBullet(SpawnData data) {
		Point2D dir = data.get("dir");
		return entityBuilder().from(data).type(BombermanType.BULLET).viewWithBBox("bala.png")
				.with(new ProjectileComponent(dir, 50)).build();

	}
}
