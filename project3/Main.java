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
    ArrayList<String> bleh = new ArrayList<String>();
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
		bleh = parse(kb);
        initialize();
        printLadder(getWordLadderDFS(wordladder.get(0),wordladder.get(1)));
        System.out.println("HELLLO");
		
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
        if (end.equals(start)){
            System.out.print("no word ladder can be found between "+start+" and "+end);
            System.exit(0);
        }
        
        ArrayList<String> printlist = new ArrayList<String>();
        printlist.add(start);
        printlist=DFS(start,end,printlist,Dictionary);
        
        return printlist;
		// TODO more code
		
		
		
		return null; // replace this line later with real return
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
    	
    	Queue<Node> Queue = new LinkedList<Node>();
    	ArrayList<String> wordLadder = new ArrayList<String>();
		Set<String> dict = makeDictionary();
		Set<String> visitedWords = new HashSet<String>();
		
		Node head = new Node(start);
		
		Queue.add(head);
		visitedWords.add(head.word);
		
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
				
				head.neighbors = allNeighbors(head.word, dict);
				
				for(Node n : head.neighbors){
					if(!visitedWords.contains(n.word)){
						//n.visited = true;
						visitedWords.add(n.word);
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
			
			return wordLadder;
		}
		wordLadder.clear();
		wordLadder.add(start);
		wordLadder.add(end);
		return wordLadder; // replace this line later with real return
	}

	public static ArrayList<Node> allNeighbors(String s, Set<String> dict){
    	ArrayList<Node> n = new ArrayList<Node>();
    	
    	for(int x = 0; x < s.length(); x++){
    		for(char y = 'a'; y <= 'z'; y++){
    			String temp = getWordToCheck(x, y, s);
				if(dict.contains(temp.toUpperCase()) && !temp.equals(s)){
					Node neigh = new Node(temp);
					n.add(neigh);
				}
    		}
    	}
    	
    	return n;
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
