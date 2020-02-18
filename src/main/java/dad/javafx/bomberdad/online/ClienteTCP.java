package dad.javafx.bomberdad.online;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClienteTCP {
	private DataInputStream is;
	private DataOutputStream os;
	private Socket clientSocket;
	public InetSocketAddress addr;
	public static boolean bombaPuesta = false;
	public static int colocada = 0;
	public Recibir recibir;

	public ClienteTCP() {
		try {
			clientSocket = new Socket();
			addr = new InetSocketAddress("10.1.2.127", 5555);
			clientSocket.connect(addr);
			is = new DataInputStream(clientSocket.getInputStream());
			os = new DataOutputStream(clientSocket.getOutputStream());
			System.out.println("Conectado");
			os.writeUTF("l");
//			@SuppressWarnings("unused")
//			String line;
//			while (!(line = is.readUTF()).equals("2")) {
//				// waiting
//				os.writeUTF("l");
//			}
//			is.readUTF();
			recibir = new Recibir(this);
			recibir.start();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public DataInputStream getIs() {
		return is;
	}

	public void setIs(DataInputStream is) {
		this.is = is;
	}

	public DataOutputStream getOs() {
		return os;
	}

	public void setOs(DataOutputStream os) {
		this.os = os;
	}
	
	

}
