package dad.javafx.bomberdad.online;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
	private int id;

	public static ArrayList<String>listaMovimientos= new ArrayList<String>();

	public ClienteTCP() {
		try {
			clientSocket = new Socket();
			addr = new InetSocketAddress(5555);
			clientSocket.connect(addr);
			is = new ObjectInputStream(clientSocket.getInputStream());
			os = new ObjectOutputStream(clientSocket.getOutputStream());
			inicializarPartida();
			recibir = new Recibir(this);
			recibir.start();
		

		} catch (Exception e) {
			// TODO: handle exception
		}
	}


	private void inicializarPartida() {
			DynamicObject dOsolicitarId= new DynamicObject("getId", "getId");
		try {
			System.out.println("Solicitar id");
			os.writeObject(dOsolicitarId);
			System.out.println("Recibiendo id");
			DynamicObject leidO=(DynamicObject)is.readObject();
			this.id=leidO.getIdJugador();
			System.out.println("ID OBTENIDA" + id);
			System.out.println("Solicitar lista");
			DynamicObject dOsolicitaLista= new DynamicObject("getLista","getLista");
			os.writeObject(dOsolicitaLista);
			System.out.println("Leyendo lista");
			DynamicObject leedOlista=(DynamicObject)is.readObject();
			while (!leedOlista.getTipoObjeto().equals("2")) {
				os.writeObject(dOsolicitaLista);
				leedOlista=(DynamicObject)is.readObject();
			}
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Finalizando inicializarS");
		
	}
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

}
