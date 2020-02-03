package dad.javafx.bomberdad;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.almasb.fxgl.core.math.FXGLMath;

public class GenerateMap {
	
	public static void newMap(int lvl) {
		try {
			String line;
			String map = "";
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
							line = replace(line,i,'b');
						}
					}
				}
				map = map+line+"\n";
			}
			b.close();
			File file2 = new File(
					GenerateMap.class.getClassLoader().getResource("./assets/levels/map.txt").getFile()
				);
			FileWriter fw = new FileWriter(file2);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(map);
			bw.close();
		} catch (FileNotFoundException e) {
			System.out.println("f1");
		} catch (IOException e) {
			System.out.println("f2");
		}
	}
	
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

}
