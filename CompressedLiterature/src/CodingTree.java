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

	private void createCodes() {
		for (Character c : charFreq.keySet()) {
			TreeNode<Character> treeNode = new TreeNode<>();
			treeNode.character = c;
			treeNode.weight = charFreq.get(c);
			pq.add(treeNode);
		}
		ArrayList<TreeNode<?>> nodes = new ArrayList<TreeNode<?>>();
		
		Object[] objArray = pq.toArray();
		for(int i = 0; i < objArray.length; i++) {
			Object object = objArray[i];
			if (object instanceof TreeNode<?>) {
				nodes.add((TreeNode<?>) object);
			}
		}
		Arrays.sort(nodes.toArray());
	}
		
	
	// (Optional)​String decode(String bits, Map<Character, String> codes)

	private class TreeNode<T> implements Comparable<T> {
		TreeNode<T> root;
		TreeNode<T> left;
		TreeNode<T> right;
		T character;
		Integer weight;

		public TreeNode() {

		}

		@Override
		public int compareTo(T o) {
			Integer otherInt = (Integer) o;
			if (this.weight < otherInt)
				return -1;
			else if (this.weight == otherInt)
				return 0;
			else
				return 1;
		}
	}

}
