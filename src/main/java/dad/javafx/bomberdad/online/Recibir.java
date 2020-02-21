package dad.javafx.bomberdad.online;

import java.io.DataInputStream;
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

	@Override
	public void run() {
		super.run();
		while (continuar) {
			try {
//				PlayerPosition p = new PlayerPosition();
//				p= (PlayerPosition)is.readObject();
//				BombermanApp.actualizarPlayer(p);
				DynamicObject dO= (DynamicObject) is.readObject();
				String tipoObjeto=dO.getTipoObjeto();
				switch (tipoObjeto) {
				
				case "PlayerPosition":
				PlayerPosition p=(PlayerPosition) dO.getObjeto();
					BombermanApp.actualizarPlayer(p);
					break;
				case "PlaceBomb":
					
					break;

				case "PowerUpp":
					
					break;
				
				default:
					break;
				}


				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

}