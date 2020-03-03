package dad.javafx.bomberdad.online;

import java.io.Serializable;

public class PlayerPosition implements Serializable {


	private static final long serialVersionUID = 1L;
	private double positionX;
	private double positionY;
	private int idEntity;

	public PlayerPosition() {
	}
	public PlayerPosition(double positionX, double positionY, int idEntity) {
		this.positionX=positionX;
		this.positionY=positionY;
		this.idEntity=idEntity;
	}
	public double getPositionX() {
		return positionX;
	}
	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}
	public double getPositionY() {
		return positionY;
	}
	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}
	public int getIdEntity() {
		return idEntity;
	}
	public void setIdEntity(int idEntity) {
		this.idEntity = idEntity;
	}
	

}
