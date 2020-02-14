package dad.javafx.bomberdad.online;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.util.Platform;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.pathfinding.CellMoveComponent;

import dad.javafx.bomberdad.BombermanApp;
import dad.javafx.bomberdad.BombermanType;
import dad.javafx.bomberdad.components.PlayerComponent;

public class ConnectionClient extends Thread {

	Socket client;
	DataOutputStream dataOut;
	DataInputStream dataIn;

	public DataOutputStream getDataOut() {
		return dataOut;
	}

	public void setDataOut(DataOutputStream dataOut) {
		this.dataOut = dataOut;
	}

	public ConnectionClient(Socket client) throws IOException {
		this.client = client;

		dataIn = new DataInputStream(client.getInputStream());
		dataOut = new DataOutputStream(client.getOutputStream());
	}

	@Override
	public void run() {
		String letra;
		int id;
		String cadena;
		while (true) {

			try {
				cadena = dataIn.readUTF();
				letra = cadena.charAt(0) + "";
				id = Integer.parseInt(cadena.charAt(1) + "");
				switch (letra) {
				case "l":
					procesaLista(letra, id);
					break;
				case "w":
					procesaDato(letra, id);
					break;
				case "a":
					procesaDato(letra, id);
					break;
				case "s":
					procesaDato(letra, id);
					break;
				case "d":
					procesaDato(letra, id);

					break;
				case "e":
					procesaDato(letra, id);
					break;

				default:
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private void procesaDato(String accion, int id) {
		
		for (int i = 0; i < Server.clientes.size(); i++) {

			try {
				Server.clientes.get(i).getDataOut().writeUTF(accion + "" + id);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
	}

	private void procesaLista(String accion, int id) {
		for (int i = 0; i < Server.clientes.size(); i++) {

			try {
				Server.clientes.get(i).getDataOut().writeUTF(Server.listaSize() + "");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
