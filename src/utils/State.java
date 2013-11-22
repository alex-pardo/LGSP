package utils;

import java.util.ArrayList;

public class State {

	ArrayList<Predicate> predicates;
	String name;
	public State(String name){
		super();
		this.name = name;
		predicates = new ArrayList<Predicate>();
	}
	public void addPredicate(Predicate p){
		predicates.add(p);
	}
	@Override
	public String toString() {
		String output = "------------------------------\n" ;
		output = output.concat("  State: " + name + "\n");
		output = output.concat("------------------------------\n");
		output = output.concat("Predicates:\n");
		for(Predicate p : predicates){
			output = output.concat(p.toString());
			output = output.concat("\n");
		}
		
		
		
		return output;
	}
	
	
}
