package dad.javafx.bomberdad.online;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import com.almasb.fxgl.dsl.FXGL;
import dad.javafx.bomberdad.BombermanType;
import dad.javafx.bomberdad.components.PlayerComponent;

public class ClienteTCP {
	private DataInputStream is;
	private DataOutputStream os;
	private Socket clientSocket;
	public static boolean bombaPuesta = false;
	public static int colocada = 0;
	public static ArrayList<String>listaMovimientos= new ArrayList<String>();

	public ClienteTCP() {
		try {
			clientSocket = new Socket();
			InetSocketAddress addr = new InetSocketAddress("10.1.2.127", 5555);
			clientSocket.connect(addr);
			is = new DataInputStream(clientSocket.getInputStream());
			os = new DataOutputStream(clientSocket.getOutputStream());
			System.out.println("Conectado");
			os.writeUTF("l1");
			@SuppressWarnings("unused")
			String line;
			while (!(line = is.readUTF()).equals("2")) {
				// waiting
				os.writeUTF("l1");
			}
			is.readUTF();
			Recibir recibir = new Recibir(is);
			recibir.start();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public ArrayList<String> getListaMovimientos() {
		return listaMovimientos;
	}

	public void setListaMovimientos(ArrayList<String> listaMovimientos) {
		this.listaMovimientos = listaMovimientos;
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

class Recibir extends Thread {
	public boolean continuar = true;
	DataInputStream is;

	public Recibir(DataInputStream is) {
		this.is = is;
	}

	@Override
	public void run() {
		super.run();
		while (continuar) {
			try {
				ClienteTCP.listaMovimientos.add(is.readUTF());
				
				//ClienteTCP.colocada = 1;
					
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
