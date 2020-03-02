package dad.javafx.bomberdad.ia;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;

@Required(AStarMoveComponent.class)
public class RandomMove extends Component {
	private AStarMoveComponent astar;

	@Override
	public void onUpdate(double tpf) {
		if (!astar.isMoving()) {

			astar.getGrid().getRandomCell(c -> c.getState().isWalkable()).ifPresent(cell -> {
				astar.moveToCell(cell.getX(), cell.getY());
			});
		}
	}
}