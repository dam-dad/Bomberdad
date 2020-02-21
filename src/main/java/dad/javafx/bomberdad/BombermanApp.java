package dad.javafx.bomberdad;

import static com.almasb.fxgl.dsl.FXGL.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGL.getGameController;
import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGL.getPhysicsWorld;

import java.io.IOException;
import java.util.Map;

import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.GameView;
import com.almasb.fxgl.app.IntroScene;
import com.almasb.fxgl.app.LoadingScene;
import com.almasb.fxgl.app.MenuType;
import com.almasb.fxgl.app.SceneFactory;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.text.TextLevelLoader;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.saving.DataFile;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.ui.UI;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import dad.javafx.bomberdad.components.PlayerComponent;
import dad.javafx.bomberdad.menu.CustomMenu;
import dad.javafx.bomberdad.menu.IntroSceneController;
import dad.javafx.bomberdad.menu.LoadingSceneController;
import dad.javafx.bomberdad.online.ClienteTCP;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;

public class BombermanApp extends GameApplication {

	public static final int TILE_SIZE = 30;

	public static Entity player, player2;
	private int lvl = 0;
	private boolean requestNewGame = false;
	public static String theme = "crab";
	private ClienteTCP cliente;
	private PlayerPosition playerPosition;
	public static boolean multiplayer = false;
	public static boolean moving = false;
	public static boolean fullScreen = false;
	public int tam = 0;
	public int tam=0;
	public int id;

	@Override
	protected void initSettings(GameSettings settings) {
		settings.setTitle("BomberDAD");
		settings.setVersion("0.1");

//		settings.setWidth(19 * TILE_SIZE);
//		settings.setHeight(19 * TILE_SIZE);
		settings.setManualResizeEnabled(true);
		settings.setMenuEnabled(true);
		settings.setIntroEnabled(true);
		settings.setFullScreenAllowed(fullScreen);
		settings.setFullScreenFromStart(fullScreen);
		settings.setSceneFactory(new SceneFactory() {
			@Override
			public FXGLMenu newMainMenu() {
				return new CustomMenu(MenuType.MAIN_MENU);
			}

			@Override
			public FXGLMenu newGameMenu() {
				return new CustomMenu(MenuType.GAME_MENU);
			}
			
			@Override
			public LoadingScene newLoadingScene() {
				return new LoadingSceneController();
			}
			
			@Override
			public IntroScene newIntro() {
				return new IntroSceneController();
			}
		});

	}

	@Override
	public void initUI() {
		getGameScene().getRoot().setTranslateX(100);
		UI ui = getAssetLoader().loadUI("BomberAppView.fxml", new BombermanAppUIController());
		ui.getRoot().setTranslateX(-100);
		getGameScene().addUI(ui);
	}

	@Override
	protected void initInput() {
		getInput().addAction(new UserAction("Move Up") {
			@Override
			protected void onAction() {
				FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(id).getComponent(PlayerComponent.class).up();
			}
		}, KeyCode.W);

		getInput().addAction(new UserAction("Move Left") {
			@Override
			protected void onAction() {
					FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(id).getComponent(PlayerComponent.class).left();
			}
		}, KeyCode.A);

		getInput().addAction(new UserAction("Move Down") {
			@Override
			protected void onAction() {
				FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(id).getComponent(PlayerComponent.class).down();
			}
		}, KeyCode.S);

		getInput().addAction(new UserAction("Move Right") {
			@Override
			protected void onAction() {
				FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(id).getComponent(PlayerComponent.class).right();
			}
		}, KeyCode.D);

		getInput().addAction(new UserAction("Place Bomb") {
			@Override
			protected void onAction() {
				FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(id).getComponent(PlayerComponent.class).placeBomb();
			}
		}, KeyCode.SPACE);

		getInput().addAction(new UserAction("Move Up2") {
			@Override
			protected void onAction() {
				if (!multiplayer)
					player2.getComponent(PlayerComponent.class).up();
			}
		}, KeyCode.UP);

		getInput().addAction(new UserAction("Move Left2") {
			@Override
			protected void onAction() {
				if (!multiplayer)
					player2.getComponent(PlayerComponent.class).left();
			}
		}, KeyCode.LEFT);

		getInput().addAction(new UserAction("Move Down2") {
			@Override
			protected void onAction() {
				if (!multiplayer)
					player2.getComponent(PlayerComponent.class).down();
			}
		}, KeyCode.DOWN);

		getInput().addAction(new UserAction("Move Right2") {
			@Override
			protected void onAction() {
				if (!multiplayer)
					player2.getComponent(PlayerComponent.class).right();
			}
		}, KeyCode.RIGHT);

		getInput().addAction(new UserAction("Place Bomb2S") {
			@Override
			protected void onActionBegin() {
				if (!multiplayer)
					player2.getComponent(PlayerComponent.class).placeBomb();
			}
		}, KeyCode.ENTER);
	}

	@Override
	public void initGame() {
		GenerateMap.newMap(lvl);
		getGameWorld().addEntityFactory(new BombermanFactory(theme));
		Texture texture = getAssetLoader().loadTexture("bg" + theme + ".gif");
		// ScrollingBackgroundView bg = new ScrollingBackgroundView(texture,
		// Orientation.HORIZONTAL);
		// ScrollingBackgroundView bg = new ScrollingBackgroundView(texture);

		GameView vista = new GameView(texture, 0);
		getGameScene().addGameView(vista);

		Level level = getAssetLoader().loadLevel("map.txt", new TextLevelLoader(TILE_SIZE, TILE_SIZE, '0'));
		getGameWorld().setLevel(level);

		AStarGrid grid = AStarGrid.fromWorld(getGameWorld(), 19, 19, 30, 30, (type) -> {

			if (type == BombermanType.FLOOR || type == BombermanType.ENEMY) {
				return CellState.WALKABLE;
			} else {
				return CellState.NOT_WALKABLE;
			}
		});

		set("grid", grid);
		player = getGameWorld().spawn("Player", TILE_SIZE, TILE_SIZE);
		player.getComponent(PlayerComponent.class).setName("Player");
		player2 = getGameWorld().spawn("Player", TILE_SIZE * 17, TILE_SIZE * 17);
		player2.getComponent(PlayerComponent.class).setName("PLayer2");
		if (multiplayer) {
			cliente = new ClienteTCP();
			id=cliente.getId();
			playerPosition= new PlayerPosition(
					0.0,
					0.0,
					id	
					);
			
		}
		
	}
	
	@Override
	public void loadState(DataFile dataFile) {
		super.loadState(dataFile);
	}
	
	@Override
	public void initGameVars(Map<String, Object> vars) {
		super.initGameVars(vars);
	}

	@Override
	public void initPhysics() {
		getPhysicsWorld().addCollisionHandler(new CollisionHandler(BombermanType.PLAYER, BombermanType.UPMAXBOMBS) {
			@Override
			protected void onCollision(Entity pl, Entity powerup) {
				if (powerup.getPosition().equals(pl.getPosition())) {
					powerup.removeFromWorld();
					pl.getComponent(PlayerComponent.class).increaseMaxBombs();
				}
			}

		});
		getPhysicsWorld().addCollisionHandler(new CollisionHandler(BombermanType.PLAYER, BombermanType.UPPOWER) {
			@Override
			protected void onCollision(Entity pl, Entity powerup) {
				if (powerup.getPosition().equals(pl.getPosition())) {
					powerup.removeFromWorld();
					pl.getComponent(PlayerComponent.class).increasePower();
				}
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
		if(multiplayer) {
			if(FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(id).getComponent(CellMoveComponent.class).isMoving()) {
			actualizaPosicion();
			envioPosicion();
			}
			
		}
//		if (multiplayer) {
//			if (cliente.bombaPuesta && cliente.colocada == 1) {
//				placeBomb(getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(cliente.recibir.id)
//						.getComponent(PlayerComponent.class));
//				cliente.colocada = 0;
//			}
//			if (cliente.recibir.moving == true) {
//				movePlayer(getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(cliente.recibir.id)
//						.getComponent(PlayerComponent.class), cliente.recibir.accion);
//			}
//		}
	}

	private void actualizaPosicion() {
		playerPosition.setPositionX(FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(id).getPosition().getX());
		playerPosition.setPositionY(FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(id).getPosition().getY());
		
	}

	private void envioPosicion() {
		
		try {
			PlayerPosition objetoEnviar= new PlayerPosition(playerPosition.getPositionX(),playerPosition.getPositionY(),playerPosition.getIdEntity());
			cliente.getOs().writeObject(objetoEnviar);
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			// Cambiar el estado de la entidad BRICK a "WALKABLE" cuando desaparece
			e.getComponent(AStarMoveComponent.class).getGrid().get(e.getComponent(CellMoveComponent.class).getCellX(),
					e.getComponent(CellMoveComponent.class).getCellY()).setState(CellState.WALKABLE);

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
		} else if (e.isType(BombermanType.ENEMY)) {
			e.removeFromWorld();
		}
	}

	private static void placeBomb(PlayerComponent player) {
		player.placeBomb();
	}
	public static void actualizarPlayer(PlayerPosition player) {
		Entity playerToMove = getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(player.getIdEntity());
		playerToMove.setX(player.getPositionX());
		playerToMove.setY(player.getPositionY());
		
	}
	public static void movePlayer(int id, String accion) {
		try {
			PlayerComponent playerToMove = getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(id)
					.getComponent(PlayerComponent.class);
			switch (accion) {
			case "w":
				playerToMove.up();
				break;
			case "a":
				playerToMove.left();
				break;
			case "s":
				playerToMove.down();
				break;
			case "d":
				playerToMove.right();
				break;
			case "e":
				placeBomb(playerToMove);
				break;
			default:
				break;
			}
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}