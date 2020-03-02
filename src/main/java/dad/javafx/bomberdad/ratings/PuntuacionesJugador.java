package dad.javafx.bomberdad.ratings;


public class PuntuacionesJugador {
	private String name;
	private int points;
	
	public PuntuacionesJugador() {
		// TODO Auto-generated constructor stub
	}
	
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
