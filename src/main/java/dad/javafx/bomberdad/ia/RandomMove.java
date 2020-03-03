package dad.javafx.bomberdad.ia;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;

/**
 * Componente que realiza un movimiento aleatorio 
 * @author Alejandro Arrocha Hdez, Rosmen Ramos Díaz, Cristian Abad de Vera, Pablo García Gómez
 *
 */

@Required(AStarMoveComponent.class)
public class RandomMove extends Component {
	private AStarMoveComponent astar;

	/**
	 * @param tpf Tiempo por frame
	 * Genera una coordenada aleatoria y se mueve hacia ella
	*/
	@Override
	public void onUpdate(double tpf) {
		if (!astar.isMoving()) {

			astar.getGrid().getRandomCell(c -> c.getState().isWalkable()).ifPresent(cell -> {
				astar.moveToCell(cell.getX(), cell.getY());
			});
		}
	}
}