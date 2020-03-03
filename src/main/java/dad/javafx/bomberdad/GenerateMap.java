package dad.javafx.bomberdad;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.almasb.fxgl.core.math.FXGLMath;

/**
 * Generados del mapa de forma aleatoria
 * @author Alejandro Arrocha Hdez, Rosmen Ramos Díaz, Cristian Abad de Vera, Pablo García Gómez
 *
 */

public class GenerateMap {
	//Nuevo, se pasa a static para poder acceder mas tarde
	private static String map= "";
	/**
	 * Función para la creación de mapas de manera aleatoria
	 * @param lvl Nivel del mapa donde leer el archivo para generar la nueva matriz
	 */
	public static void newMap(int lvl) {
		map="";
		try {
			String line;
			File file = new File(
					
					GenerateMap.class.getClassLoader().getResource("./assets/levels/"+lvl+".txt").getFile()
				);
			
			FileReader f = new FileReader(file);
			BufferedReader b = new BufferedReader(f);
			while ((line = b.readLine()) != null) {
				for (int i = 0; i < line.length(); i++) {
					if(line.charAt(i) == '0') {
						if (FXGLMath.randomBoolean()) {
							line = replace(line,i,'f');
						} else {
							if (FXGLMath.randomBoolean()) {
								line = replace(line,i,'b');
							} else {
								if (FXGLMath.randomBoolean()) {
									line = replace(line,i,'b');
								} else {
									if (FXGLMath.randomBoolean()) {
										line = replace(line,i,'r');
									} else {
										line = replace(line,i,'y');
									}
								}
							}
						}
					}
				}
				map = map+line+"\n";
			}
			b.close();
			//nuevo
			createMap(map);
		} catch (FileNotFoundException e) {
			System.out.println("f1");
		} catch (IOException e) {
			System.out.println("f2");
		}
	}
	/**
	 * Escribe en el archivo map.txt la matriz del mapa ya generado
	 * @param map Cadena que contiene la matriz del mapa
	 */
	public static void createMap(String map) {


		try {
			File file2 = new File(
					GenerateMap.class.getClassLoader().getResource("./assets/levels/map.txt").getFile()
				);
			
	
			FileWriter fw = new FileWriter(file2);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(map);
			
			bw.close();
			
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param str Cadena de texto completa
	 * @param index Posición del caracter a reemplazar
	 * @param replace Caracter al que se va a reemplazazr
	 * @return devuelve un String con los caracteres reemplazados
	 */
	
	private static String replace(String str, int index, char replace){     
	    if(str==null){
	        return str;
	    }else if(index<0 || index>=str.length()){
	        return str;
	    }
	    char[] chars = str.toCharArray();
	    chars[index] = replace;
	    return String.valueOf(chars);       
	}
	public static String getMap() {
		return map;
	}

}
