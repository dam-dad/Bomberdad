package dad.javafx.bomberdad.online;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.util.Platform;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.pathfinding.CellMoveComponent;

import dad.javafx.bomberdad.BombermanApp;
import dad.javafx.bomberdad.BombermanType;
import dad.javafx.bomberdad.components.PlayerComponent;

public class ConnectionClient extends Thread {

	Socket client;
	DataOutputStream dataOut;
	DataInputStream dataIn;
	int id;
	public DataOutputStream getDataOut() {
		return dataOut;
	}

	public void setDataOut(DataOutputStream dataOut) {
		this.dataOut = dataOut;
	}


	public ConnectionClient(Socket client,int id,BombermanApp game) throws IOException {
		this.client = client;
		this.id=id;
		dataIn = new DataInputStream(client.getInputStream());
		dataOut= new DataOutputStream(client.getOutputStream());
	}

	@Override
	public void run() {
		while (true) {
			String letra;
			try {
				letra = ""+dataIn.readUTF().charAt(0);
				switch (letra) {
				case "w":
					procesaDato(letra, id);
					break;
				case "a":
					procesaDato(letra, id);
					break;
				case "s":
					procesaDato(letra, id);
					break;
				case "d":
					procesaDato(letra, id);

					break;
				case "e":
					procesaDato(letra, id);
					break;

				default:
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	private void procesaDato(String accion,int id) {
		for(int i = 0; i< Server.clientes.size(); i ++) {
			
		try {
			Server.clientes.get(i).getDataOut().writeUTF(accion+""+id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		
	}

}
