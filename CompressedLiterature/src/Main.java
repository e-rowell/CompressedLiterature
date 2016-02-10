import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.tree.TreeNode;


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
		/*	
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter("test.txt"));
			bw.write(str.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		CodingTree tree = new CodingTree(str.toString());
		Object[] nodes = tree.pq.toArray();
		for(int i = 0; i < nodes.length; i++) {
			CodingTree.TreeNode<Character> c =  (CodingTree.TreeNode<Character>) nodes[i];
			System.out.println("character: " + (int)(c.character) + " weight:" + c.weight);
		}
		System.out.println("Total char count: " + tree.charFreq.size());

	}

}
