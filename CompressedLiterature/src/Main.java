import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		BufferedReader reader = null;
		StringBuilder str = new StringBuilder();
		try {
			reader = new BufferedReader(new FileReader("WarAndPeace.txt"));
			while (reader.ready()) {
				str.append(reader.readLine() + System.lineSeparator());
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter("test.txt"));
			bw.write(str.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		CodingTree tree = new CodingTree(str.toString());
	}

}
