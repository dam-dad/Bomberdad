package dad.javafx.bomberdad.online;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClienteTCP {
	private DataInputStream is;
	private DataOutputStream os;
	static Socket clientSocket;

	public ClienteTCP() {
		try {
			clientSocket = new Socket();
			System.out.println("Estableciendo la conexión");
			InetSocketAddress addr = new InetSocketAddress("10.1.2.127", 5555);
			clientSocket.connect(addr);
			is = new DataInputStream(clientSocket.getInputStream());
			os = new DataOutputStream(clientSocket.getOutputStream());
			System.out.println("Conectado");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeOS(String line,int index) {
		try {
			this.os.writeUTF(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setOs(DataOutputStream os) {
		this.os = os;
	}
	
}
