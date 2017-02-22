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
