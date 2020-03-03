package dad.javafx.bomberdad.ia;

import java.util.ArrayList;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;

import dad.javafx.bomberdad.BombermanType;

/**
 * Componente que realiza el movimiento de defender una zona determinada
 * @author Alejandro Arrocha Hdez, Rosmen Ramos Díaz, Cristian Abad de Vera, Pablo García Gómez
 *
 */

@Required(AStarMoveComponent.class)
public class DefendZone extends Component {
	private AStarMoveComponent astar;
	int enemyXOrigen = 0;
	int enemyYOrigen = 0;
	int[] coorCerca = new int[3];
	

	@Override
	public void onUpdate(double tpf) {
		if (enemyXOrigen == 0 & enemyYOrigen == 0) {
			enemyXOrigen = FXGL.getGameWorld().getEntitiesByType(BombermanType.ENEMY).get(0).call("getCellX");
			enemyYOrigen = FXGL.getGameWorld().getEntitiesByType(BombermanType.ENEMY).get(0).call("getCellY");
		}
		coorCerca = playerOnZone();
		if (coorCerca[2] < 7.0) {
			astar.moveToCell(coorCerca[0], coorCerca[1]);
		}
		else {
			System.out.println(enemyXOrigen);
			astar.moveToCell(enemyXOrigen, enemyYOrigen);
		}
	}
	
	public int[] playerOnZone() {
		ArrayList<Entity> listaPlayer = new ArrayList<Entity>(
				FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER));
		int x = FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(0).call("getCellX");
		int y = FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(0).call("getCellY");
		int[] coor = new int[3];
		coor[0] = x;
		coor[1] = y;
		double distance = Math.hypot(x - enemyXOrigen, y - enemyYOrigen);
		double distanceAux;

		for (int i = 0; i < listaPlayer.size(); i++) {
			x = FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(i).call("getCellX");
			y = FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(i).call("getCellY");
			distanceAux = Math.hypot(x - enemyXOrigen, y - enemyYOrigen);
			if (distanceAux < distance) {
				distance = distanceAux;
				coor[0] = x;
				coor[1] = y;
				coor[2] = (int) distance;
			}
		}
		return coor;
	}
}