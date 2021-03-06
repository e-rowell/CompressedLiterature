
/* Authors: Nicholas Hays and Ethan Rowell
 * Date: 2/9/2016
 * Assignment 3: Compressed Literature
 * Presented For: Dr. Chris Marriott
 */

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
	    File inputFile = new File("WarAndPeace.txt");
		//File inputFile = new File("OurLittleRussianCousin.txt");
		File compressedFile = new File("compressed.txt");

		double startTime = System.currentTimeMillis();

		compressText(inputFile, compressedFile);

		double elapsedTime = (System.currentTimeMillis() - startTime) / 1000;

		System.out.println(String.format("Runtime: %.3f ms", elapsedTime));
		System.out.println(String.format("Orginal file size: %d bits", inputFile.length() * 8));
		System.out.println(String.format("Compressed file size: %d bits", compressedFile.length() * 8));
		System.out.println(String.format("Compression ratio: %.2f%%",
				(double) compressedFile.length() / inputFile.length() * 100));

	}

	private static void compressText(File inputFile, File outputFile) {
		BufferedReader reader = null;
		StringBuilder str = new StringBuilder();

		try {
			reader = new BufferedReader(new FileReader(inputFile));

			while (reader.ready()) {
				str.append((char) reader.read());
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		CodingTree tree = new CodingTree(str.toString());

		writeCodes(tree, str);
		String encodedText = encodeText(tree, str);
		//decodeText(tree, encodedText);
	}

	

	private static void writeCodes(CodingTree tree, StringBuilder sBuilder) {
		StringBuilder codes = new StringBuilder();
		codes.append("{");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("codes.txt"));
			for (Character code : tree.codes.keySet()) {
				codes.append(code + "=" + tree.codes.get(code) + ", " + '\n');
			}
			codes.append("}");
			bw.write(codes.toString());

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String encodeText(CodingTree tree, StringBuilder text) {

		String huffString = tree.encodeText(text);	
		// testMethod(huffString);
		
		int numBytes = huffString.length() / 8;
		ArrayList<Integer> binCodes = new ArrayList<>();
		String intString;
		for (int i = 0; i < numBytes; i++) {
			intString = huffString.substring(i * 8, (i + 1) * 8);
			binCodes.add(Integer.parseUnsignedInt(intString, 2));
		}
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("compressed.txt"));
			for (int bin32Str : binCodes) {
				bos.write(bin32Str);
			}
			bos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return huffString.toString();
	}
	
	private static void decodeText(CodingTree tree, String encodedText) {
		String originalText = tree.decode(encodedText, tree.codes);
		try {
			BufferedOutputStream origBos = new BufferedOutputStream(new FileOutputStream("decompressed.txt"));
			char[] chars = originalText.toString().toCharArray();
			for (char c : chars) {
				origBos.write(c);
			}
			origBos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void testMethod(String sBuilder) {
		try {
			BufferedWriter bWriter = new BufferedWriter(new FileWriter("test.txt"));
			bWriter.write(sBuilder.toString());
			bWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
