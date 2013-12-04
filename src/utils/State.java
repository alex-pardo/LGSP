package utils;

import java.util.ArrayList;

public class State {

	private ArrayList<Predicate> predicates;
	private String name;
	private int used_railways; 
	
	public State(String name, int used_railways){
		this.name = name;
		this.used_railways = used_railways;
		predicates = new ArrayList<Predicate>();
	}
	public State(ArrayList<Predicate> p) {
		predicates = p;
	}
	public void addPredicate(Predicate p){
		predicates.add(p);
	}
	
	public ArrayList<Predicate> getPredicate(){
		return predicates;
	}
	
	public boolean hasThisPredicate(Predicate p){
		for(Predicate pred : predicates){
			if(pred.equals(p)) return true;
		}
		return false;
	}
	
	public boolean hasNRailways(int n){
		return n == used_railways;
	}
	
	public boolean railwaysLower(int n){
		return n < used_railways;
	}
	
	public void increaseN(){
		used_railways++;
	}
	
	public void decreaseN(){
		used_railways--;
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
	public void delPredicate(Predicate d) {
		int pos = -1;
		for(int i = 0; i < predicates.size(); i++){
			if(predicates.get(i).equals(d)) pos = i;
		}
		if(pos > -1){
			predicates.remove(pos);
		}
	}
	public void hasInstantiatedPredicate(Predicate a, Wagon wagon) {
		// TODO Auto-generated method stub
		
	}
	
	public Predicate getPredicate(Predicate a){
		for(Predicate tmp : predicates){
			if(tmp.equalsName(a.getName())) return tmp; 
		}
		return null;
	}
	
	
}
