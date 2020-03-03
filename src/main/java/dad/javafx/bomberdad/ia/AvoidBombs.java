package dad.javafx.bomberdad.ia;

import java.util.ArrayList;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;

import dad.javafx.bomberdad.BombermanType;

/**
 * Componente que realiza el movimiento de evitar una bomba
 * @author Alejandro Arrocha Hdez, Rosmen Ramos Díaz, Cristian Abad de Vera, Pablo García Gómez
 *
 */

@Required(AStarMoveComponent.class)
public class AvoidBombs extends Component {
	private AStarMoveComponent astar;
	int[] coorBomb = new int[3];
	int xb;
	int yb;

	@Override
	public void onUpdate(double tpf) {
		ArrayList<Entity> listaBombs = new ArrayList<Entity>(FXGL.getGameWorld().getEntitiesByType(BombermanType.BOMB));
		if (listaBombs.size() != 0) {

			coorBomb = bombNear();
			if (coorBomb[2] < 4) {
				xb = coorBomb[0];
				yb = coorBomb[1];
				while (!astar.getGrid().get(xb, yb).isWalkable() && xb != 18 && yb != 18) {
						xb++;
						yb++;
					}
				while (!astar.getGrid().get(xb, yb).isWalkable() && xb != 0 && yb != 0) {
						xb--;
						yb--;
					}
				}
				astar.moveToCell(xb, yb);
			}
		}
	

	public int[] bombNear() {
		ArrayList<Entity> listaBombs = new ArrayList<Entity>(FXGL.getGameWorld().getEntitiesByType(BombermanType.BOMB));
		int enemyX = FXGL.getGameWorld().getEntitiesByType(BombermanType.ENEMY).get(0).call("getCellX");
		int enemyY = FXGL.getGameWorld().getEntitiesByType(BombermanType.ENEMY).get(0).call("getCellY");
		int x = FXGL.getGameWorld().getEntitiesByType(BombermanType.BOMB).get(0).call("getCellX");
		int y = FXGL.getGameWorld().getEntitiesByType(BombermanType.BOMB).get(0).call("getCellY");
		int[] coor = new int[3];
		coor[0] = x;
		coor[1] = y;
		double distance = Math.hypot(x - enemyX, y - enemyY);
		double distanceAux;

		for (int i = 0; i < listaBombs.size(); i++) {
			x = FXGL.getGameWorld().getEntitiesByType(BombermanType.BOMB).get(i).call("getCellX");
			y = FXGL.getGameWorld().getEntitiesByType(BombermanType.BOMB).get(i).call("getCellY");
			distanceAux = Math.hypot(x - enemyX, y - enemyY);
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