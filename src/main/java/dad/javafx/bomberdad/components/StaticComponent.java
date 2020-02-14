package dad.javafx.bomberdad.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.TransformComponent;

import javafx.util.Duration;

public class StaticComponent extends Component {
	public TransformComponent position;
	private boolean disparando;
	private int numeroActual;
	private int numeroFuturo;
	float chance = 0.05f;

	@Override
	public void onUpdate(double tpf) {
		int actualTime = (int) FXGL.getEngineTimer().getNow();
		if (numeroActual != actualTime) {
			if (actualTime % 2 == 0) {
				numeroActual = actualTime;	
					verticalShot();
			} else {
				numeroActual = (int) FXGL.getEngineTimer().getNow();
				horizontalShot();
			}
		}

	}

	public void verticalShot() {
		Vec2 dir = Vec2.fromAngle(this.getEntity().getRotation() - 90);
		Vec2 dir2 = Vec2.fromAngle(this.getEntity().getRotation() + 90);
		Entity bulletRight = FXGL.getGameWorld().spawn("Bullet",
				new SpawnData(this.getEntity().getX(), this.getEntity().getY()).put("dir", dir.toPoint2D()));
		Entity bulletLeft = FXGL.getGameWorld().spawn("Bullet",
				new SpawnData(this.getEntity().getX(), this.getEntity().getY()).put("dir", dir2.toPoint2D()));
	}

	public void horizontalShot() {
		Vec2 dir = Vec2.fromAngle(this.getEntity().getRotation());
		Vec2 dir2 = Vec2.fromAngle(this.getEntity().getRotation() - 180);
		Entity bulletTop = FXGL.getGameWorld().spawn("Bullet",
				new SpawnData(this.getEntity().getX(), this.getEntity().getY()).put("dir", dir.toPoint2D()));
		Entity bulletBot = FXGL.getGameWorld().spawn("Bullet",
				new SpawnData(this.getEntity().getX(), this.getEntity().getY()).put("dir", dir2.toPoint2D()));
	}

}
