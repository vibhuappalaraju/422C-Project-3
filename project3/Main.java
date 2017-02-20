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
 * Spring 2017
 */
//This is a test change

package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin
			ps = System.out;			// default to Stdout
		}
		ArrayList<String> bleh = new ArrayList<String>(parse(kb));
		
		// TODO methods to read in words, output ladder
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		ArrayList<String> checklist = new ArrayList<String>();
		checklist.add(keyboard.next());
		if(checklist.contains("/quit")){
			System.out.print("h");
			System.exit(0);
		}
		else{
			checklist.add(keyboard.next());
			if(checklist.contains("/quit")){
				System.out.print("i");
				System.exit(0);
			}
			
		}
		return checklist;
		
		
		
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// Return empty list if no ladder.
		// TODO some code
		Set<String> dict = makeDictionary();
		// TODO more code
		
		
		
		return null; // replace this line later with real return
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		Queue<Node> Queue = new LinkedList<Node>();
    	ArrayList<String> wordLadder = new ArrayList<String>();
		Set<String> dict = makeDictionary();
		TreeMap<Integer, Node> wordMap = createWordTree(start, end, dict);
		
		Node head = new Node(start);
		Integer level = 0;
		
		Queue.add(wordMap.get(level));
		
		while(!Queue.isEmpty()){
			
			if(head.word.equals(end)){
				break;
			}
			int queueSize = Queue.size();
			
			for(int u = 0; u < queueSize; u++){
				head = Queue.poll();
				if(head.word.equals(end)){
					break;
				}
				for(Node n : head.neighbors){
					if(!n.visited){
						n.visited = true;
						n.parentNode = head;
						Queue.add(n);
					}
				}

			}
		}
		
		while(head != null){
			wordLadder.add(head.word);
			head = head.parentNode;
		}
		Collections.reverse(wordLadder);
		
		if(wordLadder.contains(end)){
			//printLadder(wordLadder);
			return wordLadder;
		}
		wordLadder.clear();
		wordLadder.add(start);
		wordLadder.add(end);
		return wordLadder; // replace this line later with real return
	}

	 public static TreeMap<Integer, Node> createWordTree(String s, String end, Set<String> dict){
    	TreeMap<Integer, Node> wt = new TreeMap<Integer, Node>();
    	Integer level = 0;
    	Queue<Node> q = new LinkedList<Node>(); 
    	
    	Node head = new Node(s);
    	q.add(head);
    	while(!q.isEmpty()){
    		int queueSize = q.size();
    		for(int p = 0; p < queueSize; p++){
    			
    			head = q.poll();
    			wt.put(level, head);
    			if(head.word.equals(end)){
    				return wt;
    			}
    			for(int x = 0; x < head.word.length(); x++){
    				for(char y = 'a'; y <= 'z'; y++){
    					String temp = getWordToCheck(x, y, head.word);
    					if(dict.contains(temp) && !temp.equals(head.word)){
    						Node n = new Node(temp);
    						q.add(n);
    						head.neighbors.add(n);
    					}
    				}
    			}
    		}
    		level++;
    	}
    	return wt;
    	
    }

	public static String getWordToCheck(int wordIndex, char alpha, String word){
    	word = word.replace(word.charAt(wordIndex), alpha);
    	return word;
    }
    
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
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
	
	public static void printLadder(ArrayList<String> ladder) {
		
	}
	// TODO
	// Other private static methods here
}
