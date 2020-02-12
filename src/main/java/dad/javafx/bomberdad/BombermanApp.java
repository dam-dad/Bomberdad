package dad.javafx.bomberdad;

import static com.almasb.fxgl.dsl.FXGL.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGL.getGameController;
import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGL.getPhysicsWorld;

import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.GameView;
import com.almasb.fxgl.app.MenuType;
import com.almasb.fxgl.app.PauseMenu;
import com.almasb.fxgl.app.SceneFactory;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.views.ScrollingBackgroundView;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.text.TextLevelLoader;
import com.almasb.fxgl.event.EventBus;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.saving.DataFile;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import dad.javafx.bomberdad.components.EnemyComponent;
import dad.javafx.bomberdad.components.PlayerComponent;
import dad.javafx.bomberdad.menu.CustomMenu;
import javafx.geometry.Orientation;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class BombermanApp extends GameApplication {

	public static final int TILE_SIZE = 30;

	public static Entity player,player2;
	private int lvl = 0;
	private boolean requestNewGame = false;
	private String theme = "fire";

	@Override
	protected void initSettings(GameSettings settings) {
		settings.setTitle("BomberDAD");
		settings.setVersion("0.1");



		settings.setWidth(TILE_SIZE * 19);
		settings.setHeight(TILE_SIZE * 19);

		settings.setMenuEnabled(true);

//		settings.setFullScreenAllowed(true);
//		settings.setFullScreenFromStart(true);
		settings.setSceneFactory(new SceneFactory() {
			@Override
			public FXGLMenu newMainMenu() {
				return new CustomMenu(MenuType.MAIN_MENU);
			}
			@Override
			public FXGLMenu newGameMenu() {
				return new CustomMenu(MenuType.GAME_MENU);
			}
		});
	}
	
		
	
	
	@Override
	protected void initInput() {
		getInput().addAction(new UserAction("Move Up") {
			@Override
			protected void onAction() {
//				playerComponent.up();
				player.getComponent(PlayerComponent.class).up();
			}
		}, KeyCode.W);

		getInput().addAction(new UserAction("Move Left") {
			@Override
			protected void onAction() {
				player.getComponent(PlayerComponent.class).left();;
			}
		}, KeyCode.A);

		getInput().addAction(new UserAction("Move Down") {
			@Override
			protected void onAction() {
				player.getComponent(PlayerComponent.class).down();;
			}
		}, KeyCode.S);

		getInput().addAction(new UserAction("Move Right") {
			@Override
			protected void onAction() {
				player.getComponent(PlayerComponent.class).right();
			}
		}, KeyCode.D);

		getInput().addAction(new UserAction("Place Bomb") {
			@Override
			protected void onActionBegin() {
				player.getComponent(PlayerComponent.class).placeBomb();
			}
		}, KeyCode.SPACE);
		
		getInput().addAction(new UserAction("Move Up2") {
			@Override
			protected void onAction() {
				player2.getComponent(PlayerComponent.class).up();
			}
		}, KeyCode.UP);

		getInput().addAction(new UserAction("Move Left2") {
			@Override
			protected void onAction() {
				player2.getComponent(PlayerComponent.class).left();
			}
		}, KeyCode.LEFT);

		getInput().addAction(new UserAction("Move Down2") {
			@Override
			protected void onAction() {
				player2.getComponent(PlayerComponent.class).down();
			}
		}, KeyCode.DOWN);

		getInput().addAction(new UserAction("Move Right2") {
			@Override
			protected void onAction() {
				player2.getComponent(PlayerComponent.class).right();
			}
		}, KeyCode.RIGHT);

		getInput().addAction(new UserAction("Place Bomb2S") {
			@Override
			protected void onActionBegin() {
				player2.getComponent(PlayerComponent.class).placeBomb();
			}
		}, KeyCode.ENTER);


	}

	@Override
	protected void initGame() {
		GenerateMap.newMap(lvl);
		getGameWorld().addEntityFactory(new BombermanFactory(theme));
		
//		getGameWorld().spawn("f");
		Texture texture = getAssetLoader().loadTexture("bg"+theme+".gif");
		//ScrollingBackgroundView bg = new ScrollingBackgroundView(texture, Orientation.HORIZONTAL);
		//ScrollingBackgroundView bg = new ScrollingBackgroundView(texture);

		GameView vista = new GameView(texture, 0);
		getGameScene().addGameView(vista);

		Level level = getAssetLoader().loadLevel("map.txt", new TextLevelLoader(TILE_SIZE, TILE_SIZE, '0'));
		getGameWorld().setLevel(level);
		  
		AStarGrid grid = AStarGrid.fromWorld(getGameWorld(),  19, 19, 30, 30, (type) -> {

			  if(type==BombermanType.FLOOR || type==BombermanType.ENEMY) {
				  return CellState.WALKABLE;
			  }else {
				
				  return CellState.NOT_WALKABLE;
			  }
	        });
	
	        set("grid", grid);
	        
	 
	        player = getGameWorld().spawn("Player", TILE_SIZE, TILE_SIZE);
	        player.getComponent(PlayerComponent.class).setName("Player");
			player2 = getGameWorld().spawn("Player", TILE_SIZE * 17, TILE_SIZE * 17);
			player2.getComponent(PlayerComponent.class).setName("PLayer2");

	
		
	


	}
	


	@Override
	protected void initPhysics() {
		getPhysicsWorld().addCollisionHandler(new CollisionHandler(BombermanType.PLAYER, BombermanType.UPMAXBOMBS) {
			@Override
			protected void onCollision(Entity pl, Entity powerup) {
				powerup.removeFromWorld();
				pl.getComponent(PlayerComponent.class).increaseMaxBombs();
			}
		});
		getPhysicsWorld().addCollisionHandler(new CollisionHandler(BombermanType.PLAYER, BombermanType.UPPOWER) {
			@Override
			protected void onCollision(Entity pl, Entity powerup) {
				powerup.removeFromWorld();
				pl.getComponent(PlayerComponent.class).increasePower();
			}
		});
	}

	private void levelUp() {
		if (lvl == 3) {
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
				e.removeFromWorld();
				levelUp();
			} else {
				playerHit.setVidas(playerHit.getVidas() - 1);
				if (playerHit.getName().equals("Player")) {
					
					playerHit.resetMaxBombs();
				} else {
				
					playerHit.resetMaxBombs();
				}
				e.getComponent(PlayerComponent.class).playFadeAnimation();
			}
		} else if (e.isType(BombermanType.BRICK)) {
			
			e.removeFromWorld();
			//Cambiar el estado de la entidad BRICK a "WALKABLE" cuando desaparece
			e.getComponent(AStarMoveComponent.class)
			.getGrid()
			.get(
			e.getComponent(CellMoveComponent.class).getCellX(), 
			e.getComponent(CellMoveComponent.class).getCellY())
			.setState(CellState.WALKABLE);
			
			Entity f = getGameWorld().spawn("f", e.getX(), e.getY());
			f.getViewComponent().setOpacity(0);

			if (FXGLMath.randomBoolean()) {

				int x = (int) e.getPosition().getX();
				int y = (int) e.getPosition().getY();

				if (FXGLMath.randomBoolean()) {
					getGameWorld().spawn("PUMaxBombs", x, y);
				} else {
					getGameWorld().spawn("PUPower", x, y);
				}
			}
		}else if (e.isType(BombermanType.ENEMY)) {
			e.removeFromWorld();
		} 
	}
	
	


	public static void main(String[] args) {
		launch(args);
	}
}