/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Vibhu Appalaraju
 * vka249
 * 16235
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL:https://github.com/vibhuappalaraju/422C-Project-3.git
 * Spring 2017
 */
package assignment3;

import java.util.ArrayList;

public class Node {
	public Node parentNode = null;
	public String word;
	public ArrayList<Node> neighbors = new ArrayList<Node>();
	Node(String s){
		this.word = s;
	}
}
