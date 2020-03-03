package dad.javafx.bomberdad;


import static com.almasb.fxgl.dsl.FXGL.getAssetLoader;
import static com.almasb.fxgl.dsl.FXGL.getGameController;
import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGL.getPhysicsWorld;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.GameView;
import com.almasb.fxgl.app.IntroScene;
import com.almasb.fxgl.app.LoadingScene;
import com.almasb.fxgl.app.MenuType;
import com.almasb.fxgl.app.SceneFactory;
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

import dad.javafx.bomberdad.components.BombComponent;

import dad.javafx.bomberdad.components.PlayerComponent;
import dad.javafx.bomberdad.menu.CustomMenu;
import dad.javafx.bomberdad.menu.IntroSceneController;
import dad.javafx.bomberdad.menu.LoadingSceneController;
import dad.javafx.bomberdad.online.ClienteTCP;
import dad.javafx.bomberdad.online.DynamicObject;
import dad.javafx.bomberdad.online.PlayerPosition;
import dad.javafx.bomberdad.ratings.Puntuaciones;
import dad.javafx.bomberdad.ratings.PuntuacionesDataProvider;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * Clase principal que maneja los componentes principales del juego
 * @author Alejandro Arrocha Hdez, Rosmen Ramos Díaz, Cristian Abad de Vera, Pablo García Gómez
 *
 */

public class BombermanApp extends GameApplication {

	public static final int TILE_SIZE = 30;
	public static final int UI_SIZE = 200;
	public static Entity player, player2, enemy, enemy2;
	private int lvl = 0;
	private static boolean requestNewGame = false;
	public static String theme = "dad";
	public static ClienteTCP cliente;
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
	public static int id;
	public static int numberPlayers;
/**
 * Inicializa menú del juego
 * @param settings clase que contiene todas las herramientas para crear el menú del juego.
 */
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
		settings.setAppIcon(getClass().getResource("/assets/icon/icon.png").toString());
		settings.setSceneFactory(new SceneFactory() {
			/**
			 * Devolver menú inicial customizado
			 * @return Clase con el menú customizado
			 */
			@Override
			public FXGLMenu newMainMenu() {
				return new CustomMenu(MenuType.MAIN_MENU);
			}
			
			@Override
			public FXGLMenu newGameMenu() {
				return new CustomMenu(MenuType.GAME_MENU);
			}
			/**
			 * Devolver pantalla de carga customizada
			 * @return Clase con pantalla de carga customizada
			 */
			@Override
			public LoadingScene newLoadingScene() {
				return new LoadingSceneController();
			}
			/**
			 * Devolver video de introducción al iniciar el juego
			 * @return Clase que devuelve video customizado con transiciones 
			 */
			@Override
			public IntroScene newIntro() {
				return new IntroSceneController();
			}
		});

	}
/**
 * Metodo para iniciar las puntuaciones dentro del juego
 * 
 */
	@Override
	public void initUI() {
		getGameScene().getRoot().setTranslateX(UI_SIZE);
		UI ui = getAssetLoader().loadUI("BomberAppView.fxml", uiController);
		ui.getRoot().setTranslateX(-UI_SIZE);
		getGameScene().addUI(ui);
	}
/**
 * Método que recoge las teclas pulsada de los jugadores
 */
	@Override
	protected void initInput() {
		getInput().addAction(new UserAction("Move Up") {
			@Override
			protected void onAction() {

				try {
					FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(id).getComponent(PlayerComponent.class)
							.up();
				} catch (Exception e) {
					
				}
			}
		}, KeyCode.W);

		getInput().addAction(new UserAction("Move Left") {
			@Override
			protected void onAction() {

				try {
					FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(id).getComponent(PlayerComponent.class)
							.left();
				} catch (Exception e) {
					
				}
			}
		}, KeyCode.A);

		getInput().addAction(new UserAction("Move Down") {
			@Override
			protected void onAction() {

				try {
					FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(id).getComponent(PlayerComponent.class)
							.down();
				} catch (Exception e) {
				
				}

			}
		}, KeyCode.S);

		getInput().addAction(new UserAction("Move Right") {
			@Override
			protected void onAction() {

				try {
					FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(id).getComponent(PlayerComponent.class)
							.right();
				} catch (Exception e) {
				
				}

			}
		}, KeyCode.D);

		getInput().addAction(new UserAction("Place Bomb") {
			@Override
			protected void onActionBegin() {
				if (multiplayer) {
					envioPosicion("bomba");
				}
				try {
					FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(id).getComponent(PlayerComponent.class)
							.placeBomb();
				} catch (Exception e) {
					
				}
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
/**
 * Inicia juego
 */
	@Override
	public void initGame() {
		if (multiplayer) {
			initOnlineMode();
		} else {
			initOfflineMode();
		}

	}

/**
 * Método que prepara el juego para el modo offline
 * Genera mapa,inicializa los jugadores, inicializa rattings
 */
	public void initOfflineMode() {
		GenerateMap.newMap(lvl);
		cargarMundo();
		player = getGameWorld().spawn("Player", TILE_SIZE, TILE_SIZE);
		player.getComponent(PlayerComponent.class).setName("Player");
		player2 = getGameWorld().spawn("Player", TILE_SIZE * 17, TILE_SIZE * 17);
		player2.getComponent(PlayerComponent.class).setName("Player 2");
//		enemy = getGameWorld().spawn("e", TILE_SIZE, TILE_SIZE * 17);
//		enemy.getComponent(EnemyComponent.class);
//		enemy2 = getGameWorld().spawn("d", TILE_SIZE * 17, TILE_SIZE);
//		enemy2.getComponent(EnemyComponent.class);
		ratings.getPoints().get(0).set(0, player.getComponent(PlayerComponent.class).getName());
		ratings.getPoints().get(1).set(0, player2.getComponent(PlayerComponent.class).getName());

	}

	/**
	 * Método que prepara el juego para el modo online
	 * Se genera el cliente y se conecta al servidor
	 * Genera mapa, genera el nivel de la partida, inicializa los jugadores, inicializa rattings
	 */
	public void initOnlineMode() {

		if (multiplayer && !onlineActivo) {
			cliente = new ClienteTCP(ip, port);
			id = cliente.getId();
			onlineActivo = true;
		}
		playerPosition = new PlayerPosition(0.0, 0.0, id);
		GenerateMap.createMap(cliente.getMapa());
		cargarMundo();
		player = getGameWorld().spawn("Player", TILE_SIZE, TILE_SIZE);
		player.getComponent(PlayerComponent.class).setName("Player");
		player2 = getGameWorld().spawn("Player", TILE_SIZE * 17, TILE_SIZE * 17);
		player2.getComponent(PlayerComponent.class).setName("Player 2");
		juegoPreparado = true;
	}
	/**
	 * Método que carga las texturas del mapa y lo asigna a la vista del juego.
	 * Mapea todo el nivel del juego para asignar a cada entidad, si se puede caminar por encima del componente o no
	 */
	private void cargarMundo() {
		getGameWorld().addEntityFactory(new BombermanFactory(theme));
		Texture texture = getAssetLoader().loadTexture("bg" + theme + ".gif");
		GameView vista = new GameView(texture, -0);
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
	}
/**
 * Override necesario
 */
	@Override
	public void loadState(DataFile dataFile) {
		super.loadState(dataFile);
	}
/**
 * Override necesario
 */
	@Override
	public void initGameVars(Map<String, Object> vars) {
		super.initGameVars(vars);
	}
/**
 * Determina las físicas de los componentes cuando colisionan entre sí
 */
	@Override
	public void initPhysics() {
		getPhysicsWorld().addCollisionHandler(new CollisionHandler(BombermanType.PLAYER, BombermanType.UPMAXBOMBS) {
			@Override
			protected void onCollision(Entity pl, Entity powerup) {
				
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

		});
		getPhysicsWorld().addCollisionHandler(new CollisionHandler(BombermanType.PLAYER, BombermanType.UPPOWER) {
			@Override
			protected void onCollision(Entity pl, Entity powerup) {
			
					powerup.removeFromWorld();
					pl.getComponent(PlayerComponent.class).increasePower();
					int id = 0;
					if (pl.getComponent(PlayerComponent.class).getName().equals(player.getComponent(PlayerComponent.class).getName())) {
						id = 0;
					} else if (pl.getComponent(PlayerComponent.class).getName().equals(player2.getComponent(PlayerComponent.class).getName())) {
						id = 1;
					}
					uiController.setAddProgress(BombermanType.UPPOWER, id);
				
			}
		});
	}
/**
 * Método para cambiar el nivel del mapa, dependiendo del lvl que asigne, se generará un mapa u otro
 */
	private void levelUp() {
		if (!multiplayer) {
			if (lvl == 3) {
				FXGL.getGameController().gotoMainMenu();
				try {
					generarPdf();
				} catch (JRException | IOException e) {
					e.printStackTrace();
				}
			} else {
				lvl++;
				requestNewGame = true;
			}
		} else {
			if (lvl == 3) {
				lvl=0;
			} else {
				lvl++;
			}
			requestNewGame = true;
		}
	}
/**
 * @param tpf tiempo por frame
 * Cada vez que se refresca un frame actualiza la posición y la envía al servidor si está en modo multiplayer.
 * Carga un nuevo juego con su correspondiente mapa si requestNewGame pasa a true.
 * 
 */
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
					try {
						cliente.getOs().writeObject(dOSolicitaMapa);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}

		}
	}
/**
 * Actualiza la posición del player in game y la guarda en el objeto playerPosition;
 */
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

/**
 * Envío posición del jugador al servidor para que sea tratada por el resto de jugadores conectados a la partida
 * @param type Tipo de dato que se va actualizar cuando el objeto sea enviado y recibido, si es PlayerPosition, se actualiza posición del jugador que lo haya
 * enviado, si es PlacePlayerBomb,generará una bomba en las coordenadas pasadas.
 */
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
/**
 * Procesa las entidades que serán eliminadas cuando son afectadas por una bomba
 * @param e Entitad que será procesada para su eliminación,
 * @param owned Player que puso la bomba, utilizada para agregar la puntuación dependiendo de que entidades haya destruido
 */
	public void onDestroyed(Entity e, PlayerComponent owned) {
		if (e.isType(BombermanType.PLAYER)) {
			int pl = 0;
			if (owned != null && !owned.equals(e.getComponent(PlayerComponent.class))) {
				if (owned.getName().equals(player.getComponent(PlayerComponent.class).getName())) {
					pl = 0;
				} else if (owned.getName().equals(player2.getComponent(PlayerComponent.class).getName())) {
					pl = 1;
				}
				int pOld = Integer.parseInt(ratings.getPoints().get(pl).get(1));
				int pNew = pOld + 100;
				uiController.setPointsLbl(pNew + "", pl);
				ratings.getPoints().get(pl).set(1, "" + pNew);
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
				if (playerHit.getName().equals(player.getComponent(PlayerComponent.class).getName())) {
					playerHit.resetMaxBombs();
					playerHit.resetPower();
				} else {
					playerHit.resetMaxBombs();
					playerHit.resetPower();
				}
				uiController.resetPowerUps(pl);
				e.getComponent(PlayerComponent.class).playFadeAnimation();
			}
		} else if (e.isType(BombermanType.BRICK) || e.isType(BombermanType.BRICKRED) || e.isType(BombermanType.BRICKYELLOW)) {
			if (owned != null) {
				int pl = 0;
				if (owned.getName().equals(player.getComponent(PlayerComponent.class).getName())) {
					pl = 0;
				} else if (owned.getName().equals(player2.getComponent(PlayerComponent.class).getName())) {
					pl = 1;
				}
				int pOld = Integer.parseInt(ratings.getPoints().get(pl).get(1));
				int pNew = pOld + 5;
				uiController.setPointsLbl(pNew + "", pl);
				ratings.getPoints().get(pl).set(1, "" + pNew);

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

		} else if (e.isType(BombermanType.ENEMY)) {
			e.removeFromWorld();
		}
	}

/**
 * Recibe un String para generar un nuevo mapa he inicializar una nueva partida
 * @param mapa String que guarda una matriz que servirá para generar un nuevo mapa
 */
	public static void actualizaNuevoMapa(String mapa) {
		juegoPreparado = false;
		cliente.setMapa(mapa);
		FXGL.getGameTimer().runOnceAfter(() -> {
			try {
				getGameController().startNewGame();
			} catch (Exception e) {
				
			}
			
		}, Duration.millis(500));
	}

/**
 * Actualiza la posición de un player dentro del juego 
 * @param player PLayer al que se actualizará la posición
 */
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

/**
 * Recibe un jugador, coge su coordenas y genera un bomba en dichas coordenas
 * @param player al que se le recogeran sus coordenadas.
 */
	public static void ponerBombaPlayer(PlayerPosition player) {
		if (juegoPreparado) {
			FXGL.getGameTimer().runOnceAfter(() -> {
				try {
				PlayerComponent comprobarBombasPuesta= FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(player.getIdEntity()).getComponent(PlayerComponent.class);
					if( comprobarBombasPuesta.getBombsPlaced()==comprobarBombasPuesta.getMaxBombs()) {
						
					}else {
						comprobarBombasPuesta.setBombsPlaced(comprobarBombasPuesta.getBombsPlaced()+1);
					int power = FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(player.getIdEntity())
							.getComponent(PlayerComponent.class).getPower();
					int x = (int) player.getPositionX() / BombermanApp.TILE_SIZE;
					int y = (int) player.getPositionY() / BombermanApp.TILE_SIZE;
					Entity bomb = FXGL.getGameWorld().spawn("Bomb",
							new SpawnData(x * BombermanApp.TILE_SIZE, y * BombermanApp.TILE_SIZE).put("radius",
									(BombermanApp.TILE_SIZE / 2) + power));

					FXGL.getGameTimer().runOnceAfter(() -> {
						if(juegoPreparado) {
						bomb.getComponent(BombComponent.class).explode(power,FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(player.getIdEntity())
								.getComponent(PlayerComponent.class));
						comprobarBombasPuesta.setBombsPlaced(comprobarBombasPuesta.getBombsPlaced()-1);
						}

					}, Duration.seconds(2));
					}
				} catch (Exception e) {

				}
			}, Duration.millis(10));
		}
	}
	
	public static final String JRXML_FILE = "/reports/informe.jrxml";
	public static final String PDF_FILE = "pdf/informe.pdf";
	/**
	 * Método para generar los informas a través de la puntuación.
	 * @throws JRException
	 * @throws IOException
	 */
	public static void generarPdf() throws JRException, IOException {

		JasperReport report = JasperCompileManager.compileReport(BombermanApp.class.getResourceAsStream(JRXML_FILE));
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("anyo", 2020);
        JasperPrint print  = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(PuntuacionesDataProvider.getPuntuaciones()));
        JasperExportManager.exportReportToPdfFile(print, PDF_FILE);
		Desktop.getDesktop().open(new File(PDF_FILE));
	}
/**
 * Main
 * @param args
 */
	public static void main(String[] args) {
		launch(args);
	}

}