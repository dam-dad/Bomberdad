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
import com.almasb.fxgl.entity.SpawnData;
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


import dad.javafx.bomberdad.components.EnemyComponent;

import dad.javafx.bomberdad.components.BombComponent;

import dad.javafx.bomberdad.components.PlayerComponent;
import dad.javafx.bomberdad.menu.CustomMenu;
import dad.javafx.bomberdad.menu.IntroSceneController;
import dad.javafx.bomberdad.menu.LoadingSceneController;
import dad.javafx.bomberdad.online.ClienteTCP;
import dad.javafx.bomberdad.online.DynamicObject;
import dad.javafx.bomberdad.online.PlayerPosition;
import dad.javafx.bomberdad.ratings.Puntuaciones;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class BombermanApp extends GameApplication {

	public static final int TILE_SIZE = 30;
	public static final int UI_SIZE = 200;
	public static Entity player, player2, enemy;
	private int lvl = 0;
	private static boolean requestNewGame = false;
	public static String theme = "crab";
	private static ClienteTCP cliente;
	public static String ip;
	public static int port;
	private PlayerPosition playerPosition;
	public static boolean multiplayer = false, onlineActivo = false;
	public static boolean moving = false;
	public static boolean fullScreen = false;

	// nuevo
	private static boolean juegoPreparado = false;
	public static Puntuaciones ratings = new Puntuaciones();
	int i = 0;
	public int tam = 0;
	private BombermanAppUIController uiController = new BombermanAppUIController();
	public int id;
	public static int numberPlayers;

	@Override
	protected void initSettings(GameSettings settings) {
		settings.setTitle("BomberDAD");
		settings.setVersion("0.1");
		settings.setWidth((19 * TILE_SIZE) + (UI_SIZE * 2));
		settings.setHeight(19 * TILE_SIZE);
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
		getGameScene().getRoot().setTranslateX(UI_SIZE);
		UI ui = getAssetLoader().loadUI("BomberAppView.fxml", uiController);
		ui.getRoot().setTranslateX(-UI_SIZE);
		getGameScene().addUI(ui);
	}

	@Override
	protected void initInput() {
		getInput().addAction(new UserAction("Move Up") {
			@Override
			protected void onAction() {

				FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(id).getComponent(PlayerComponent.class)
						.up();
			}
		}, KeyCode.W);

		getInput().addAction(new UserAction("Move Left") {
			@Override
			protected void onAction() {

				FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(id).getComponent(PlayerComponent.class)
						.left();
			}
		}, KeyCode.A);

		getInput().addAction(new UserAction("Move Down") {
			@Override
			protected void onAction() {

				FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(id).getComponent(PlayerComponent.class)
						.down();

			}
		}, KeyCode.S);

		getInput().addAction(new UserAction("Move Right") {
			@Override
			protected void onAction() {

				FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(id).getComponent(PlayerComponent.class)
						.right();

			}
		}, KeyCode.D);

		getInput().addAction(new UserAction("Place Bomb") {
			@Override
			protected void onActionBegin() {
				if (multiplayer) {
					envioPosicion("bomba");
				}
				FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(id).getComponent(PlayerComponent.class)
						.placeBomb();
			}
		}, KeyCode.SPACE);

		getInput().addAction(new UserAction("Move Up2") {
			@Override
			protected void onAction() {
				if (!multiplayer) {
					player2.getComponent(PlayerComponent.class).up();
				}
			}
		}, KeyCode.UP);

		getInput().addAction(new UserAction("Move Left2") {
			@Override
			protected void onAction() {
				if (!multiplayer) {
					player2.getComponent(PlayerComponent.class).left();
				}
			}
		}, KeyCode.LEFT);

		getInput().addAction(new UserAction("Move Down2") {
			@Override
			protected void onAction() {
				if (!multiplayer) {
					player2.getComponent(PlayerComponent.class).down();
				}
			}
		}, KeyCode.DOWN);

		getInput().addAction(new UserAction("Move Right2") {
			@Override
			protected void onAction() {
				if (!multiplayer) {
					player2.getComponent(PlayerComponent.class).right();
				}
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
		if (multiplayer) {
			initOnlineMode();
		} else {
			initOfflineMode();
		}

	}

//Nuevo
	public void initOfflineMode() {
		GenerateMap.newMap(lvl);
		cargarMundo();
		player = getGameWorld().spawn("Player", TILE_SIZE, TILE_SIZE);
		player.getComponent(PlayerComponent.class).setName("Rosmen");
		player2 = getGameWorld().spawn("Player", TILE_SIZE * 17, TILE_SIZE * 17);
		player2.getComponent(PlayerComponent.class).setName("Pablo");
		enemy = getGameWorld().spawn("e", TILE_SIZE, TILE_SIZE * 17);
		enemy.getComponent(EnemyComponent.class);
		ratings.getPoints().get(0).set(0, player.getComponent(PlayerComponent.class).getName());
		ratings.getPoints().get(1).set(0, player2.getComponent(PlayerComponent.class).getName());

	}

//nuevo, hay mucho codigo repetido, hay que pullirlo un poco
	public void initOnlineMode() {

		if (multiplayer && !onlineActivo) {
			cliente = new ClienteTCP(ip, port);
			id = cliente.getId();
			onlineActivo = true;
			System.out.println("FIN crear cliente");
		}
		
		playerPosition = new PlayerPosition(0.0, 0.0, id);
		GenerateMap.createMap(cliente.getMapa());
		System.out.println(cliente.getMapa() +"yolo");
		cargarMundo();
		player = getGameWorld().spawn("Player", TILE_SIZE, TILE_SIZE);
		player.getComponent(PlayerComponent.class).setName("Player");
		player2 = getGameWorld().spawn("Player", TILE_SIZE * 17, TILE_SIZE * 17);
		player2.getComponent(PlayerComponent.class).setName("PLayer2");

		juegoPreparado = true;
	}
	private void cargarMundo() {
		getGameWorld().addEntityFactory(new BombermanFactory(theme));
		Texture texture = getAssetLoader().loadTexture("bg" + theme + ".gif");
		GameView vista = new GameView(texture, 0);
		getGameScene().addGameView(vista);
		System.out.println("hilo");

		Level level = getAssetLoader().loadLevel("map.txt", new TextLevelLoader(TILE_SIZE, TILE_SIZE, '0'));
		getGameWorld().setLevel(level);

		System.out.println("Holi");
		AStarGrid grid = AStarGrid.fromWorld(getGameWorld(), 19, 19, 30, 30, (type) -> {

			if (type == BombermanType.FLOOR || type == BombermanType.ENEMY) {
				return CellState.WALKABLE;
			} else {
				return CellState.NOT_WALKABLE;
			}
		});
		set("grid", grid);
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
					int id = 0;
					if (pl.getComponent(PlayerComponent.class).getName().equals(player.getComponent(PlayerComponent.class).getName())) {
						id = 0;
					} else if (pl.getComponent(PlayerComponent.class).getName().equals(player2.getComponent(PlayerComponent.class).getName())) {
						id = 1;
					}
					uiController.setAddProgress(BombermanType.UPMAXBOMBS, id);
				}
			}

		});
		getPhysicsWorld().addCollisionHandler(new CollisionHandler(BombermanType.PLAYER, BombermanType.UPPOWER) {
			@Override
			protected void onCollision(Entity pl, Entity powerup) {
				if (powerup.getPosition().equals(pl.getPosition())) {
					powerup.removeFromWorld();
					pl.getComponent(PlayerComponent.class).increasePower();
					int id = 0;
					if (pl.getComponent(PlayerComponent.class).getName().equals("Player")) {
						id = 0;
					} else if (pl.getComponent(PlayerComponent.class).getName().equals("Player2")) {
						id = 1;
					}
					uiController.setAddProgress(BombermanType.UPPOWER, id);
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

		if (multiplayer) {

			actualizaPosicion();
			envioPosicion("playerPosition");

		}
		// nuevo
		if (requestNewGame) {
			requestNewGame = false;
			if (!multiplayer) {
				getGameController().startNewGame();
			} else {
				if (id == 0) {
					DynamicObject dOSolicitaMapa = new DynamicObject("RequestNewMap", String.valueOf(lvl));
					System.out.println(lvl);
					try {
						cliente.getOs().writeObject(dOSolicitaMapa);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}

		}
	}

	private void actualizaPosicion() {
		if (juegoPreparado) {
			try {
				playerPosition.setPositionX(
						FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(id).getPosition().getX());
				playerPosition.setPositionY(
						FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(id).getPosition().getY());
			} catch (Exception e) {

			}
		}
	}

//nuevo
	private void envioPosicion(String type) {
		if (juegoPreparado) {
			try {
				String tipoEnvio;
				if (type == "playerPosition") {
					tipoEnvio = "PlayerPosition";
				} else {
					tipoEnvio = "PlacePlayerBomb";
				}
				PlayerPosition objetoEnviar = new PlayerPosition(playerPosition.getPositionX(),
						playerPosition.getPositionY(), playerPosition.getIdEntity());
				DynamicObject dO = new DynamicObject(tipoEnvio, objetoEnviar);
				cliente.getOs().writeObject(dO);

			} catch (IOException e) {

			}
		}
	}

	public void onDestroyed(Entity e, PlayerComponent owned) {
		if (e.isType(BombermanType.PLAYER)) {
			int pl = 0;
			if (owned != null && !owned.equals(e.getComponent(PlayerComponent.class))) {
				if (owned.getName().equals(player.getComponent(PlayerComponent.class).getName())) {
					pl = 0;
				} else if (owned.getName().equals(player.getComponent(PlayerComponent.class).getName())) {
					pl = 1;
				}
				int pOld = Integer.parseInt(ratings.getPoints().get(pl).get(1));
				int pNew = pOld + 100;
				uiController.setPointsLbl(pNew + "", pl);
				ratings.getPoints().get(pl).set(1, "" + pNew);
//				System.out.println(ratings.getPoints().get(pl).get(0)+" points: "+ratings.getPoints().get(pl).get(1));
			}
			PlayerComponent playerHit = e.getComponent(PlayerComponent.class);
			if (playerHit.getName().equals(player.getComponent(PlayerComponent.class).getName())) {
				pl = 0;
			} else if (playerHit.getName().equals(player2.getComponent(PlayerComponent.class).getName())) {
				pl = 1;
			}
			uiController.setLifesLbl(playerHit.getVidas() + "", pl);
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
		} else if (e.isType(BombermanType.BRICK) || e.isType(BombermanType.BRICKRED) || e.isType(BombermanType.BRICKYELLOW)) {
			if (owned != null) {
				int pl = 0;
				if (owned.getName().equals("Player")) {
					pl = 0;
				} else if (owned.getName().equals("Player2")) {
					pl = 1;
				}
				int pOld = Integer.parseInt(ratings.getPoints().get(pl).get(1));
				int pNew = pOld + 5;
				uiController.setPointsLbl(pNew + "", pl);
				ratings.getPoints().get(pl).set(1, "" + pNew);
				System.out
						.println(ratings.getPoints().get(pl).get(0) + " points: " + ratings.getPoints().get(pl).get(1));
			}
			e.removeFromWorld();
			// Cambiar el estado de la entidad BRICK a "WALKABLE" cuando desaparece
			e.getComponent(AStarMoveComponent.class).getGrid().get(e.getComponent(CellMoveComponent.class).getCellX(),
					e.getComponent(CellMoveComponent.class).getCellY()).setState(CellState.WALKABLE);

			Entity f = getGameWorld().spawn("f", e.getX(), e.getY());
			f.getViewComponent().setOpacity(0);
			
			int x = (int) e.getPosition().getX();
			int y = (int) e.getPosition().getY();
			
			if (e.isType(BombermanType.BRICKRED)) {
				getGameWorld().spawn("PUPower", x, y);
			} else if (e.isType(BombermanType.BRICKYELLOW)) {
				getGameWorld().spawn("PUMaxBombs", x, y);
			}
//			if (FXGLMath.randomBoolean()) {
//
//				int x = (int) e.getPosition().getX();
//				int y = (int) e.getPosition().getY();
//
//				if (FXGLMath.randomBoolean()) {
//					getGameWorld().spawn("PUMaxBombs", x, y);
//				} else {
//					getGameWorld().spawn("PUPower", x, y);
//				}
//			}
		} else if (e.isType(BombermanType.ENEMY)) {
			e.removeFromWorld();
		}
	}

//Nuevo
	public static void actualizaNuevoMapa(String mapa) {
		juegoPreparado = false;
		cliente.setMapa(mapa);
		FXGL.getGameTimer().runOnceAfter(() -> {
			getGameController().startNewGame();
		}, Duration.millis(500));
	}

//Nuevo
	public static void actualizarPlayer(PlayerPosition player) {
		if (juegoPreparado) {
			FXGL.getGameTimer().runOnceAfter(() -> {
				try {
					Entity playerToMove = getGameWorld().getEntitiesByType(BombermanType.PLAYER)
							.get(player.getIdEntity());
					playerToMove.setX(player.getPositionX());
					playerToMove.setY(player.getPositionY());
				} catch (Exception e) {

				}
			}, Duration.millis(10));

		}
	}

//Nuevo
	public static void ponerBombaPlayer(PlayerPosition player) {
		if (juegoPreparado) {
			FXGL.getGameTimer().runOnceAfter(() -> {
				try {
					int power = FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(player.getIdEntity())
							.getComponent(PlayerComponent.class).getPower();
					int x = (int) player.getPositionX() / BombermanApp.TILE_SIZE;
					int y = (int) player.getPositionY() / BombermanApp.TILE_SIZE;
					Entity bomb = FXGL.getGameWorld().spawn("Bomb",
							new SpawnData(x * BombermanApp.TILE_SIZE, y * BombermanApp.TILE_SIZE).put("radius",
									(BombermanApp.TILE_SIZE / 2) + power));

					FXGL.getGameTimer().runOnceAfter(() -> {

						bomb.getComponent(BombComponent.class).explode(power,FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(player.getIdEntity())
								.getComponent(PlayerComponent.class));


					}, Duration.seconds(2));
				} catch (Exception e) {

				}
			}, Duration.millis(10));
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}