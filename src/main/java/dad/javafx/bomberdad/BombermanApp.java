package dad.javafx.bomberdad;

import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.text.TextLevelLoader;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import dad.javafx.bomberdad.components.PlayerComponent;
import javafx.scene.input.KeyCode;
import static com.almasb.fxgl.dsl.FXGL.*;

public class BombermanApp extends GameApplication {

    public static final int TILE_SIZE = 40;

    private Entity player, player2;
    private PlayerComponent playerComponent, playerComponent2;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("BomberDAD");
        settings.setVersion("0.1");
        settings.setMenuEnabled(true);
        settings.setWidth(TILE_SIZE*15);
        settings.setHeight(TILE_SIZE*15);
    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                playerComponent.moveUp();
            }
        }, KeyCode.W);

        getInput().addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                playerComponent.moveLeft();
            }
        }, KeyCode.A);

        getInput().addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                playerComponent.moveDown();
            }
        }, KeyCode.S);

        getInput().addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                playerComponent.moveRight();
            }
        }, KeyCode.D);

        getInput().addAction(new UserAction("Place Bomb") {
            @Override
            protected void onActionBegin() {
                playerComponent.placeBomb();
            }
        }, KeyCode.SPACE);
        
        getInput().addAction(new UserAction("Move Up2") {
            @Override
            protected void onActionBegin() {
                playerComponent2.moveUp();
            }
        }, KeyCode.UP);

        getInput().addAction(new UserAction("Move Left2") {
            @Override
            protected void onActionBegin() {
                playerComponent2.moveLeft();
            }
        }, KeyCode.LEFT);

        getInput().addAction(new UserAction("Move Down2") {
            @Override
            protected void onActionBegin() {
                playerComponent2.moveDown();
            }
        }, KeyCode.DOWN);

        getInput().addAction(new UserAction("Move Right2") {
            @Override
            protected void onActionBegin() {
                playerComponent2.moveRight();
            }
        }, KeyCode.RIGHT);

        getInput().addAction(new UserAction("Place Bomb2S") {
            @Override
            protected void onActionBegin() {
                playerComponent2.placeBomb();
            }
        }, KeyCode.ENTER);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new BombermanFactory());

        getGameWorld().spawn("f");

        Level level = getAssetLoader().loadLevel("0.txt", new TextLevelLoader(40, 40, '0'));
        getGameWorld().setLevel(level);

        player = getGameWorld().spawn("Player", 0 ,0);
        player2 = getGameWorld().spawn("Player", TILE_SIZE*14, TILE_SIZE*14);
        playerComponent = player.getComponent(PlayerComponent.class);
        playerComponent2 = player2.getComponent(PlayerComponent.class);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(BombermanType.PLAYER, BombermanType.POWERUP) {
            @Override
            protected void onCollisionBegin(Entity pl, Entity powerup) {
                powerup.removeFromWorld();
                playerComponent.increaseMaxBombs();
            }
        });
    }


	public void onWallDestroyed(Entity e) {
        if (FXGLMath.randomBoolean()) {
            // TODO:
//            int x = wall.getPositionComponent().getGridX(BombermanApp.TILE_SIZE);
//            int y = wall.getPositionComponent().getGridY(BombermanApp.TILE_SIZE);
//
//            getGameWorld().spawn("Powerup", x*40, y*40);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}