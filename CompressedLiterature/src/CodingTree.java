import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

//
public class CodingTree<T> {

	// map of characters in the message to binary codes
	public Map<T, String> codes;
	public Map<T, Integer> charFreq;
	PriorityQueue<TreeNode> pq;
	StringBuilder myHuffCode;
	// message encoded using the Huffman codes.
	List<Byte> bits;

	/*
	 * a constructor that takes the text of a message to be compressed. The
	 * constructor is responsible for calling all private methods that carry out
	 * the Huffman coding algorithm.
	 */
	public CodingTree(String message) {
		myHuffCode = new StringBuilder();
		codes = new HashMap<>();
		charFreq = new HashMap<>();
		pq = new PriorityQueue<>();
		parseChars(message);
		// parseCommas(message);
		genFreq();
		buildHuffman();
	}

	private void buildCodes(TreeNode root) {
		if (root == null)
			return;
		if(root.myLeft != null) 
			myHuffCode.append(0);
		buildCodes(root.myLeft);
		if(root.myRight != null) {
			myHuffCode.append(0);
		}
		buildCodes(root.myRight);
		if(isLeaf(root)) {
			codes.put((T) root.myData, myHuffCode.toString());
		}
		myHuffCode.deleteCharAt(myHuffCode.length() - 1);
		return;
	}


	private boolean isLeaf(TreeNode root) {
		boolean theTruth = false;
		if (root.myLeft == null && root.myRight == null) {
			theTruth = true;	
		} 
		return theTruth;
	}
	
	public String decode(Character c) {
		return codes.get(c);
	}

	@SuppressWarnings("unchecked")
	private void parseChars(String message) {
		for (Character c : message.toCharArray()) {
			if (charFreq.get(c) == null) {
				charFreq.put((T) c, 1);
			} else {
				charFreq.put((T) c, charFreq.get(c).intValue() + 1);
			}
		}
	}

	/*private Integer parseCommas(String message) {
		charFreq.put('=', 0);
		for (char c : message.toCharArray()) {
			if (c == '=') {
				charFreq.put('=', charFreq.get('=') + 1);
			}
		}
		return charFreq.get('=');
	}*/

	private void buildHuffman() {
		while (pq.size() >= 1) {
			TreeNode node1 = getMin();
			TreeNode node2 = getMin();
			pq.add(combineWeights(node1, node2));
		}
		buildCodes(getMin());
	}

	private TreeNode combineWeights(TreeNode node1, TreeNode node2) {
		int newFreq = node1.myFrequency + node2.myFrequency;
		TreeNode node = new TreeNode(null, newFreq, node1, node2);
		return node;
	}

	private TreeNode getMin() {
		return pq.poll();
	}

	private void genFreq() {
		for (T c : charFreq.keySet()) {
			TreeNode treeNode = new TreeNode(c, charFreq.get(c), null, null);
			pq.add(treeNode);
		}
	}

	// (Optional)​String decode(String bits, Map<Character, String> codes)
	 class TreeNode implements Comparable<T> {
		public TreeNode(T data, int freq, TreeNode left, TreeNode right) {
			myFrequency = freq;
			myData = data;
			myLeft = left;
			myRight = right;
		}

		public int compareTo(Object x) {
			@SuppressWarnings("unchecked")
			TreeNode test = (TreeNode) x;
			if (myFrequency > test.myFrequency)
				return 1;
			if (myFrequency == test.myFrequency)
				return 0;
			return -1;
		}

		public T myData;
		TreeNode myLeft, myRight;
		int myFrequency;
		byte myCode;
	}

}
