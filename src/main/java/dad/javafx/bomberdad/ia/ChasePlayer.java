package dad.javafx.bomberdad.ia;

import java.util.ArrayList;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;

import dad.javafx.bomberdad.BombermanType;

@Required(AStarMoveComponent.class)
public class ChasePlayer extends Component {
	private AStarMoveComponent astar;
	int[] coorCerca = new int[2];

	@Override
	public void onUpdate(double tpf) {
		coorCerca = masCercanoPro();
		astar.moveToCell(coorCerca[0], coorCerca[1]);
	}

//	public String masCercano() {
//
//		String cercano = "player";
//		double d1;
//		double d2;
//		int playerX = FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(0).call("getCellX");
//		int player2X = FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(1).call("getCellX");
//		int enemyX = FXGL.getGameWorld().getEntitiesByType(BombermanType.ENEMY).get(0).call("getCellX");
//		int playerY = FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(0).call("getCellY");
//		int player2Y = FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(1).call("getCellY");
//		int enemyY = FXGL.getGameWorld().getEntitiesByType(BombermanType.ENEMY).get(0).call("getCellY");
//
//		d1 = Math.hypot(playerX - enemyX, playerY - enemyY);
//		d2 = Math.hypot(player2X - enemyX, player2Y - enemyY);
//
//		if (d1 > d2) {
//			cercano = "player2";
//		}
//		return cercano;
//	}

	public int[] masCercanoPro() {
		ArrayList<Entity> listaPlayer = new ArrayList<Entity>(
				FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER));
		int[] coor = new int[2];
		int enemyX = FXGL.getGameWorld().getEntitiesByType(BombermanType.ENEMY).get(0).call("getCellX");
		int enemyY = FXGL.getGameWorld().getEntitiesByType(BombermanType.ENEMY).get(0).call("getCellY");
		int x = FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(0).call("getCellX");
		int y = FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(0).call("getCellY");
		coor[0] = x;
		coor[1] = y;
		double distance = Math.hypot(x - enemyX, y - enemyY);
		double distanceAux;

		for (int i = 0; i < listaPlayer.size(); i++) {
			x = FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(i).call("getCellX");
			y = FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(i).call("getCellY");
			distanceAux = Math.hypot(x - enemyX, y - enemyY);
			if (distanceAux < distance) {
				distance = distanceAux;
				coor[0] = x;
				coor[1] = y;
			}
		}
		return coor;
	}
}