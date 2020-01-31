package dad.javafx.bomberdad;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateMap {
	public static void main(String[] args) {
		try {
			String line;
			String map = "";
			File file = new File(
					GenerateMap.class.getClassLoader().getResource("./assets/levels/0.txt").getFile()
				);
			FileReader f = new FileReader(file);
			BufferedReader b = new BufferedReader(f);
			while ((line = b.readLine()) != null) {
				map = map+line+"\n";
			}
			map = map.replace('0', 'b');
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
	public  void newMap() {
		
	}

}
