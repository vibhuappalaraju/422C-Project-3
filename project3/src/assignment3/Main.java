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
		if (wordladder.size()==0) {
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
 * This function returns the DFS Ladder and if the DFS ladder is null then they add the start and the end word
 * @param start is the starting String for the word ladder
 * @param end is the ending String for the word ladder
 * @return ArrayList getWordLadderDFS which contains the start and the end word if there is no word ladder and
 * the full ladder if a word ladder exists between start and end.
 */
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		Set<String> Dictionary = makeDictionary();


		ArrayList<String> beentolist = new ArrayList<String>();
		beentolist.add(start);// add the start word to the been to arraylist and passes it as a paramater with the start word, end word and dictionary.
		beentolist = DFS(start, end, beentolist, Dictionary);
		if(beentolist == null){// there is no word ladder so we add the start word and end word and return the arraylist
			beentolist = new ArrayList<String>();
			beentolist.add(start.toLowerCase());
		beentolist.add(end.toLowerCase());}
		return beentolist;
		
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
 *  This function prints either the DFS or BFS word ladder
 * @param ladder is the ArrayList<String>  which contains the word ladder between two words
 */
	public static void printLadder(ArrayList<String> ladder) {
		if (ladder.size() <=2 ) { //prints there is no ladder if there is no ladder or if the ladder is just the start word and end word
			System.out.print("no word ladder can be found between " + ladder.get(0) + " and " + ladder.get(1));

		} else { // if a word ladder exists we print each word in the ladder ArrayList
			System.out.println("a " + (ladder.size()) + "-rung word ladder exists between " + ladder.get(0) + " and "
					+ ladder.get(ladder.size()-1));
			for (String s : ladder) {
				System.out.println(s);
			}
		}
	}
	
/**
 * This function changes the start word and checks if its in the dictionary. If it does, it passes that 
 * word as a paramter and is recursively call till it finds the end word.if the new word does not have a path 
 * to the end word it will return null.
 * @param start is the starting String for the word ladder
 * @param end is the ending String for the word ladder
 * @param visited is an ArrayList<String> which keeps track of all the words visited and if it does not have a 
 * path to a new word or the end word, it will remove it from the visited array list
 * @param Dictionary is a Set<String> which is being passed recursively so we do not repeat the words being passed 
 * as a paramter recursively
 * @return is an ArrayList<String> which returns either null if there is no word ladder and the word ladder if there
 * is a word ladder from the start word to the end word.
 */
	public static ArrayList<String> DFS(String start, String end, ArrayList<String> visited, Set<String> Dictionary) {

		Dictionary.remove(start.toUpperCase());// removes the start word from the dictionary so we do not find it again
		if (start.equals(end)) {// checks if the start word is the end word
			return visited;

		}

		for (int x = 0; x < start.length(); x++) {
			StringBuilder startword = new StringBuilder(start);
			for (int y = 0; y < 26; y++) {

				startword.setCharAt(x, (char) ('a' + y));// changes the string such that it looks for a one letter difference of the word
				if (Dictionary.contains(startword.toString().toUpperCase())// checks the dictionary if that one letter difference of the startword is in the dictionary or has been visited
						&& !visited.contains(startword.toString())) {

					visited.add(startword.toString());// add the newly changed string to the visited list
					Dictionary.remove(startword.toString().toUpperCase());//removes the  newly changes string form the dictionary


					ArrayList<String> visitladder = new ArrayList<String>();
					visitladder = DFS(startword.toString(), end, visited, Dictionary);// calls itself on the newly changed function (recursion)
					if (visitladder == null) {// if the newly changed word does not ever reach the end word recursively it will return null
						visited.remove(startword.toString()); // returning null will remove the newly changed word from the visited list

					} else {
						
						return visitladder;// if the word is eventually found it will return the arraylist which will contain the full ladder
					}

				}

			}

		}
		return null;// this will return null if the start word does not ever reach a one letter difference word in the dictionary

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
