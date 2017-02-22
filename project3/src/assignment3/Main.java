/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL:
 * Fall 2016
 */

package assignment3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.*;

public class Main {
	static ArrayList<String> wordladder = new ArrayList<String>();
	// static variables and constants only here.

	public static void main(String[] args) throws Exception {

		Scanner kb; // input Scanner for commands
		PrintStream ps; // output file
		// If arguments are specified, read/write from/to files instead of Std
		// IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps); // redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin
			ps = System.out; // default to Stdout
		}

		wordladder = parse(kb);
		if (wordladder == null) {
			return;
		}

		initialize();
		printLadder(getWordLadderDFS(wordladder.get(0), wordladder.get(1)));
		System.out.println("");
		printLadder(getWordLadderBFS(wordladder.get(0), wordladder.get(1)));

		// TODO methods to read in words, output ladder
	}

	public static void initialize() {

		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests. So call it
		// only once at the start of main.
	}

	/**
	 * @param keyboard
	 *            Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. If
	 *         command is /quit, return empty ArrayList.
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		// TO DO
		ArrayList<String> kb = new ArrayList<String>();
		kb.add(keyboard.next().toLowerCase());

		if (kb.contains("/quit")) {
			ArrayList<String> emptylist = new ArrayList<String>();
			return emptylist;
		} else {
			kb.add(keyboard.next().toLowerCase());
			if (kb.contains("/quit")) {
				ArrayList<String> emptylist = new ArrayList<String>();
				return emptylist;
			}

		}
		return kb;
	}
/**
 * 
 * @param start 
 * @param end
 * @return
 */
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		Set<String> Dictionary = makeDictionary();


		ArrayList<String> printlist = new ArrayList<String>();
		printlist.add(start);
		printlist = DFS(start, end, printlist, Dictionary);
		if(printlist == null){
			printlist = new ArrayList<String>();
			printlist.add(start.toLowerCase());
		printlist.add(end.toLowerCase());}
		return printlist;
		
	}
	
/**
	 * Uses breadth first search to find word ladder between start and end
	 * @param start , the first word to begin the word search
	 * @param end , the last word to end the word ladder
	 * @return an ArrayList<String> containing the word ladder
	 */
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
    	Queue<Node> Queue = new LinkedList<Node>();
    	ArrayList<String> wordLadder = new ArrayList<String>();
		Set<String> dict = makeDictionary();
		Set<String> visitedWords = new HashSet<String>();
	//	ArrayList<Node> neighbors = new ArrayList<Node>();
		
		Node head = new Node(start);
		
		Queue.add(head);				//Add starting word to the Queue
		visitedWords.add(head.word);	//Add to visited set so no word repeats
		head.parentNode = null;			
		
		
		while(!Queue.isEmpty()){
			
			head = Queue.remove();		//Remove top element from the queue
			dict.remove(head.word.toUpperCase());
			if(head.word.equals(end)){	//End search if we found the end word
				break;
			}
		
			head.neighbors = allNeighbors(head.word, dict); //find all words in dictionary with one letter difference
			
			for(Node n : head.neighbors){ 				//Iterate through all "neighbors"
				
				if(!visitedWords.contains(n.word)){
					visitedWords.add(n.word);
					n.parentNode = head;			
					Queue.add(n);		
					
				}
			}
		
		}
		
		
		
		if(head.word.equals(end)){
			while(head != null){
				wordLadder.add(head.word);
				head = head.parentNode;
			}
			Collections.reverse(wordLadder);
			return wordLadder;
		}
		
		wordLadder.clear();
		wordLadder.add(start);
		wordLadder.add(end);
		return wordLadder; 
	}
    
    /**
     * Locates all words in dictionary with a one letter difference between 
     * them and the String; returns an ArrayList<Node> array 
     * @param s : the String we are finding neighbors of
     * @param dict : the Dictionary with available words
     * @return	AArrayList<Node> array with all neighbor nodes
     */
	public static ArrayList<Node> allNeighbors(String s, Set<String> dict){
    	ArrayList<Node> n = new ArrayList<Node>();
    	StringBuilder wl = new StringBuilder(s);
    	
    	for(int x = 0; x < s.length(); x++){
    		for(char y = 'a'; y <= 'z'; y++){
    			wl.setCharAt(x, y);
    			String temp = wl.toString();
    			//Only adds words within the dictionary
    			
				if(dict.contains(temp.toUpperCase()) && oneLetterDifference(s, temp)){
					Node neigh = new Node(temp);
					n.add(neigh);
					dict.remove(temp.toUpperCase());
				}
				wl = new StringBuilder(s);
    		}
    	}
    	
    	return n;
    }

	public static Set<String> makeDictionary() {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner(new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
/**
 * 
 * @param ladder
 */
	public static void printLadder(ArrayList<String> ladder) {
		if (ladder.size() <=2 ) {
			System.out.print("no word ladder can be found between " + ladder.get(0) + " and " + ladder.get(1));

		} else {
			System.out.println("a " + (ladder.size()) + "-rung word ladder exists between " + ladder.get(0) + " and "
					+ ladder.get(ladder.size()-1));
			for (String s : ladder) {
				System.out.println(s);
			}
		}
	}
	
/**
 * 
 * @param start
 * @param end
 * @param visited
 * @param Dictionary
 * @return
 */
	public static ArrayList<String> DFS(String start, String end, ArrayList<String> visited, Set<String> Dictionary) {

		Dictionary.remove(start.toUpperCase());
		if (start.equals(end)) {
			return visited;

		}

		for (int x = 0; x < start.length(); x++) {
			StringBuilder startword = new StringBuilder(start);
			for (int y = 0; y < 26; y++) {

				startword.setCharAt(x, (char) ('a' + y));
				if (Dictionary.contains(startword.toString().toUpperCase())
						&& !visited.contains(startword.toString())) {

					visited.add(startword.toString());
					Dictionary.remove(startword.toString().toUpperCase());

					ArrayList<String> visitladder = new ArrayList<String>();
					visitladder = DFS(startword.toString(), end, visited, Dictionary);
					if (visitladder == null) {
						visited.remove(startword.toString());

					} else {
						
						return visitladder;
					}

				}

			}

		}
		return null;

	}

	/**
	 * This method determines whether there is a one letter
	 * difference between String a and String b
	 * @param a String
	 * @param b	String
	 * @return	true if one letter difference, false otherwise
	 */
	private static boolean oneLetterDifference(String a, String b){
		int difference = 0;
		for(int x = 0; x < a.length(); x++){
			if(a.charAt(x) != b.charAt(x)){
				difference++;
			}
		}
		
		if(difference == 1){
			return true;
		}
		return false;
	}

}
