package dad.javafx.bomberdad;

import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.physics.BoundingShape;
//import com.sun.javafx.geom.Point2D;

import dad.javafx.bomberdad.components.BombComponent;
import dad.javafx.bomberdad.components.EnemyComponent;
import dad.javafx.bomberdad.components.PlayerComponent;

//----
import dad.javafx.bomberdad.ia.ChasePlayer;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.entity.*;
import javafx.geometry.Point2D;
//---
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import static com.almasb.fxgl.dsl.FXGL.*;


public class BombermanFactory implements EntityFactory {
	
	private String theme = "crab";
	
	public BombermanFactory(String theme) {
		this.theme = theme;
	}
	
    @Spawns("f")
    public Entity newBackground(SpawnData data) {
        return FXGL.entityBuilder()
        		.type(BombermanType.FLOOR)
                .from(data)
                .opacity(0)
                .viewWithBBox(FXGL.getAssetLoader().loadTexture("bg"+theme+".gif", BombermanApp.TILE_SIZE, BombermanApp.TILE_SIZE))
                .build();
    }

    @Spawns("w")
    public Entity newWall(SpawnData data) {
        return FXGL.entityBuilder()
                .type(BombermanType.WALL)
                .from(data)
                .viewWithBBox(FXGL.getAssetLoader().loadTexture("rock"+theme+".png", BombermanApp.TILE_SIZE, BombermanApp.TILE_SIZE))
                .build();
    }

    @Spawns("b")
    public Entity newBrick(SpawnData data) {
        return FXGL.entityBuilder()
                .type(BombermanType.BRICK)
                .from(data)
                .viewWithBBox(FXGL.getAssetLoader().loadTexture("brick"+theme+".png", BombermanApp.TILE_SIZE, BombermanApp.TILE_SIZE))
                .with(new CellMoveComponent(30, 30,0))
                .with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid"))))
                .build();
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
  //  PRUEBAS
    @Spawns("Player")
    public Entity newPlayer(SpawnData data) {
      Entity e = entityBuilder()
                .from(data)
                .type(BombermanType.PLAYER)
                .bbox(new HitBox(new Point2D(2, 2), BoundingShape.box(30, 30)))
                .viewWithBBox(FXGL.getAssetLoader().loadTexture("py"+theme+".gif", BombermanApp.TILE_SIZE, BombermanApp.TILE_SIZE))
                .with(new CollidableComponent(true))
                .with(new CellMoveComponent(30, 30, 150))
                .with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid"))))
                .with(new PlayerComponent())
                .build();

//        e.getTransformComponent().setRotationOrigin(new Point2D(35 / 2.0, 40 / 2.0));
      	e.getTransformComponent().setScaleOrigin(new Point2D(0, 0));
   

        return e;
    }
    

    @Spawns("Bomb")
    public Entity newBomb(SpawnData data) {
        return FXGL.entityBuilder()
                .type(BombermanType.BOMB)
                .from(data)
                .viewWithBBox(texture("bomb.png").toAnimatedTexture(14, Duration.seconds(2.0)).play())
                .with(new BombComponent(data.get("radius")))
                .build();
    }

    @Spawns("PUMaxBombs")
    public Entity newMaxBomsUp(SpawnData data) {
        return FXGL.entityBuilder()
                .type(BombermanType.UPMAXBOMBS)
                .from(data)
                .viewWithBBox(texture("MoreBombsBottle.gif"))
                .with(new CollidableComponent(true))
                .build();
    }
    
    @Spawns("PUPower")
    public Entity newPower(SpawnData data) {
        return FXGL.entityBuilder()
                .type(BombermanType.UPPOWER)
                .from(data)
                .viewWithBBox(texture("PowerBottle.gif"))
                .with(new CollidableComponent(true))
                .build();
    }
    
    @Spawns("explosion")
    public Entity newExplosion(SpawnData data) {
        return FXGL.entityBuilder()
                .type(BombermanType.EXPLOSION)
                .from(data)
                .view(texture("explosion"+BombermanApp.TILE_SIZE+".png").toAnimatedTexture(16, Duration.seconds(0.66)).play())
                .with(new ExpireCleanComponent(Duration.seconds(0.66)))
                .build();
    }
 
    //////
    @Spawns("e")
    public Entity newEnemyPrueba(SpawnData data) {
        Entity enemy = entityBuilder()
                .from(data)
                .type(BombermanType.ENEMY)
                .bbox(new HitBox(new Point2D(2, 2), BoundingShape.box(30, 30)))
                .viewWithBBox(new Rectangle(BombermanApp.TILE_SIZE, BombermanApp.TILE_SIZE, Color.DARKRED))
                .with(new CellMoveComponent(30, 30, 150))
                .with(new AStarMoveComponent(new LazyValue<>(() -> geto("grid"))))
                .with(new EnemyComponent())
                .with(new ChasePlayer())
                .build();

        enemy.getTransformComponent().setScaleOrigin(new Point2D(0, 0));

        return enemy;
    }
}
