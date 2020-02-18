package dad.javafx.bomberdad.online;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionClient extends Thread {

	public Socket client;
	DataOutputStream dataOut;
	DataInputStream dataIn;
	private int id;

	public DataOutputStream getDataOut() {
		return dataOut;
	}

	public void setDataOut(DataOutputStream dataOut) {
		this.dataOut = dataOut;
	}

	public ConnectionClient(Socket client, int id) throws IOException {
		super();
		this.client = client;
		this.id = id;

		dataIn = new DataInputStream(client.getInputStream());
		dataOut = new DataOutputStream(client.getOutputStream());
	}

	@Override
	public void run() {
		String letra;
		while (true) {
			try {
				letra = dataIn.readUTF();
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
				Server.clientes.get(i).getDataOut().writeUTF(accion + id);
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
