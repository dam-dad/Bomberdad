package dad.javafx.bomberdad.ratings;

/**
 * Modelo para la generación de informes
 * @author Alejandro Arrocha Hdez, Rosmen Ramos Díaz, Cristian Abad de Vera, Pablo García Gómez
 *
 */

public class PuntuacionesJugador {
	
	private String name;
	private int points;
	
	public PuntuacionesJugador() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor el nombre y los puntos de cada jugador
	 * @param name
	 * @param points
	 */
	public PuntuacionesJugador(String name, int points) {
		this.name = name;
		this.points = points;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	
}
