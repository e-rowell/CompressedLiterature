/* Authors: Nicholas Hays and Ethan Rowell
 * Date: 2/9/2016
 * Assignment 3: Compressed Literature
 * Presented For: Dr. Chris Marriott
 */
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 
 * A coding tree object decodes characters represented as strings in ASCII into
 * a compressed size using Huffman's algorithm.
 * 
 * @author Nicholas Hays & Ethan Rowell
 */
public class CodingTree {

	// map of characters in the message to binary codes
	public Map<Character, String> codes;
	public Map<Character, Integer> charFreq;
	PriorityQueue<TreeNode> pq;
	StringBuilder myCharHuffCode;

	// message encoded using the Huffman codes.
	StringBuilder myEncodedText;
	private TreeNode root;

	/**
	 * Constructor that encodes the input message to compress. The constructor
	 * is responsible for calling all private methods that carry out the Huffman
	 * coding algorithm.
	 * 
	 * @param messaage
	 *            the message to be encoded.
	 */
	public CodingTree(String message) {
		myCharHuffCode = new StringBuilder();
		codes = new HashMap<>();
		charFreq = new HashMap<>();
		pq = new PriorityQueue<>();
		myEncodedText = new StringBuilder();
		parseChars(message);
		genFreq();
		buildHuffman();
	}

	/**
	 * Builds Huffman codes by recursively traversing the root, generating a '0'
	 * if left traverse or '1' if right traverse. Once a leaf node is
	 * discovered, the leaf nodes associated data (i.e character) is mapped to
	 * the generated code of traversal (i.e x -> 100110001110). On the way up
	 * the tree, if the huffman code length is greater than 0, the last '1' or
	 * '0' to be added will be deleted.
	 * 
	 * @param root
	 *            the TreeNode to build a code from.
	 */
	private void buildCodes(TreeNode root) {
		if (root == null)
			return;
		if (root.myLeft != null)
			myCharHuffCode.append(0);
		buildCodes(root.myLeft);
		if (root.myRight != null) {
			myCharHuffCode.append(1);
		}
		buildCodes(root.myRight);
		if (isLeaf(root)) {
			codes.put((Character) root.myData, myCharHuffCode.toString());
		}
		if (myCharHuffCode.length() > 0) {
			myCharHuffCode.deleteCharAt(myCharHuffCode.length() - 1);
		}
		return;
	}

	/**
	 * Determines if this node is a leaf node.
	 * 
	 * @param root
	 *            the TreeNode to be checked against.
	 * @return boolean determines if this TreeNode is a leaf node.
	 */
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

	/**
	 * Maps each character in the input message to its frequency. i.e (e ->
	 * 10560, x -> 123, , -> 56627...).
	 * 
	 * @param message
	 *            the message from the input string.
	 */
	private void parseChars(String message) {
		for (Character c : message.toCharArray()) {
			if (charFreq.get(c) == null) {
				charFreq.put((Character) c, 1);
			} else {
				charFreq.put((Character) c, charFreq.get(c).intValue() + 1);
			}
		}
	}

	/**
	 * Builds Huffman tree by creating a new node from combining the minimum 2
	 * character frequencies in the priority queue, and then adds the resulting
	 * new node back into the queue. Repeats until there is one node in the
	 * queue. This node represents the Huffman tree. Once the huffman tree is
	 * built in priority queue, the root node is then traversed by 'buildcodes'
	 * to generate all variable length huffman codes.
	 * 
	 */
	private void buildHuffman() {
		while (pq.size() > 1) {
			TreeNode node1 = getMin();
			TreeNode node2 = getMin();
			pq.add(combineWeights(node1, node2));
		}
		root = pq.peek();
		buildCodes(getMin());
	}

	public String encodeText(StringBuilder str) {

		for (Character character : str.toString().toCharArray()) {
			myEncodedText.append(this.codes.get(character));
		}
		return myEncodedText.toString();
	}

	/**
	 * Creates a new TreeNode by combining the weights of the input nodes, and
	 * adding each to its left and right subtree.
	 * 
	 * @param node1
	 *            TreeNode left subtree.
	 * @param node2
	 *            TreeNode right subtree.
	 * @return TreeNode with new frequency and node1 and node2 subtrees as
	 *         children.
	 */
	private TreeNode combineWeights(TreeNode node1, TreeNode node2) {
		int newFreq = node1.myFrequency + node2.myFrequency;
		TreeNode node = new TreeNode(null, newFreq, node1, node2);
		return node;
	}

	/**
	 * Finds the minimum node in the tree.
	 * 
	 * @return the TreeNode corresponding to the Node with the least character
	 *         frequency.
	 */
	private TreeNode getMin() {
		return pq.poll();
	}

	/**
	 * Transfers characters in hashmap to priority queue.
	 */
	private void genFreq() {
		for (Character c : charFreq.keySet()) {
			TreeNode treeNode = new TreeNode(c, charFreq.get(c), null, null);
			pq.add(treeNode);
		}
	}

	/**
	 * Decodes compressed Huffman file back to original work.
	 * 
	 * @param the encoded binary string.
	 * @return the original text.
	 */
	public String decode(String bits, Map<Character, String> codes) {
		StringBuilder decodedString = new StringBuilder();
		StringBuilder tempString = new StringBuilder();
		Map<String, Character> reverseMap = new HashMap<>();
		for(Map.Entry<Character, String> entry: codes.entrySet()) {
			reverseMap.put(entry.getValue(), entry.getKey());
		}
		Character temp;
		for (Character character : bits.toCharArray()) {
			tempString.append(character);
			temp = reverseMap.get(tempString.toString());
			if(temp != null) {
				decodedString.append(temp);
				tempString.setLength(0);
			}
		}
		return decodedString.toString();
	}

	/**
	 * Prototype for each node stored in the Huffman tree.
	 *
	 * @author Nicholas Hays & Ethan Rowell.
	 */
	class TreeNode implements Comparable {

		/**
		 * Constructor that stores data (i.e character), its frequency, and its
		 * left and right child nodes.
		 * 
		 * @param data the character to hold.
		 * @param freq this nodes data frequency.
		 * @param left this nodes left child.
		 * @param right this nodes right child.
		 */
		public TreeNode(Character data, int freq, TreeNode left, TreeNode right) {
			myFrequency = freq;
			myData = data;
			myLeft = left;
			myRight = right;
		}

		/**
		 * Compares this node frequency to other nodes Frequency.
		 * 
		 * @param otherNode
		 *            the other node to compare against.
		 * @return an integer value representing -1 if this node is less than
		 *         the other node, 0 if both nodes have same frequency, or 1 if
		 *         this node's frequency is greater than the other nodes
		 *         frequency.
		 */
		public int compareTo(Object x) {
			TreeNode test = (TreeNode) x;
			if (myFrequency > test.myFrequency)
				return 1;
			if (myFrequency == test.myFrequency)
				return 0;
			return -1;
		}

		public Character myData;
		TreeNode myLeft, myRight;
		int myFrequency;
		byte myCode;
	}

}
