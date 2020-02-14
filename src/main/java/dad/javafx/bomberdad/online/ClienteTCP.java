package dad.javafx.bomberdad.online;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.almasb.fxgl.dsl.FXGL;

import dad.javafx.bomberdad.BombermanType;
import dad.javafx.bomberdad.components.PlayerComponent;

public class ClienteTCP {
private DataInputStream is;
private DataOutputStream os;
private Socket clientSocket;
public static boolean bombaPuesta=false;
public static int colocada=0;

public ClienteTCP() {
	try {
		clientSocket= new Socket();
		InetSocketAddress addr= new InetSocketAddress(5555);
		clientSocket.connect(addr);
		is= new DataInputStream(clientSocket.getInputStream());
		os= new DataOutputStream(clientSocket.getOutputStream());
		System.out.println("Conectado");
		Recibir recibir= new Recibir(is);
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
class Recibir extends Thread {
	public  boolean continuar= true;
	DataInputStream is;
	public Recibir(DataInputStream is ) {
		this.is=is;
	}

	@Override
	public void run() {
		super.run();
		while(continuar) {
			try {
				String letra= is.readUTF().charAt(0)+"";
				System.out.println(letra);
				if(letra.equals("d")) {
					
					FXGL.getGameWorld().getEntitiesByType(BombermanType.PLAYER).get(0).getComponent(PlayerComponent.class).getAstar().moveToRightCell();
				}else if ( letra.equals("e")) {
					System.out.println("dentro del bucle");
					ClienteTCP.bombaPuesta=true;
					ClienteTCP.colocada=1;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
	}
	
}
