package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

import lgsp.Problem;

public final class FileReader {

	private static final String WAGONS = "Wagons=";
	private static final String INITIAL_STATE = "Initial_state=";
	private static final String GOAL_STATE = "Goal_state=";
	
	private static String[] files = {"/problems/problem1.txt","/problems/problem2.txt","/problems/problem3.txt","/problems/problem4.txt","/problems/problem5.txt"};
	
	/**
	 * Reads a file and parses it
	 * @return
	 */
	public static Problem readFile(){
		System.out.println("Planning and Approximate Reasoning -- URV -- Fall 2013");
		System.out.println("David Sanchez & Alex Pardo");
		System.out.println("-- Linear Goal Stack Planner --");
		for(int k = 1; k <= files.length; k++) System.out.println(k + ". Problem " + k);
		System.out.println("Select one problem: ");
		Scanner in = new Scanner(System.in);
        int i = in.nextInt();
		i --;
		String filename = System.getProperty("user.dir")+files[i];
		System.out.println(filename);
		File file = new File(filename);
		new Logger("Problem"+(i+1));
		return parseFile(file);
		
	}
	
	/*
	 * Parse a problem file
	 */
	private static Problem parseFile(File f){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new java.io.FileReader(f));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		String line;
		String text = null;
		try {
			text = line = br.readLine();
			while ((line = br.readLine()) != null) {
				text.concat(line);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		text = text.replace(" ", "");
		text = text.replace("\n", "");
		int i1 = text.indexOf(WAGONS);
		int i2 = text.indexOf(INITIAL_STATE);
		int i3 = text.indexOf(GOAL_STATE);
		
		System.out.println(text.substring(i1, i2));
		Logger.writeString(text.substring(i1, i2));
		System.out.println(text.substring(i2, i3));
		Logger.writeString(text.substring(i2, i3));
		System.out.println(text.substring(i3));
		Logger.writeString(text.substring(i3));
		
		
		ArrayList<Wagon> wagons = parseWagons(text.substring(i1, i2));
		new SystemWagons(wagons);
		State initial = parseState(text.substring(i2, i3), wagons);
		State goal = parseState(text.substring(i3), wagons);
		
		Problem problem = new Problem(wagons, initial, goal);
		return problem;
	}
	
	/*
	 * Parses the wagons
	 */
	private static ArrayList<Wagon> parseWagons(String s){
		ArrayList<Wagon> wagons = new ArrayList<Wagon>();
		
		int equal = s.indexOf("=");
		String data = s.substring(equal+1);
		for(String w : data.split(",")){
			wagons.add(new Wagon(w));
		}
		
		return wagons;
	}
	
	/*
	 * Parses the states
	 */
	private static State parseState(String input, ArrayList<Wagon> w){
		
		int equal = input.indexOf("=");
		State s = new State(input.substring(0,equal),3,3);
		String data = input.substring(equal+1);
		for(String p : data.split(";")){
			s.addPredicate(PredicateCreator.createPredicate(p));
		}
			
		return s;
	}
	
	
}
