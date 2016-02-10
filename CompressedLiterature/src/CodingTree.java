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
		//parseCommas(message);
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
			TreeNode<Character> treeNode = new TreeNode<>();
			treeNode.character = c;
			treeNode.weight = charFreq.get(c);
			pq.add(treeNode);
		}
	}

	// (Optional)​String decode(String bits, Map<Character, String> codes)

	class TreeNode<T> implements Comparable<TreeNode<T>> {
		TreeNode<T> root;
		TreeNode<T> left;
		TreeNode<T> right;
		T character;
		Integer weight;

		public TreeNode() {

		}

		@Override
		public int compareTo(TreeNode<T> o) {
			return Integer.compare(this.weight, (Integer) o.weight);
		}
	}
}
