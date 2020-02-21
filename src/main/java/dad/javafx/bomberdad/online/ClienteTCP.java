package dad.javafx.bomberdad.online;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	private ObjectInputStream is;
	private ObjectOutputStream os;
	private Socket clientSocket;

	public InetSocketAddress addr;
	public static boolean bombaPuesta = false;
	public static int colocada = 0;
	public Recibir recibir;
	private int id=1;

	public static ArrayList<String>listaMovimientos= new ArrayList<String>();

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	public ObjectOutputStream getOs() {
		return os;
	}


	public ObjectInputStream getIs() {
		return is;
	}


	public void setIs(ObjectInputStream is) {
		this.is = is;
	}


	public void setOs(ObjectOutputStream os) {
		this.os = os;
	}
	public ClienteTCP() {
		try {
			clientSocket = new Socket();
			addr = new InetSocketAddress( 5555);
			clientSocket.connect(addr);
			is = new ObjectInputStream(clientSocket.getInputStream());
			os = new ObjectOutputStream(clientSocket.getOutputStream());
//			System.out.println("Conectado");
//			//coge id
//			os.writeUTF("i");
//			id=Integer.parseInt((String)is.readUTF());
//			//Espera jugadores
//			os.writeUTF("l");
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



	
	

}
