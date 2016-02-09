/* Authors: Nicholas Hays and Ethan Rowell
 * Date: 2/9/2016
 * Assignment 3: Compressed Literature
 * Presented For: Dr. Chris Marriott
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	@SuppressWarnings("unchecked")
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
		
	/*	BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter("test.txt"));
			bw.write(str.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		decodeTree();
		
		
		CodingTree<Character> tree = new CodingTree<Character>(str.toString());
		Object[] nodes = tree.pq.toArray();
		for(int i = 0; i < nodes.length; i++) {
			CodingTree<Character>.TreeNode node = (CodingTree<Character>.TreeNode) nodes[i];
			System.out.println("character:" + node.myData + " weight:" + node.myFrequency);
		}
		System.out.println("Total char count: " + tree.charFreq.size());

	}
	private static void writeCodes(CodingTree<Character> tree) {
		StringBuilder codes = new StringBuilder();
		codes.append('{');
		for(String code: tree.codes.values()) {
			codes.append(tree.codes.get(code) + "=" + code + "/n");
		}
		codes.append('}');
	}
	private static void decodeTree() {
		File file = new File("");
		
	}

}
