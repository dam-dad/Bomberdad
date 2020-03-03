package dad.javafx.bomberdad.ratings;

import java.util.ArrayList;

import dad.javafx.bomberdad.BombermanApp;

/**
 * Clase para el sistema de puntuaciones del juego
 * @author Alejandro Arrocha Hdez, Rosmen Ramos Díaz, Cristian Abad de Vera, Pablo García Gómez
 *
 */

public class Puntuaciones {
	private ArrayList<ArrayList<String>> points = new ArrayList<ArrayList<String>>();

	/**
	 * Constructor que genera las filas y columnas de las puntuaciones en la tabla bidireccional
	 */
	
	public Puntuaciones() {
		points.add(new ArrayList<String>());
		points.get(0).add("");
		points.get(0).add("0");
		points.add(new ArrayList<String>());
		points.get(1).add("");
		points.get(1).add("0");
		if (BombermanApp.numberPlayers >= 3) {
			points.add(new ArrayList<String>());
			points.get(2).add("");
			points.get(2).add("0");
		}
		if (BombermanApp.numberPlayers >= 4) {
			points.add(new ArrayList<String>());
			points.get(3).add("");
			points.get(3).add("0");
		}
	}
	
	public ArrayList<ArrayList<String>> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<ArrayList<String>> points) {
		this.points = points;
	}
}
