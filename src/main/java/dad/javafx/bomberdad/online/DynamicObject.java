package dad.javafx.bomberdad.online;

import java.io.Serializable;

/**
 * Objectos dinámicos para el envio de distintos objetos por sockets
 * @author Alejandro Arrocha Hdez, Rosmen Ramos Díaz, Cristian Abad de Vera, Pablo García Gómez
 *
 */

public class DynamicObject implements Serializable{

	private static final long serialVersionUID = 1L;
	String tipoObjeto;
	int idJugador;
	Object objeto;


	public DynamicObject(String cadena,int idJugador, Object objeto) {
		this.tipoObjeto=cadena;
		this.objeto=objeto;
		this.idJugador=idJugador;
	}
	public DynamicObject(String cadena, Object objeto) {
		this.tipoObjeto=cadena;
		this.objeto=objeto;

	}
	public String getTipoObjeto() {
		return tipoObjeto;
	}
	public void setTipoObjeto(String tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}
	public Object getObjeto() {
		return objeto;
	}
	public void setObjeto(Object objeto) {
		this.objeto = objeto;
	}
	public int getIdJugador() {
		return idJugador;
	}
	public void setIdJugador(int idJugador) {
		this.idJugador = idJugador;
	}

}
