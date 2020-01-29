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

    public BombComponent(int radius) {
        this.radius = radius;
    }

    public void explode() {
        BoundingBoxComponent bbox = getEntity().getBoundingBoxComponent();
        ArrayList<Entity> entities = new ArrayList<Entity>();
        ArrayList<Entity> entitiesToDelete = new ArrayList<Entity>();
        
        //Explosion vertical
        
        FXGL.getGameWorld()
                .getEntitiesInRange(bbox.range(0, radius))
                .stream()
                .filter(e -> e.isType(BombermanType.BRICK) || e.isType(BombermanType.PLAYER))
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

        //Revisar si hay un muro en medio
        
        for (Entity st:entities) {
        	
        	List<Entity> ent = new SimpleListProperty<Entity>();
        	if (st.getX() < this.getEntity().getX()) {
        		ent = FXGL.getGameWorld().getEntitiesAt(new Point2D(st.getX()+40,st.getY()));
				if (ent.get(0).isType(BombermanType.WALL)) {
					//NOTHING
				} else {
					entitiesToDelete.add(st);
				}
			} else if (st.getX() > this.getEntity().getX()) {
        		ent = FXGL.getGameWorld().getEntitiesAt(new Point2D(st.getX()-40,st.getY()));
				if (ent.get(0).isType(BombermanType.WALL)) {
					//NOTHING
				} else {
					entitiesToDelete.add(st);
				}
			} else if (st.getY() < this.getEntity().getY()) {
        		ent = FXGL.getGameWorld().getEntitiesAt(new Point2D(st.getX(),st.getY()+40));
				if (ent.get(0).isType(BombermanType.WALL)) {
					//NOTHING
				} else {
					entitiesToDelete.add(st);
				}
			} else if (st.getY() > this.getEntity().getY()) {
        		ent = FXGL.getGameWorld().getEntitiesAt(new Point2D(st.getX(),st.getY()-40));
				if (ent.get(0).isType(BombermanType.WALL)) {
					//NOTHING
				} else {
					entitiesToDelete.add(st);
				}
			}
        	
		}
        
        //Destruimos las entidades
        
        for (Entity st:entitiesToDelete) {
        	FXGL.<BombermanApp>getAppCast().onDestroyed(st);
		}
        
        getEntity().removeFromWorld();
    }
}
