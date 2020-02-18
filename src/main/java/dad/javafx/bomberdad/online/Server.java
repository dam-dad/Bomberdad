package dad.javafx.bomberdad.online;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.almasb.fxgl.app.GameApplication;

import dad.javafx.bomberdad.BombermanApp;

public class Server {
	private static final int PORT = 5555;
	private static int id=0;


	public static ArrayList<ConnectionClient> clientes = new ArrayList<>();
	public static void iniciar() throws IOException {
		ServerSocket listener = new ServerSocket(PORT);
		while (clientes.size()<2) {
			System.out.println("[SERVER] Esperando clientes");
			Socket client = listener.accept();
			System.out.println("Conectando cliente");
			ConnectionClient clientThread = new ConnectionClient(client);
			clientes.add(clientThread);
			clientThread.start();
			id++;
		}
		


	}
	public static int listaSize() {
		return clientes.size();
	}
	public static void main(String[] args) {
		try {
			iniciar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
