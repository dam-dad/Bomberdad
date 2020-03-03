package dad.javafx.bomberdad.online;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import com.almasb.fxgl.dsl.FXGL;
import javafx.util.Duration;

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
/**
 * Se conecta al servidor, llama a inicializarPartidad() para preparar la partida online
 * @param ip Ip del servidor a conectarse
 * @param port Puerto del servidor 
 */
	public ClienteTCP(String ip, int port) {
		try {
			clientSocket = new Socket();
			addr = new InetSocketAddress(ip, port);
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


/**
 * Realiza una serie de peticiones iniciales al servidor para poder cargar la partida, como el id del personaje que le toca y el mapa inicial.
 * También esperará hasta que estén los dos jugadores conectados al servidor, para iniciarse de manera sincronizada.
 */
	private void inicializarPartida() {
		try {
			DynamicObject dOsolicitarId= new DynamicObject("getId","getId");
			os.writeObject(dOsolicitarId);
			DynamicObject leidO=(DynamicObject)is.readObject();
			this.id=leidO.getIdJugador();
			DynamicObject dOsolicitaLista= new DynamicObject("getLista","getLista");
			os.writeObject(dOsolicitaLista);
			DynamicObject leedOlista = (DynamicObject) is.readObject();
			
			while (!leedOlista.getTipoObjeto().equals("2")) {
				os.writeObject(dOsolicitaLista);
				leedOlista = (DynamicObject) is.readObject();
			}
		
				DynamicObject dOSolicitaMapa = new DynamicObject("RequestInicialMap", "0");
				os.writeObject(dOSolicitaMapa);
			DynamicObject leeMapa=(DynamicObject)is.readObject();
			while(!leeMapa.getTipoObjeto().equals("RequestInicialMap")) {
				leeMapa=(DynamicObject)is.readObject();
	
				}
			setMapa((String)leeMapa.getObjeto());
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public Recibir getRecibir() {
		return recibir;
	}

	
	public void closeCient() {
		try {
			DynamicObject dOTerminar= new DynamicObject("Terminar","Terminar");
			os.writeObject(dOTerminar);
			recibir.setFinal(false);
			clientSocket.close();
			FXGL.getGameTimer().runOnceAfter(() -> {
				FXGL.getGameController().gotoMainMenu();
			}, Duration.millis(10));
		} catch (IOException e) {
			FXGL.getGameTimer().runOnceAfter(() -> {
				FXGL.getGameController().gotoMainMenu();
			}, Duration.millis(10));
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
