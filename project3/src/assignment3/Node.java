/* Node.java
 * EE422C Project 3 submission by
 * Vibhu Appalaraju
 * vka249
 * 16235
 * Megan Cooper
 * mlc4285
 * <16235
 * Slip days used: <0>
 * Git URL:https://github.com/vibhuappalaraju/422C-Project-3.git
 * Spring 2017
 */
package assignment3;

import java.util.ArrayList;
/* This is the general structure of each word node
 * Used in BFS , utilizes parentNode to travese path to get the word ladder
 */
public class Node {
	public Node parentNode = null;
	public String word;
	public ArrayList<Node> neighbors = new ArrayList<Node>();
	Node(String s){
		this.word = s;
	}
}
