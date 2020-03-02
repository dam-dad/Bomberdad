package dad.javafx.bomberdad.online;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import dad.javafx.bomberdad.BombermanApp;

public class Recibir extends Thread {
	public boolean continuar = true;
	private ObjectInputStream is;
	public int id;

	public Recibir(Socket client) {
		try {
			this.is = new ObjectInputStream(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		super.run();
		try {
			while (continuar) {
				System.out.println("antes leer primer recibir");
				DynamicObject dO = (DynamicObject) is.readObject();
				String tipoObjeto = dO.getTipoObjeto();
				switch (tipoObjeto) {

				case "PlayerPosition":
					PlayerPosition p = (PlayerPosition) dO.getObjeto();
					BombermanApp.actualizarPlayer(p);
					break;
				// Nuevo
				case "PlacePlayerBomb":
					PlayerPosition pBomba = (PlayerPosition) dO.getObjeto();
					BombermanApp.ponerBombaPlayer(pBomba);
					break;
				// Nuevo
				case "RequestNewMap":
					String mapa = (String) dO.getObjeto();
					System.out.println(mapa);
					BombermanApp.actualizaNuevoMapa(mapa);
					break;
				default:
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}