package dad.javafx.bomberdad.online;

import java.io.IOException;
import java.io.ObjectInputStream;

import dad.javafx.bomberdad.BombermanApp;

public class Recibir extends Thread {
	public boolean continuar = true;
	private ObjectInputStream is;
	public int id;

	public Recibir(ClienteTCP client) {
			is = client.getIs();
	}
/**
 * Recibe los objetos DynamicObject y los procesará dependiendo el tipo de objeto que guarde
 */
	@Override
	public void run() {
		super.run();
		while (continuar) {
			try {
				DynamicObject dO= (DynamicObject) is.readObject();
				String tipoObjeto=dO.getTipoObjeto();
				switch (tipoObjeto) {

				case "PlayerPosition":
					PlayerPosition p = (PlayerPosition) dO.getObjeto();
					BombermanApp.actualizarPlayer(p);
					break;
				case "PlacePlayerBomb":
					PlayerPosition pBomba = (PlayerPosition) dO.getObjeto();
					BombermanApp.ponerBombaPlayer(pBomba);
					break;
				case "RequestNewMap":
					String mapa = (String) dO.getObjeto();
					BombermanApp.actualizaNuevoMapa(mapa);
					break;

				default:
					break;
				}
			}catch (IOException e) {} 
			catch (ClassNotFoundException e1) {}
		}

	}

}