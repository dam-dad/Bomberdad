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
import javafx.geometry.Point2D;
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
		settings.setMenuEnabled(false);
		settings.setWidth(TILE_SIZE * 15);
		settings.setHeight(TILE_SIZE * 15);
	}

	@Override
	protected void initInput() {
		getInput().addAction(new UserAction("Move Up") {
			@Override
			protected void onActionBegin() {
				playerComponent.moveUp();
			}
		}, KeyCode.W);

		getInput().addAction(new UserAction("Move Left") {
			@Override
			protected void onActionBegin() {
				playerComponent.moveLeft();
			}
		}, KeyCode.A);

		getInput().addAction(new UserAction("Move Down") {
			@Override
			protected void onActionBegin() {
				playerComponent.moveDown();
			}
		}, KeyCode.S);

		getInput().addAction(new UserAction("Move Right") {
			@Override
			protected void onActionBegin() {
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

		player = getGameWorld().spawn("Player", 0, 0);
		player2 = getGameWorld().spawn("Player", TILE_SIZE * 14, TILE_SIZE * 14);
		playerComponent = player.getComponent(PlayerComponent.class);
		playerComponent.setName("Player");
		playerComponent.setVidas(3);;
		playerComponent2 = player2.getComponent(PlayerComponent.class);
		playerComponent2.setName("Player2");;
	}

	@Override
	protected void initPhysics() {
		getPhysicsWorld().addCollisionHandler(new CollisionHandler(BombermanType.PLAYER, BombermanType.UPMAXBOMBS) {
			@Override
			protected void onCollisionBegin(Entity pl, Entity powerup) {
				powerup.removeFromWorld();
				playerComponent.increaseMaxBombs();
			}
		});
		getPhysicsWorld().addCollisionHandler(new CollisionHandler(BombermanType.PLAYER, BombermanType.UPPOWER) {
			@Override
			protected void onCollisionBegin(Entity pl, Entity powerup) {
				powerup.removeFromWorld();
				playerComponent.increasePower();
			}
		});
	}

	public void onDestroyed(Entity e) {
		if (e.isType(BombermanType.PLAYER)) {
			PlayerComponent playerHit = e.getComponent(PlayerComponent.class);
			playerHit.setVidas(playerHit.getVidas() - 1);
			if (playerHit.getVidas() == 0) {
				e.setPosition(new Point2D(TILE_SIZE * 16, TILE_SIZE * 16));
				e.removeFromWorld();
			} else {
				if (playerHit.getName().equals("Player")) {
					e.setPosition(new Point2D(0, 0));
					playerHit.resetMaxBombs();
				} else {
					e.setPosition(new Point2D(TILE_SIZE * 14, TILE_SIZE * 14));
					playerHit.resetMaxBombs();
				}
			}
		} else {
			e.removeFromWorld();
			if (FXGLMath.randomBoolean()) {
		            int x = (int) e.getPosition().getX();
		            int y = (int) e.getPosition().getY();

		            if (FXGLMath.randomBoolean()) {
			            getGameWorld().spawn("PUMaxBombs", x, y);
		            }else {
		            	getGameWorld().spawn("PUPower", x, y);
		            }
			}
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}