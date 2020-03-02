package dad.javafx.bomberdad.ia;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;

@Required(AStarMoveComponent.class)
public class FollowWalls extends Component {
	private AStarMoveComponent astar;

	@Override
	public void onUpdate(double tpf) {
		if (!astar.isMoving()) {

			
		}
	}
}