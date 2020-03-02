package dad.javafx.bomberdad.ia;

import java.util.ArrayList;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;

import dad.javafx.bomberdad.BombermanType;

@Required(AStarMoveComponent.class)
public class RunAway extends Component {
	private AStarMoveComponent astar;

	@Override
	public void onUpdate(double tpf) {
		FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(0).getX();
		
	}
	
	public int[] playerNear() {
	ArrayList<Entity> listaPlayer = new ArrayList<Entity>(
			FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER));
	int[] coor = new int[3];
	int enemyX = FXGL.getGameWorld().getEntitiesByType(BombermanType.ENEMY).get(0).call("getCellX");
	int enemyY = FXGL.getGameWorld().getEntitiesByType(BombermanType.ENEMY).get(0).call("getCellY");
	int x = FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(0).call("getCellX");
	int y = FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(0).call("getCellY");
	coor[0] = x;
	coor[1] = y;
	double distance = Math.hypot(x - enemyX, y - enemyY);
	double distanceAux;
	int i;

	for (i = 0; i < listaPlayer.size(); i++) {
		x = FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(i).call("getCellX");
		y = FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(i).call("getCellY");
		distanceAux = Math.hypot(x - enemyX, y - enemyY);
		if (distanceAux < distance) {
			distance = distanceAux;
			coor[0] = x;
			coor[1] = y;
			coor[2] = i;
		}
	}
	return coor;
}
}