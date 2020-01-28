package dad.javafx.bomberdad.components;

import com.almasb.fxgl.dsl.FXGL;
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

        //Explosion vertical
        
        FXGL.getGameWorld()
                .getEntitiesInRange(bbox.range(0, radius))
                .stream()
                .filter(e -> e.isType(BombermanType.BRICK))
                .forEach(e -> {
                    FXGL.<BombermanApp>getAppCast().onWallDestroyed(e);
                    e.removeFromWorld();
                });
        
        //Explosion horizontal
        
        FXGL.getGameWorld()
        .getEntitiesInRange(bbox.range(radius, 0))
        .stream()
        .filter(e -> e.isType(BombermanType.BRICK))
        .forEach(e -> {
            FXGL.<BombermanApp>getAppCast().onWallDestroyed(e);
            e.removeFromWorld();
            });
        
        //Eliminar jugador V
        
        FXGL.getGameWorld()
        .getEntitiesInRange(bbox.range(0, radius))
        .stream()
        .filter(e -> e.isType(BombermanType.PLAYER))
        .forEach(e -> {
            FXGL.<BombermanApp>getAppCast();
            e.removeFromWorld();
        });
        
        //Eliminar jugador H
        
        FXGL.getGameWorld()
        .getEntitiesInRange(bbox.range(radius, 0))
        .stream()
        .filter(e -> e.isType(BombermanType.PLAYER))
        .forEach(e -> {
            FXGL.<BombermanApp>getAppCast();
            e.removeFromWorld();
        });

        getEntity().removeFromWorld();
    }
}
