package dad.javafx.bomberdad;

import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.GameView;
import com.almasb.fxgl.app.MenuType;
import com.almasb.fxgl.app.PauseMenu;
import com.almasb.fxgl.app.SceneFactory;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.text.TextLevelLoader;
import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.views.ScrollingBackgroundView;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.texture.Texture;

import dad.javafx.bomberdad.components.PlayerComponent;
import dad.javafx.bomberdad.menu.CustomMenu;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import static com.almasb.fxgl.dsl.FXGL.*;

public class BombermanApp extends GameApplication {

	public static final int TILE_SIZE = 30;

	private Entity player, player2;
	private PlayerComponent playerComponent, playerComponent2;
	private int lvl = 0;
	private boolean requestNewGame = false;

	@Override
	protected void initSettings(GameSettings settings) {
		settings.setTitle("BomberDAD");
		settings.setVersion("0.1");
		settings.setWidth(TILE_SIZE * 19);
		settings.setHeight(TILE_SIZE * 19);
		
		settings.setMenuEnabled(false);
        settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new CustomMenu(MenuType.MAIN_MENU);
            }
            
            @Override
            public PauseMenu newPauseMenu() {
            	return super.newPauseMenu();
            }
        });
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
		GenerateMap.newMap(lvl);
		getGameWorld().addEntityFactory(new BombermanFactory());

//		getGameWorld().spawn("f");
		Texture texture = getAssetLoader().loadTexture("floor.png");
		ScrollingBackgroundView bg = new ScrollingBackgroundView(texture, Orientation.HORIZONTAL);
		GameView vista= new GameView(bg, 0);
		getGameScene().addGameView(vista);

		Level level = getAssetLoader().loadLevel("map.txt", new TextLevelLoader(TILE_SIZE, TILE_SIZE, '0'));
		getGameWorld().setLevel(level);

		player = getGameWorld().spawn("Player", TILE_SIZE, TILE_SIZE);
		player2 = getGameWorld().spawn("Player", TILE_SIZE * 17, TILE_SIZE * 17);
		playerComponent = player.getComponent(PlayerComponent.class);
		playerComponent.setName("Player");
		playerComponent2 = player2.getComponent(PlayerComponent.class);
		playerComponent2.setName("Player2");
	}
	

	@Override
	protected void initPhysics() {
		getPhysicsWorld().addCollisionHandler(new CollisionHandler(BombermanType.PLAYER, BombermanType.UPMAXBOMBS) {
			@Override
			protected void onCollisionBegin(Entity pl, Entity powerup) {
				powerup.removeFromWorld();
				pl.getComponent(PlayerComponent.class).increaseMaxBombs();
			}
		});
		getPhysicsWorld().addCollisionHandler(new CollisionHandler(BombermanType.PLAYER, BombermanType.UPPOWER) {
			@Override
			protected void onCollisionBegin(Entity pl, Entity powerup) {
				powerup.removeFromWorld();
				pl.getComponent(PlayerComponent.class).increasePower();
			}
		});
	}
	
	private void levelUp() {
		if (lvl == 4) {
			lvl = 0;
		} else {
			lvl++;
		}
		requestNewGame = true;
	}
	
	 @Override
	    protected void onUpdate(double tpf) {
	        if (requestNewGame) {
	            requestNewGame = false;
	            getGameController().startNewGame();
	        }
	    }

	public void onDestroyed(Entity e) {
		if (e.isType(BombermanType.PLAYER)) {
			PlayerComponent playerHit = e.getComponent(PlayerComponent.class);
			if (playerHit.getVidas() == 0) {
				e.setPosition(new Point2D(TILE_SIZE * 22, TILE_SIZE * 22));
				e.removeFromWorld();
				levelUp();
			} else {
				playerHit.setVidas(playerHit.getVidas()-1);
				if (playerHit.getName().equals("Player")) {
					e.setPosition(new Point2D(TILE_SIZE, TILE_SIZE));
					playerHit.resetMaxBombs();
				} else {
					e.setPosition(new Point2D(TILE_SIZE * 17, TILE_SIZE * 17));
					playerHit.resetMaxBombs();
				}
			}
		} else if(e.isType(BombermanType.BRICK)) {
			
			e.removeFromWorld();
			Entity f=getGameWorld().spawn("f", e.getX(), e.getY());
			f.getViewComponent().setOpacity(0);

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