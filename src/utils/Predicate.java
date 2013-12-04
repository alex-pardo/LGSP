package utils;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	
	private static final String[] TYPES = {"ON-STATION", "IN-FRONT-OF", "FREE-LOCOMOTIVE","USED-RAILWAYS","FREE","TOWED","LOADED","EMPTY", "N<MAX"};
	private ArrayList<Object> input = null;
	private List<String> inputNames = null;
	public static final String PLUS = "n+1";
	public static final String MINUS = "n-1";
	public static final String EQUALS = "n";
	public static final int MAX_RAILWAYS = 3;
	int type = -1;
	int used_railways_n = -1;
	int[] accepted_inputs = {1, 2, 0, 1, 1, 1, 1, 1};
	
	
	public Predicate(String name, ArrayList<Object> objects, List<String> inputNames){
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
	
	public void addInstance(ArrayList<Object> objects){
		int i = 0;
		while(i < objects.size() && input.size() < accepted_inputs[type]){
			input.add(objects.get(i));
		}
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


	public ArrayList<Object> getInstances() {
		return input;
	}

	
	public List<String> getInputNames() {
		return inputNames;
	}


	public void setInputNames(List<String> inputNames) {
		this.inputNames = inputNames;
	}


	@Override
	public boolean equals(Object obj) {
		Predicate p = (Predicate) obj;
		
		try {
			if(this.getName().equals(p.getName())){
				if(p.getInstances() == null && input == null){
					return true;
				}
				for(Object o1 : p.getInstances()){
					boolean trobat = false;			
					for(Object o2 : input){
						if(o1 instanceof Wagon && o2 instanceof Wagon){
							if(((Wagon) o1).nameEquals(((Wagon) o2).getName())){ trobat = true;break;}
						} else if(o1 instanceof Integer && o2 instanceof Integer){
							if((Integer) o1 == (Integer) o2){ trobat = true; break;} 
						}else{
							return false;
						}
					}
					if(!trobat) return false;
				}
				
				for(Object o1 : input){
					boolean trobat = false;			
					for(Object o2 : p.getInstances()){
						if(o1 instanceof Wagon && o2 instanceof Wagon){
							if(((Wagon) o1).nameEquals(((Wagon) o2).getName())){ trobat = true;break;}
						} else if(o1 instanceof Integer && o2 instanceof Integer){
							if((Integer) o1 == (Integer) o2){ trobat = true; break;} 
						}else{
							return false;
						}
					}
					if(!trobat) return false;
				}
				
				
			}else{
				return false;
			}
			return true;
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	@Override
	public Object clone(){
		return new Predicate(this.getName(), this.input, this.inputNames);
	}
	
}
