package dad.javafx.bomberdad.ratings;

import java.util.ArrayList;

public class Puntuaciones {
	private ArrayList<ArrayList<String>> points = new ArrayList<ArrayList<String>>();

	public Puntuaciones() {
		points.add(new ArrayList<String>());
		points.get(0).add("");
		points.get(0).add("0");
		points.add(new ArrayList<String>());
		points.get(1).add("");
		points.get(1).add("0");
//		points.add(new ArrayList<String>());
//		points.get(2).add("");
//		points.get(2).add("0");
//		points.add(new ArrayList<String>());
//		points.get(3).add("");
//		points.get(3).add("0");
	}
	
	public ArrayList<ArrayList<String>> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<ArrayList<String>> points) {
		this.points = points;
	}
}
