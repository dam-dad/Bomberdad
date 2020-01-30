package dad.javafx.bomberdad.components;

import java.util.ArrayList;
import java.util.List;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;

import dad.javafx.bomberdad.BombermanApp;
import dad.javafx.bomberdad.BombermanType;
import javafx.beans.property.SimpleListProperty;
import javafx.geometry.Point2D;

public class BombComponent extends Component {

	private int radius;
	 ArrayList<Entity> entities = new ArrayList<Entity>();

     ArrayList<Entity> entitiesToDelete = new ArrayList<Entity>();
    public BombComponent(int radius) {
        this.radius = radius;
    }

    public void explode() {
        BoundingBoxComponent bbox = getEntity().getBoundingBoxComponent();
        entities.clear();
        entitiesToDelete.clear();
        

        //Explosion vertical
        

        FXGL.getGameWorld()
        .getEntitiesInRange(bbox.range(0, radius))
        .stream()
        .filter(e -> e.isType(BombermanType.BRICK) || e.isType(BombermanType.PLAYER) || e.isType(BombermanType.FLOOR))
        .forEach(e -> {
        	
        		entities.add(e);
        });
       
     
        //Explosion horizontal
        FXGL.getGameWorld()

        		.getEntitiesInRange(bbox.range(radius, 0))
        		.stream()
        		.filter(e -> e.isType(BombermanType.BRICK) || e.isType(BombermanType.PLAYER))
        		.forEach(e -> {
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
			if (st.getX() < this.getEntity().getX()) {
				for (int i = (int) (st.getX() + 40); i < this.getEntity().getX(); i = i + 40) {
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
				for (int i = (int) (st.getX() - 40); i > this.getEntity().getX(); i = i - 40) {
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
				for (int i = (int) (st.getY() + 40); i < this.getEntity().getY(); i = i + 40) {
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
				for (int i = (int) (st.getY() - 40); i > this.getEntity().getY(); i = i - 40) {
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

        
        //Destruimos las entidades
        
        for (Entity st:entitiesToDelete) {
        	FXGL.<BombermanApp>getAppCast().onDestroyed(st);
//        	FXGL.spawn("explosion",st.getPosition());
		}
        
        getEntity().removeFromWorld();
        FXGL.spawn("explosion",getEntity().getPosition());
    }

}
