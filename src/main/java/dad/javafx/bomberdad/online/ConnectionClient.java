package dad.javafx.bomberdad.online;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionClient extends Thread {

	public Socket client;

	ObjectOutputStream objectOut;
	ObjectInputStream objectIn;
	DynamicObject objetoDinamico;
	private int idPlayer;

	public ConnectionClient(Socket client, int id) throws IOException {
		super();
		this.client = client;
		this.idPlayer = id;
		objectOut = new ObjectOutputStream(client.getOutputStream());
		objectIn = new ObjectInputStream(client.getInputStream());
	}

	@Override
	public void run() {
//		String letra;
		while (true) {
			try {

				// lo actual
//				this.playerPos=(PlayerPosition)objectIn.readObject();
				// objectIn.readObject();
//				procesaDato(this.playerPos);
				this.objetoDinamico = (DynamicObject) objectIn.readObject();
				procesaDynamicObject(this.objetoDinamico);

			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

	private void procesaDynamicObject(DynamicObject dO) {

		DynamicObject nDo = new DynamicObject(dO.getTipoObjeto(), dO.getObjeto());

		switch (nDo.getTipoObjeto()) {

		case "getId":
			
			nDo.setIdJugador(idPlayer);
			try {
				this.objectOut.writeObject(nDo);
			} catch (IOException e) {

				e.printStackTrace();
			}
			break;
			
		case "getLista":
			
			nDo.setTipoObjeto(Server.listaSize() + "");
			try {
				this.objectOut.writeObject(nDo);
			} catch (IOException e) {

				e.printStackTrace();
			}
			break;
			
		case "PlayerPosition":
			nDo.setIdJugador(dO.getIdJugador());
			procesaPosicion(dO);
			break;
		case "PlaceBomb":

			break;

		case "PowerUpp":

			break;

		default:
			break;
		}

	}

	public void procesaPosicion(DynamicObject dO) {
		for (int i = 0; i < Server.clientes.size(); i++) {
			if (this.idPlayer != Server.clientes.get(i).getIdPlayer()) {
				try {
					Server.clientes.get(i).getObjectOut().writeObject(dO);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public ObjectOutputStream getObjectOut() {
		return objectOut;
	}

	public void setObjectOut(ObjectOutputStream objectOut) {
		this.objectOut = objectOut;
	}

	public ObjectInputStream getObjectIn() {
		return objectIn;
	}

	public void setObjectIn(ObjectInputStream objectIn) {
		this.objectIn = objectIn;
	}

	public int getIdPlayer() {
		return idPlayer;
	}

}
