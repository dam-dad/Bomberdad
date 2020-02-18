package dad.javafx.bomberdad.online;

import java.io.DataInputStream;
import java.io.IOException;

import dad.javafx.bomberdad.BombermanApp;

public class Recibir extends Thread {
	public boolean continuar = true;
	private DataInputStream is;
	public int id;

	public Recibir(ClienteTCP client) {
		is = new DataInputStream(client.getIs());
	}

	@Override
	public void run() {
		super.run();
		while (continuar) {
			try {
				String letra = is.readUTF();
				System.out.println(letra);
				try {
					id = Integer.parseInt(letra.charAt(1) + "");
				} catch (Exception e) {}
				BombermanApp.movePlayer(id, letra.charAt(0)+"");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}