package dad.javafx.bomberdad.components;

import java.util.ArrayList;
import java.util.List;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;

import dad.javafx.bomberdad.BombermanApp;
import dad.javafx.bomberdad.BombermanType;
import javafx.beans.property.SimpleListProperty;
import javafx.geometry.Point2D;

@Required(AStarMoveComponent.class)
public class BombComponent extends Component {

	private int radius;
	ArrayList<Entity> entities = new ArrayList<Entity>();

	ArrayList<Entity> entitiesToDelete = new ArrayList<Entity>();
	ArrayList<Entity> floorEntities= new ArrayList<Entity>();
	public BombComponent(int radius) {
		this.radius = radius;
	}

	@Override
	public void onAdded() {
		
		this.getEntity().getComponent(AStarMoveComponent.class)
		.getGrid()
		.get(
				this.getEntity().getComponent(CellMoveComponent.class).getCellX(), 
				this.getEntity().getComponent(CellMoveComponent.class).getCellY())
		.setState(CellState.NOT_WALKABLE);
	}


	public void explode(int power, PlayerComponent owned) {
		BoundingBoxComponent bbox = getEntity().getBoundingBoxComponent();
		entities.clear();
		entitiesToDelete.clear();
		floorEntities.clear();
		
		// Explosion vertical
 
		FXGL.getGameWorld().getEntitiesInRange(bbox.range(0, radius)).stream()
				.filter(e -> e.isType(BombermanType.BRICK) || e.isType(BombermanType.BRICKRED) || e.isType(BombermanType.BRICKYELLOW) || e.isType(BombermanType.PLAYER) || e.isType(BombermanType.FLOOR) || e.isType(BombermanType.ENEMY)).forEach(e -> {
					entities.add(e);
				});


		// Explosion horizontal
		FXGL.getGameWorld().getEntitiesInRange(bbox.range(radius, 0)).stream()
				.filter(e -> e.isType(BombermanType.BRICK) || e.isType(BombermanType.BRICKRED) || e.isType(BombermanType.BRICKYELLOW) || e.isType(BombermanType.PLAYER) || e.isType(BombermanType.FLOOR) || e.isType(BombermanType.ENEMY)).forEach(e -> {
					boolean thereIs = false;
					for (int i = 0; i < entities.size(); i++) {
						if (entities.get(i) == e) {
							thereIs = true;
							break;
						}
					}
					if (!thereIs) {
						entities.add(e);
						thereIs = false;
					}
				});

		// Revisar si hay un muro en medio

		for (Entity st : entities) {

			List<Entity> ent = new SimpleListProperty<Entity>();
			boolean isWall = false;
//			boolean isFloor= false;
			if (st.getX() < this.getEntity().getX()) {
				for (int i = (int) (st.getX() + BombermanApp.TILE_SIZE); i < this.getEntity().getX(); i = i + BombermanApp.TILE_SIZE) {
					ent = FXGL.getGameWorld().getEntitiesAt(new Point2D(i, st.getY()));
					if (!ent.isEmpty()) {
						if (ent.get(0).isType(BombermanType.WALL)) {
							isWall = true;
						}
					}
				}
				if (!isWall) {

					entitiesToDelete.add(st);

				}

			} else if (st.getX() > this.getEntity().getX()) {
				for (int i = (int) (st.getX() - BombermanApp.TILE_SIZE); i > this.getEntity().getX(); i = i - BombermanApp.TILE_SIZE) {
					ent = FXGL.getGameWorld().getEntitiesAt(new Point2D(i, st.getY()));
					if (!ent.isEmpty()) {
						if (ent.get(0).isType(BombermanType.WALL)) {
							isWall = true;
						}
					}
				}
				if (!isWall) {
					entitiesToDelete.add(st);

				}
			} else if (st.getY() < this.getEntity().getY()) {
				for (int i = (int) (st.getY() + BombermanApp.TILE_SIZE); i < this.getEntity().getY(); i = i + BombermanApp.TILE_SIZE) {
					ent = FXGL.getGameWorld().getEntitiesAt(new Point2D(st.getX(), i));
					if (!ent.isEmpty()) {
						if (ent.get(0).isType(BombermanType.WALL)) {
							isWall = true;
						}
					}
				}
				if (!isWall) {
					entitiesToDelete.add(st);

				}
			} else if (st.getY() > this.getEntity().getY()) {
				for (int i = (int) (st.getY() - BombermanApp.TILE_SIZE); i > this.getEntity().getY(); i = i - BombermanApp.TILE_SIZE) {
					ent = FXGL.getGameWorld().getEntitiesAt(new Point2D(st.getX(), i));
					if (!ent.isEmpty()) {
						if (ent.get(0).isType(BombermanType.WALL)) {
							isWall = true;
						}
					}
				}
				if (!isWall) {
					entitiesToDelete.add(st);

				}
			} else if (st.getX() == this.getEntity().getX() && st.getY() == this.getEntity().getY()) {
				entitiesToDelete.add(st);
			}

		}

		// Destruimos las entidades

		for (Entity st : entitiesToDelete) {
			if(st.isType(BombermanType.FLOOR) || st.isType(BombermanType.ENEMY)) {
				
				FXGL.spawn("explosion",st.getPosition());	
			}else if (st.isType(BombermanType.BRICK) || st.isType(BombermanType.BRICKRED) || st.isType(BombermanType.BRICKYELLOW)){
				Entity aux=st;
				FXGL.<BombermanApp>getAppCast().onDestroyed(st, owned);
				aux.getTypeComponent().setValue(BombermanType.FLOOR);
			
	        FXGL.spawn("explosion",st.getPosition());
			     
			} else {
				FXGL.<BombermanApp>getAppCast().onDestroyed(st, owned);
			}
		}
		getEntity().getComponent(AStarMoveComponent.class)
		.getGrid()
		.get(
				this.getEntity().getComponent(CellMoveComponent.class).getCellX(), 
				this.getEntity().getComponent(CellMoveComponent.class).getCellY())
		.setState(CellState.WALKABLE);
		getEntity().removeFromWorld();
		FXGL.spawn("explosion", getEntity().getPosition());
	}

	

}
