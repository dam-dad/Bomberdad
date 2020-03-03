package dad.javafx.bomberdad.ia;

import java.util.ArrayList;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;

import dad.javafx.bomberdad.BombermanType;

/**
 * Componente que realiza el movimiento de perseguir al jugador
 * @author Alejandro Arrocha Hdez, Rosmen Ramos Díaz, Cristian Abad de Vera, Pablo García Gómez
 *
 */

@Required(AStarMoveComponent.class)
public class ChasePlayer extends Component {
	private AStarMoveComponent astar;
	int[] coorCerca = new int[2];

	/**
	 * @param tpf Tiempo por frame
	 * Recibe las coordenadas del player mas cercano y se mueve hacia su posición
	*/
	@Override
	public void onUpdate(double tpf) {
		coorCerca = playerNear();
		astar.moveToCell(coorCerca[0], coorCerca[1]);
	}

	/**
	 * Localizar al player mas cercano
	 * @return Devuelve un array de enteros, (posición X e Y del jugador mas cercano) 
	*/
	public int[] playerNear() {
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