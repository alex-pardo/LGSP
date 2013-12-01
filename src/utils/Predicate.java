package utils;

import java.util.ArrayList;
import java.util.List;

public class Predicate {

	//Number of the parameters at each predicate
	public static final int ON_STATION = 1;
	public static final int IN_FRONT_OF = 2;
	public static final int FREE = 1;
	public static final int FREE_LOCOMOTIVE = 0;
	public static final int TOWED = 1;
	public static final int USED_RAILWAYS = 1;
	public static final int LOADED = 1;
	public static final int EMPTY = 1;
	
	private static final String[] TYPES = {"ON-STATION", "IN-FRONT-OF", "FREE-LOCOMOTIVE","USED-RAILWAYS","FREE","TOWED","LOADED","EMPTY"};
	private ArrayList<Object> input = null;
	private String[] inputNames = null;
	int type = -1;
	int max_railways = 3;
	int used_railways_n = -1;
	
	
	
	public Predicate(String name, ArrayList<Object> objects){
		int i = 0;
		while(i < TYPES.length && type == -1){
			String s = TYPES[i];
			if(s.equals(name)) type = i;
			i++;
		}

		if(type == 3) used_railways_n = 0;
		
		this.input = objects;
		this.inputNames = inputNames;
	}
	
	
	public void Instantiate(ArrayList<Object> objects){
		this.input = objects;
	}
	
	public boolean equalsName(String name){
		return TYPES[type].equals(name);
	}
	

	public String getName() {
		return TYPES[type];
	}
	
	public ArrayList<Object> getInput() {
		return input;
	}


	public void setInput(ArrayList<Object> input) {
		String name = TYPES[type];
		int num = Integer.parseInt(name);
		this.input = input;
	}


	@Override
	public String toString() {
		String output = TYPES[type];
		boolean initial = true;
		if(input != null){
			output = output.concat("(");
			for(Object o : input ){
				if(!initial) output = output.concat(",");
				output = output.concat(o.toString());
				initial = false;
			}
			output = output.concat(")");
		}
		return output;
	}
	
}
