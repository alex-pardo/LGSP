package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import lgsp.Problem;

public final class FileReader {

	private static final String WAGONS = "Wagons=";
	private static final String INITIAL_STATE = "Initial_state=";
	private static final String GOAL_STATE = "Goal_state=";
	
	private static String filename = "/Users/alexpardofernandez/Dropbox/Uni/master/PAR/practical_ex/problem1.txt";
	
	public static Problem readFile(){
		
		/*JFileChooser fileChooser = new JFileChooser();
		File file = null;
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		  file = fileChooser.getSelectedFile();
		  return parseFile(file);
		}*/
		
		File file = new File(filename);
		return parseFile(file);
		//return null;
		
	}
	
	private static Problem parseFile(File f){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new java.io.FileReader(f));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		text = text.replace(" ", "");
		int i1 = text.indexOf(WAGONS);
		int i2 = text.indexOf(INITIAL_STATE);
		int i3 = text.indexOf(GOAL_STATE);
		
		System.out.println(text.substring(i1, i2));
		System.out.println(text.substring(i2, i3));
		System.out.println(text.substring(i3));
		
		
		ArrayList<Wagon> wagons = parseWagons(text.substring(i1, i2));
		new SystemWagons(wagons);
		State initial = parseState(text.substring(i2, i3), wagons);
		State goal = parseState(text.substring(i3), wagons);
		
		Problem problem = new Problem(wagons, initial, goal);
		return problem;
	}
	
	private static ArrayList<Wagon> parseWagons(String s){
		ArrayList<Wagon> wagons = new ArrayList<Wagon>();
		
		int equal = s.indexOf("=");
		String data = s.substring(equal+1);
		for(String w : data.split(",")){
			wagons.add(new Wagon(w));
		}
		
		return wagons;
	}
	
	private static State parseState(String input, ArrayList<Wagon> w){
		
		int equal = input.indexOf("=");
		State s = new State(input.substring(0,equal));
		String data = input.substring(equal+1);
		for(String p : data.split(";")){
			s.addPredicate(PredicateCreator.createPredicate(p));
		}
			
		return s;
	}
	
	
}
