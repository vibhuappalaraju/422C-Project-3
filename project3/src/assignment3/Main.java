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

package project3;

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
 * 
 * @param start
 * @param end
 * @return
 */
	public static ArrayList<String> getWordLadderBFS(String start, String end) {

		Set<String> Dictionary = makeDictionary();
		Queue<Node> words = new LinkedList<Node>();
		Node first = new Node();
		first.storeword = start;
		words.add(first);
		int wordlength = start.length();
		boolean queuechecker = words.isEmpty();
		int x = 0;
		int y = 0;

		while (!queuechecker) {
			Node addtoqueue = new Node();
			addtoqueue = words.remove();
			String freshword = addtoqueue.storeword;

			addtoqueue.storeladder.add(freshword);

			if (freshword.equals(end)) {

				

				// return the ladder here instead

				return addtoqueue.storeladder;

			}
			

			StringBuilder wordusing = new StringBuilder(freshword);

			Dictionary.remove(wordusing.toString().toUpperCase());
			while (x < wordlength) {
				while (y < 26) {
					wordusing.setCharAt(x, (char) ('a' + y));
					if (Dictionary.contains(wordusing.toString().toUpperCase())) {
						Node addword = new Node();
						addword.storeword = wordusing.toString();
						addword.storeladder = new ArrayList<String>(addtoqueue.storeladder);
						words.add(addword);
						Dictionary.remove(wordusing.toString());

					}

					wordusing = new StringBuilder(freshword);
					y++;

				}

				x++;
				y = 0;

			}

			x = 0;
			queuechecker = words.isEmpty();

		}
		ArrayList<String> emptylist = new ArrayList<String>();
		emptylist.add(start.toLowerCase());
		emptylist.add(end.toLowerCase());
		return emptylist; // return empty ladder

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

}
