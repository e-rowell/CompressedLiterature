import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

//
public class CodingTree {

	// map of characters in the message to binary codes
	public Map<Character, String> codes;
	public Map<Character, Integer> charFreq;
	PriorityQueue<TreeNode<Character>> pq;
	// message encoded using the Huffman codes.
	List<Byte> bits;

	/*
	 * a constructor that takes the text of a message to be compressed. The
	 * constructor is responsible for calling all private methods that carry out
	 * the Huffman coding algorithm.
	 */
	public CodingTree(String message) {
		codes = new HashMap<>();
		charFreq = new HashMap<>();
		pq = new PriorityQueue<>();
		parseChars(message);
		// parseCommas(message);
		createCodes();
	}

	private void parseChars(String message) {
		for (char c : message.toCharArray()) {
			if (charFreq.get(c) == null) {
				charFreq.put(c, 1);
			} else {
				charFreq.put(c, charFreq.get(c).intValue() + 1);
			}
		}
	}

	private Integer parseCommas(String message) {
		charFreq.put('=', 0);
		for (char c : message.toCharArray()) {
			if (c == '=') {
				charFreq.put('=', charFreq.get('=') + 1);
			}
		}
		return charFreq.get('=');
	}

	private void createCodes() {
		for (Character c : charFreq.keySet()) {
			TreeNode<Character> treeNode = new TreeNode<>(c, charFreq.get(c), null, null);
			pq.add(treeNode);
		}
	}

	// (Optional)​String decode(String bits, Map<Character, String> codes)
	public class TreeNode<T> implements Comparable {
		public TreeNode(char c, int freq, TreeNode l, TreeNode r) {
			frequency = freq;
			symbol = c;
			left = l;
			right = r;
		}

		public int compareTo(Object x) {
			TreeNode test = (TreeNode) x;
			if (frequency > test.frequency)
				return 1;
			if (frequency == test.frequency)
				return 0;
			return -1;
		}

		char symbol;
		TreeNode left, right;
		int frequency;
	}

}
