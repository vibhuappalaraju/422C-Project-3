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
    static ArrayList<String> wordladderBFS = new ArrayList<String>();
	static ArrayList<String> wordladderDFS = new ArrayList<String>();
    
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
		ArrayList<String> userInput = new ArrayList<String>();
		userInput = parse(kb);
        
        while(!userInput.contains("/quit")){
        	initialize();
        	while(userInput.size() < 2){
        		userInput = parse(kb);
        	}
        	wordladderBFS = getWordLadderBFS(userInput.get(0),userInput.get(1));
        	wordladderDFS = getWordLadderDFS(userInput.get(0),userInput.get(1));
        	printLadder(wordladderBFS);
        	System.out.println();
        	printLadder(wordladderDFS);
        	System.out.println();
        	userInput.clear();
        }
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
        if (end.equals(start)){
            System.out.print("no word ladder can be found between "+start+" and "+end);
            System.exit(0);
        }
        
        ArrayList<String> printlist = new ArrayList<String>();
        printlist.add(start);
        printlist=DFS(start,end,printlist,dict);
        
        return printlist;
		// TODO more code
		
		
		
		 // replace this line later with real return
	}
	
    /**
	 * Uses breadth first search to find word ladder between start and end
	 * @param start , the first word to begin the word search
	 * @param end , the last word to end the word ladder
	 * @return an ArrayList<String> containing the word ladder
	 */
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
    	int secondTime = 0;				//indicates which loop word ladder found in
    	Queue<Node> Queue = new LinkedList<Node>();
    	ArrayList<String> wordLadder = new ArrayList<String>();
		Set<String> dict = makeDictionary();
		Set<String> visitedWords = new HashSet<String>();
		
		Node head = new Node(start);
		
		Queue.add(head);				//Add starting word to the Queue
		visitedWords.add(head.word);	//Add to visited set so no word repeats
		head.parentNode = null;			
		
		while(!Queue.isEmpty()){	
			head = Queue.poll();		//Remove top element from the queue

			if(head.word.equals(end)){	//End search if we found the end word
				break;
			}
				
			head.neighbors = allNeighbors(head.word, dict, secondTime); //find all words in dictionary with one letter difference
				
			for(Node n : head.neighbors){ 				//Iterate through all "neighbors"
				if(!visitedWords.contains(n.word)){
					visitedWords.add(n.word);
					n.parentNode = head;			//Make the parent node head -> for backtracing
					Queue.add(n);					
				}
			}

		}
		
		if(!head.word.equals(end)){	//Repeat the same process if first time failed, but with end as start
			head = new Node(end);
			end = start;
			visitedWords.clear();
			secondTime++;			//Let's know that in the second loop at the end
			Queue.add(head);
			
			while(!Queue.isEmpty()){ 
				head = Queue.poll();

				if(head.word.equals(end)){
					break;
				}
				
				head.neighbors = allNeighbors(head.word, dict, secondTime);
				
				for(Node n : head.neighbors){
					if(!visitedWords.contains(n.word)){
						visitedWords.add(n.word);
						n.parentNode = head;
						Queue.add(n);
					}
				}

			}
		}
		
		if(head.word.equals(end)){
			//Uses parent pointers to add path to word ladder array list
			while(head != null){
				wordLadder.add(head.word);
				head = head.parentNode;
			}
			if(secondTime == 0) //Only reverse if word ladder found in first loop
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
	public static ArrayList<Node> allNeighbors(String s, Set<String> dict, int secondTime){
    	ArrayList<Node> n = new ArrayList<Node>();
    	
    	for(int x = 0; x < s.length(); x++){
    		for(char y = 'a'; y <= 'z'; y++){
    			String temp = getWordToCheck(x, y, s);
    			//Only adds words within the dictionary
				if(dict.contains(temp.toUpperCase()) && !temp.equals(s)){
					if(secondTime == 1){
						Node neigh = new Node(temp);
						n.add(neigh);
					}
					else {
						if(oneLetterDifference(s, temp)){
							Node neigh = new Node(temp);
							n.add(neigh);
						}
					}
				}
    		}
    	}
    	
    	return n;
    }


	 

	/**
	 * This function takes a word index, a char, and a String and
	 * replaces the String[word index] with the char
	 * @param wordIndex is the index of the word to replace with char
	 * @param alpha character
	 * @param word	String
	 * @return	A new string with the above changes
	 */
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
    
    public static ArrayList<String> DFS(String start, String end, ArrayList<String> visited, Set<String> Dictionary) {
        
        Dictionary.remove(start.toUpperCase());
        
        if (start.equals(end)) {
            
            
            return visited;
            
        }
        
        for (int x = 0; x < start.length(); x++) {
            StringBuilder startword = new StringBuilder(start);
            for (int y = 0; y < 26; y++) {
                
                startword.setCharAt(x, (char) ('a' + y));
                if (Dictionary.contains(startword.toString().toUpperCase()) && !visited.contains(startword.toString())) {
                    
                    visited.add(startword.toString());
                    Dictionary.remove(startword.toString().toUpperCase());
                    //					ArrayList<String> whatever = new ArrayList<String>();
                    //					whatever = DFS(startword.toString(), end, visited, Dictionary);
                    //					if(whatever != null){
                    //						return whatever;
                    //					}
                    //					else{
                    //						visited.remove(startword.toString());
                    //					}
                    ArrayList<String> whatever = new ArrayList<String>();
                    whatever = DFS(startword.toString(), end, visited, Dictionary);
                    if (whatever == null) {
                        visited.remove(startword.toString());
                        
                    } else {
                        //visited = DFS(startword.toString(), end, visited, Dictionary);
                        return whatever;
                    }
                    
                }
                
            }
            
        }
        return null;
        
    }
	
	public static void printLadder(ArrayList<String> ladder) {
        if(ladder == null){
            System.out.print("no word ladder can be found between "+wordladder.get(0)+" and "+wordladder.get(1));
            
        }
        System.out.print(ladder.toString());
		
	}
	// TODO
	// Other private static methods here
}
