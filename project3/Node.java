package assignment3;

import java.util.ArrayList;

public class Node {
	public Node parentNode;
	public boolean visited = false;
	public String word;
	Node(String s){
		this.word = s;
	}
}
