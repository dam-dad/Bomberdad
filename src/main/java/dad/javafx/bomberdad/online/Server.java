package dad.javafx.bomberdad.online;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import dad.javafx.bomberdad.GenerateMap;

public class Server extends Thread{
	private static final int PORT = 5555;
	private static int id = 0;
	private static int numeroJugadoresPartida;
	public static String initialMap;
	public static ArrayList<ConnectionClient> clientes = new ArrayList<>();
	

	public Server(int numPlayers) {
		numeroJugadoresPartida = numPlayers;
	}
	
	@Override
	public void run() {
		try {
			iniciar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Genera un mapa inicial y acepta la conexión del cliente
	 */
	public void iniciar() throws IOException {
		generaMapaInicial();
		ServerSocket listener = new ServerSocket(PORT);
		while (clientes.size() < numeroJugadoresPartida) {
			System.out.println("[SERVER] Esperando clientes");
			Socket client = listener.accept();
			System.out.println("Conectando cliente");
			ConnectionClient clientThread = new ConnectionClient(client,id,numeroJugadoresPartida,initialMap);
			clientes.add(clientThread);
			clientThread.start();
			
			id++;
		}
		listener.close();
	
	}

	public static int listaSize() {
		return clientes.size();
	}

	private static void generaMapaInicial() {
		GenerateMap.newMap(0);
		initialMap=GenerateMap.getMap();
		
	}

	public static void setNumeroJugadoresPartida(int numeroJugadoresPartida) {
		Server.numeroJugadoresPartida = numeroJugadoresPartida;
	}
	
}
