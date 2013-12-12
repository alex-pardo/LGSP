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
	
	/**
	 * Replaces the instantiation
	 * @param objects
	 */
	public void Instantiate(ArrayList<Object> objects){
		if(type < 8 && type != 3 && type != 2){
			input = new ArrayList<Object>();
			int i = 0;
			while(i < objects.size() && input.size() < accepted_inputs[type]){
				input.add(objects.get(i));
				i++;
			}
		}
	}
	
	public void Instantiate(Object object){
		if(type < 8 && type != 3){
			input = new ArrayList<Object>();
			if(input.size() < accepted_inputs[type]){
				input.add(object);
			}
		}
	}
	
	
	/**
	 * append instances
	 * @param objects
	 */
	public void addInstance(ArrayList<Object> objects){
		int i = input.size()-1;
		while(i < objects.size() && input.size() < accepted_inputs[type]){
			input.add(objects.get(i));
			i++;
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

	public int hasInstances(ArrayList<Object> instances, Object o){
		int result = 0;
		int null_pos = -1;
		for(int i = 0; i < instances.size(); i++){
			if(instances.get(i) == null) null_pos = i;
			if(instances.get(i) != null && input.contains(instances.get(i))) result ++;
		}
		if(null_pos >= 0){
			if(input.contains(o)){
				result++;
			}
		}
		//if(result <= 1) return 0;
		return result;
	}
	
	public List<String> getInputNames() {
		return inputNames;
	}

	public List<String> getInputNames(Operator o) {
		ArrayList<String> tmp = new ArrayList<String>();
		switch(o.getOp_type()){
		case 0: 
			//COUPLE 
			//Preconditions: USED-RAILWAYS(n), ON-STATION(x), FREE-LOCOMOTIVE, FREE(x)
			//Eliminate: ON-STATION(x), FREE-LOCOMOTIVE, USED-RAILWAYS(n)
			//Add: TOWED(x), USED-RAILWAYS(n-1)
			if(type == 5){
				tmp.add("x");
				return tmp;
			} 
			
			
			break;
		
		case 1:
			//PARK
			// Preconditions: TOWED(x), USED-RAILWAYS(n), n<max-railways
			// Eliminate: TOWED(x), USED-RAILWAYS(n)
			// Add: ON-STATION(x), USED-RAILWAYS(n+1), FREE-LOCOMOTIVE
			
			if(type == 0){
				tmp.add("x");
				return tmp;
			}
			break;
			
		case 2:
			// DETACH
			// Preconditions: IN-FRONT-OF(x,y), FREE(x), FREE-LOCOMOTIVE
			// Eliminate: IN-FRONT-OF(x,y), FREE-LOCOMOTIVE
			// Add: TOWED(x), FREE(y)
			if(type == 5){
				tmp.add("x");
				return tmp;
			}
			if(type == 4){
				tmp.add("y");
				return tmp;
			}
			break;
			
		case 3:
			// ATTACH
			// Preconditions: TOWED(x), FREE(y)
			// Eliminate: TOWED(x), FREE(y)
			// Add: IN-FRONT-OF(x,y), FREE-LOCOMOTIVE
			
			if(type == 1){
				tmp.add("x");
				tmp.add("y");
				return tmp;
			}
			
			break;
			
		case 4:
			// LOAD
			// Preconditions: ON-STATION(x), EMPTY(x)
			// Eliminate: EMPTY(x)
			// Add: LOADED(x)
			
			if(type == 6){
				tmp.add("x");
				return tmp;
			}
			
			break;
			
		case 5:
			// UNLOAD
			// Preconditions: ON-STATION(x), LOADED(x)
			// Eliminate: LOADED(x)
			// Add: EMPTY(x)
			
			if(type == 7){
				tmp.add("x");
				return tmp;
			}
			
			break;
		
		default:
			break;
	}
		return null;
	}
	

	public void setInputNames(List<String> inputNames) {
		this.inputNames = inputNames;
	}


	@Override
	public boolean equals(Object obj) {
		Predicate p = (Predicate) obj;
		if(p.getName().equals(TYPES[this.type])){ //check free locomotive and used_rails
			if(p.getInstances() == null && input == null) return true;
			if(p.getInstances() == null || input == null) return false;
			for(int i = 0; i < this.input.size(); i++){
				if(!((Wagon)input.get(i)).getName().equals(((Wagon)p.getInstances().get(i)).getName())) return false;	
			}
			return true;
		}
		return false;
		
	}
	
	@Override
	public Object clone(){
		return new Predicate(this.getName(), this.input, this.inputNames);
	}

	
	
}
