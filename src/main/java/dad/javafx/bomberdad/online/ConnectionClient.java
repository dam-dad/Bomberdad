package dad.javafx.bomberdad.online;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionClient extends Thread {

	public Socket client;
	DataOutputStream dataOut;
	DataInputStream dataIn;
	ObjectOutputStream objectOut;
	ObjectInputStream objectIn;
	private PlayerPosition playerPos;
	private int idPlayer;



	public ConnectionClient(Socket client, int id) throws IOException {
		super();
		this.client = client;
		this.idPlayer = id;

		dataIn = new DataInputStream(client.getInputStream());
		dataOut = new DataOutputStream(client.getOutputStream());
		objectOut = new ObjectOutputStream(client.getOutputStream());
		objectIn = new ObjectInputStream(client.getInputStream());
	}

	@Override
	public void run() {
//		String letra;
		while (true) {
			try {
//				letra = dataIn.readUTF();
//				switch (letra) {
//				case "i":
//					dataOut.writeUTF(this.id +"");
//				case "l":
//					procesaLista(letra, id);
//					break;
//				default:
//					break;
//				}
				this.playerPos=(PlayerPosition)objectIn.readObject();
				objectIn.readObject();
				procesaDato(this.playerPos);
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

	private void procesaDato(PlayerPosition playerPos) {

		PlayerPosition objetoEnviar= new PlayerPosition(playerPos.getPositionX(),playerPos.getPositionY(),playerPos.getIdEntity());
		for (int i = 0; i < Server.clientes.size(); i++) {
			try {
				if(playerPos.getIdEntity()!= Server.clientes.get(i).getIdPlayer()) {
					Server.clientes.get(i).getObjectOut().writeObject(objetoEnviar);
				}
			} catch (IOException e) {
				e.printStackTrace();
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

	public DataOutputStream getDataOut() {
		return dataOut;
	}

	public void setDataOut(DataOutputStream dataOut) {
		this.dataOut = dataOut;
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
