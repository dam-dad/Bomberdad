package dad.javafx.bomberdad.online;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import dad.javafx.bomberdad.GenerateMap;

/**
 * Moderador entre cliente y servidor
 * @author Alejandro Arrocha Hdez, Rosmen Ramos Díaz, Cristian Abad de Vera, Pablo García Gómez
 *
 */

public class ConnectionClient extends Thread {

	public Socket client;
	private boolean continuar = true;
	ObjectOutputStream objectOut;
	ObjectInputStream objectIn;
	DynamicObject objetoDinamico;
	private int idPlayer;
	private int numeroJugadoresPartida;
	private String mapaInicial;
	public ConnectionClient(Socket client, int id, int numeroJugadoresPartida,String mapaInicial) throws IOException {
		super();
		this.client = client;
		this.idPlayer = id;
		this.mapaInicial=mapaInicial;
		objectOut = new ObjectOutputStream(client.getOutputStream());
		objectIn = new ObjectInputStream(client.getInputStream());
		this.numeroJugadoresPartida = numeroJugadoresPartida;
		System.out.println(id);
	}

	@Override
	public void run() {

		while (continuar) {
			try {
				this.objetoDinamico = (DynamicObject) objectIn.readObject();
				procesaDynamicObject(this.objetoDinamico);

			} catch (IOException | ClassNotFoundException e) { }

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
		// nuevo
		case "PlacePlayerBomb":
		case "PlayerPosition":
			nDo.setIdJugador(dO.getIdJugador());
			procesaPosicion(nDo);
			break;
		case "RequestNewMap":
			procesaMapa(nDo);
			break;
		case "RequestInicialMap":
			try {
				nDo.setObjeto(mapaInicial);
				this.objectOut.writeObject(nDo);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "NumeroJugadores":
			procesaNumJugadores(nDo);
			break;
		case "Terminar":
			finalizarPartida(nDo);
			continuar = false;
			break;
		default:
			break;
		}
	}

	private void finalizarPartida(DynamicObject dO) {
//		dO.setTipoObjeto("Terminar");
//		for (int i = 0; i < Server.clientes.size(); i++) {
//			if (this.idPlayer != Server.clientes.get(i).getIdPlayer()) {
//				try {
//					Server.clientes.get(i).getObjectOut().writeObject(dO);
//				} catch (IOException e) {
//				}
//			} else {
//				Server.clientes.remove(i);
//			}
//		}
//		try {
//			client.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		if (idPlayer == 0) {
			Server.closeSockets();
		}
	}

	private void procesaNumJugadores(DynamicObject dO) {
		DynamicObject dOenvio = new DynamicObject("NumeroJugadores", numeroJugadoresPartida);
		try {
			this.objectOut.writeObject(dOenvio);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// nuevo
	private void procesaMapa(DynamicObject dO) {
		int lvl = Integer.parseInt((String) dO.getObjeto());
		String map = generaMapa(lvl);

		DynamicObject dOenvio = new DynamicObject("RequestNewMap", map);
		for (int i = 0; i < Server.clientes.size(); i++) {
			try {
				Server.clientes.get(i).getObjectOut().writeObject(dOenvio);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void procesaPosicion(DynamicObject dO) {

		for (int i = 0; i < Server.clientes.size(); i++) {
		
			if (this.idPlayer != Server.clientes.get(i).getIdPlayer()) {
				try {
					
					Server.clientes.get(i).getObjectOut().writeObject(new DynamicObject(dO.getTipoObjeto(),dO.getObjeto()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String generaMapa(int lvl) {
		GenerateMap.newMap(lvl);
		return GenerateMap.getMap();
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

	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}

}
