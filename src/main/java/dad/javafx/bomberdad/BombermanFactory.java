package dad.javafx.bomberdad;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.components.CollidableComponent;
import dad.javafx.bomberdad.components.BombComponent;
import dad.javafx.bomberdad.components.PlayerComponent;
import com.almasb.fxgl.entity.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import static com.almasb.fxgl.dsl.FXGL.*;

public class BombermanFactory implements EntityFactory {

    @Spawns("f")
    public Entity newBackground(SpawnData data) {
        return FXGL.entityBuilder()
        		.type(BombermanType.FLOOR)
                .from(data)
                .viewWithBBox(FXGL.getAssetLoader().loadTexture("floor.png", 40*15, 40*15))
                .build();
    }

    @Spawns("w")
    public Entity newWall(SpawnData data) {
        return FXGL.entityBuilder()
                .type(BombermanType.WALL)
                .from(data)
                .viewWithBBox(FXGL.getAssetLoader().loadTexture("wall.png", 40, 40))
                .build();
    }

    @Spawns("b")
    public Entity newBrick(SpawnData data) {
        return FXGL.entityBuilder()
                .type(BombermanType.BRICK)
                .from(data)
                .viewWithBBox(FXGL.getAssetLoader().loadTexture("brick.png", 40, 40))
                .build();
    }

    @Spawns("Player")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder()
                .type(BombermanType.PLAYER)
                .from(data)
                .viewWithBBox(new Rectangle(BombermanApp.TILE_SIZE, BombermanApp.TILE_SIZE, Color.BLUE))
                .with(new CollidableComponent(true))
                .with(new PlayerComponent())
                .build();
    }

    @Spawns("Bomb")
    public Entity newBomb(SpawnData data) {
        return FXGL.entityBuilder()
                .type(BombermanType.BOMB)
                .from(data)
                .viewWithBBox(new Circle(BombermanApp.TILE_SIZE / 2, BombermanApp.TILE_SIZE / 2, BombermanApp.TILE_SIZE / 2, Color.BLACK))
                .with(new BombComponent(data.get("radius")))
                .build();
    }

    @Spawns("PUMaxBombs")
    public Entity newMaxBomsUp(SpawnData data) {
        return FXGL.entityBuilder()
                .type(BombermanType.UPMAXBOMBS)
                .from(data)
                .viewWithBBox(new Rectangle(BombermanApp.TILE_SIZE, BombermanApp.TILE_SIZE, Color.YELLOW))
                .with(new CollidableComponent(true))
                .build();
    }
    
    @Spawns("PUPower")
    public Entity newPower(SpawnData data) {
        return FXGL.entityBuilder()
                .type(BombermanType.UPPOWER)
                .from(data)
                .viewWithBBox(new Rectangle(BombermanApp.TILE_SIZE, BombermanApp.TILE_SIZE, Color.RED))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("explosion")
    public Entity newExplosion(SpawnData data) {
        return FXGL.entityBuilder()
                .type(BombermanType.EXPLOSION)
                .from(data)
                .view(texture("explosion.png").toAnimatedTexture(16, Duration.seconds(0.66)).play())
                .with(new ExpireCleanComponent(Duration.seconds(0.66)))
                .build();
    }
}
