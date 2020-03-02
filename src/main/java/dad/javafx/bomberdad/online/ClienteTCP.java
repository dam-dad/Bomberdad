package dad.javafx.bomberdad.online;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ClienteTCP {
	private ObjectInputStream is;
	private ObjectOutputStream os;
	private Socket clientSocket;

	public InetSocketAddress addr;
	public static boolean bombaPuesta = false;
	public static int colocada = 0;
	public Recibir recibir;
	private int id;
	private String mapa;

	public static ArrayList<String> listaMovimientos = new ArrayList<String>();

	public ClienteTCP(String ip, int port) {
		try {
			clientSocket = new Socket();
			addr = new InetSocketAddress(ip, port);
			clientSocket.connect(addr);
			is = new ObjectInputStream(clientSocket.getInputStream());
			os = new ObjectOutputStream(clientSocket.getOutputStream());
			inicializarPartida();
//			is.close();
//			os.close();
			System.out.println("start hilo");
			recibir = new Recibir(this);
			recibir.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public Recibir getRecibir() {
		return recibir;
	}

	private void inicializarPartida() {
		try {
			DynamicObject dOsolicitarId= new DynamicObject("getId","getId");
			os.writeObject(dOsolicitarId);
			DynamicObject leidO=(DynamicObject)is.readObject();
			System.out.println(leidO.getTipoObjeto());
			this.id=leidO.getIdJugador();
			System.out.println("DespuesSolicitud"+id);
			DynamicObject dOsolicitaLista= new DynamicObject("getLista","getLista");
			os.writeObject(dOsolicitaLista);
			DynamicObject leedOlista = (DynamicObject) is.readObject();
			System.out.println(leedOlista.getObjeto());
			while (!leedOlista.getTipoObjeto().equals("2")) {
				os.writeObject(dOsolicitaLista);
				leedOlista = (DynamicObject) is.readObject();
			}
			// nuevo
			if (id == 0) {
				DynamicObject dOSolicitaMapa = new DynamicObject("RequestNewMap", "0");
				os.writeObject(dOSolicitaMapa);
			}
//				is.readObject();
			DynamicObject leeMapa=(DynamicObject)is.readObject();
			while(!leeMapa.getTipoObjeto().equals("RequestNewMap")) {
				leeMapa=(DynamicObject)is.readObject();
	
				}
			setMapa((String)leeMapa.getObjeto());
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public String getMapa() {
		return mapa;
	}

	public void setMapa(String mapa) {
		this.mapa = mapa;
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
