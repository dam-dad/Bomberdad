package dad.javafx.bomberdad.online;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import dad.javafx.bomberdad.BombermanApp;
import dad.javafx.bomberdad.PlayerPosition;

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
				PlayerPosition p = new PlayerPosition();
				p= (PlayerPosition)is.readObject();
				//System.out.println(p.getPositionX());
	
				BombermanApp.actualizarPlayer(p);
//				String letra = is.readUTF();
//				System.out.println(letra);
//				try {
//					id = Integer.parseInt(letra.charAt(1) + "");
//				} catch (Exception e) {}
				//BombermanApp.movePlayer(id, letra.charAt(0)+"");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

}