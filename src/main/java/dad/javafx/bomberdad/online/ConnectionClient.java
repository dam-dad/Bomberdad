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
	
	
	

	/**
	 * Constructor para el manejador de las peticiones del cliente conectados al servidor
	 * @param client Socket de cliente en escucha con el servidor
	 * @param id Del jugador
	 * @param numeroJugadoresPartida Determina el número de jugadores que se conectarán a la partida
	 * @param mapaInicial String que contiene el mapa inicial de l partida
	 * 
	 */
	public ConnectionClient(Socket client, int id, int numeroJugadoresPartida,String mapaInicial) throws IOException {
		super();
		this.client = client;
		this.idPlayer = id;
		this.mapaInicial=mapaInicial;
		objectOut = new ObjectOutputStream(client.getOutputStream());
		objectIn = new ObjectInputStream(client.getInputStream());
		this.numeroJugadoresPartida = numeroJugadoresPartida;
	}
/**
 * Hilo a la escucha de los objetos DynamicObject que reciba del cliente, los procesará al llamar al método procesaDynamicObject
 */
	@Override
	public void run() {

		while (continuar) {
			try {
				this.objetoDinamico = (DynamicObject) objectIn.readObject();
				procesaDynamicObject(this.objetoDinamico);

			} catch (IOException | ClassNotFoundException e) { }

		}
	}
	/**
	 * 
	 * @param dO Objecto dinamico que guarda un objeto indeterminado,que se procesará gracias a una variable string,
	 * también propia del objeto, que determina que tipo de objeto está guardando
	 * Según el tipo de objecto se procesará de diferente maneras.
	 */

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

/**
 * Envía al cliente el numero de jugadores que se conectarán en la partida
 * @param dO Objeto de la petición.
 */


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

/**
 * Genera y reenvía todos los jugadores conectados un nuevo mapa
 * @param dO Objeto petición mapa
 */
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
/**
 * Reenvía el objeto a todos los jugadores en partida, salvo al cliente al que lo envió
 * @param dO Objeto que guarda las coordenadas del jugador al que se actualizará la posición
 */
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
		GenerateMap gm = new GenerateMap();
		gm.newMap(lvl);
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
