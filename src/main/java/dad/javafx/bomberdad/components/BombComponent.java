package dad.javafx.bomberdad.components;

import java.util.ArrayList;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;


import dad.javafx.bomberdad.BombermanApp;
import dad.javafx.bomberdad.BombermanType;

public class BombComponent extends Component {

	private int radius;

    public BombComponent(int radius) {
        this.radius = radius;
    }

    public void explode() {
        BoundingBoxComponent bbox = getEntity().getBoundingBoxComponent();
        ArrayList<Entity> entities = new ArrayList<Entity>();
        
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
        			boolean is = false;
        			for (int i = 0; i < entities.size(); i++) {
						if (entities.get(i) == e) {
							is = true;
							break;
						}
					}
        			if (!is) {
        				entities.add(e);
        			}
        		});

        for (Entity st:entities) {
        	FXGL.<BombermanApp>getAppCast().onWallDestroyed(st);
		}
        
        getEntity().removeFromWorld();
    }
}
