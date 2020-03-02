package dad.javafx.bomberdad.ratings;

import java.util.ArrayList;
import java.util.List;

import dad.javafx.bomberdad.BombermanApp;

public class PuntuacionesDataProvider {
	public static List<PuntuacionesJugador> getPuntuaciones() {
		List<PuntuacionesJugador> listRatings = new ArrayList<PuntuacionesJugador>();
		listRatings.add(new PuntuacionesJugador(BombermanApp.ratings.getPoints().get(0).get(0), 
				Integer.parseInt(BombermanApp.ratings.getPoints().get(0).get(1))));
		listRatings.add(new PuntuacionesJugador(BombermanApp.ratings.getPoints().get(1).get(0), 
				Integer.parseInt(BombermanApp.ratings.getPoints().get(1).get(1))));
		if (BombermanApp.numberPlayers >= 3) {
			listRatings.add(new PuntuacionesJugador(BombermanApp.ratings.getPoints().get(2).get(0), 
					Integer.parseInt(BombermanApp.ratings.getPoints().get(2).get(1))));
		}
		if (BombermanApp.numberPlayers >= 4) {
			listRatings.add(new PuntuacionesJugador(BombermanApp.ratings.getPoints().get(2).get(0), 
					Integer.parseInt(BombermanApp.ratings.getPoints().get(2).get(1))));
		}
		return listRatings;
	}
}
