package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Operator {

	
	public static final String[] OPERATOR_LIST = {"COUPLE", "PARK", "DETACH", "ATTACH", "LOAD", "UNLOAD"};
	private static final int[] needed_objects = {1,1,2, 2,1,1};
	private String name;
	private ArrayList<Predicate> preconditions;
	private ArrayList<Predicate> add;
	private ArrayList<Predicate> delete;
	private int op_type = -1;
	private ArrayList<Object> input;
	private List<String> inputNames = null;
	public static List<String> xy;
	public static List<String> y;
	public static List<String> x;
	public static List<String> n;
	public static List<String> n1;
	public static List<String> nminus1;
	public static List<String> nonVariables;
	
	
	public Operator(String name){
		int i = 0;
		while(i < OPERATOR_LIST.length && op_type == -1){
			String s = OPERATOR_LIST[i];
			if(s.equals(name)){op_type = i;}
			i++;
		}
		this.name = name;
		constructLists();
		
	}
	
	
	private void constructLists() {
		preconditions = new ArrayList<Predicate>();
		add = new ArrayList<Predicate>();
		delete = new ArrayList<Predicate>();
		
		//Declarate the possible variables
        String[] string = new String[]{"x" , "y"};
		xy = Arrays.asList(string);
        string = new String[]{"y"};
		y = Arrays.asList(string);
        string = new String[]{"x"};
		x = Arrays.asList(string);
        string = new String[]{"n"};
		n = Arrays.asList(string);
        string = new String[]{"n+1"};
		n1 = Arrays.asList(string);
        string = new String[]{"n-1"};		
        nminus1 = Arrays.asList(string);
        string = new String[]{};	
        nonVariables = Arrays.asList(string);
		
		switch(op_type){
			case 0: 
				//COUPLE 
				//Preconditions: USED-RAILWAYS(n), ON-STATION(x), FREE-LOCOMOTIVE, FREE(x)
				//Eliminate: ON-STATION(x), FREE-LOCOMOTIVE, USED-RAILWAYS(n)
				//Add: TOWED(x), USED-RAILWAYS(n-1)
				inputNames = x;
				
				preconditions.add(new Predicate("USED-RAILWAYS", null, n));
				preconditions.add(new Predicate("ON-STATION", null, x));
				preconditions.add(new Predicate("FREE-LOCOMOTIVE", null, nonVariables));
				preconditions.add(new Predicate("FREE", null, x));
				
				delete.add(new Predicate("ON-STATION", null, x));
				delete.add(new Predicate("FREE-LOCOMOTIVE", null, nonVariables));
				delete.add(new Predicate("USED-RAILWAYS", null, n));
				
				add.add(new Predicate("TOWED", null, x));
				add.add(new Predicate("USED-RAILWAYS", null, nminus1));	
				break;
			
			case 1:
				//PARK
				// Preconditions: TOWED(x), USED-RAILWAYS(n), n<max-railways
				// Eliminate: TOWED(x), USED-RAILWAYS(n)
				// Add: ON-STATION(x), USED-RAILWAYS(n+1), FREE-LOCOMOTIVE
				inputNames = x;
				
				preconditions.add(new Predicate("TOWED", null, x));
				preconditions.add(new Predicate("USED-RAILWAYS", null, n));
				preconditions.add(new Predicate("N<MAX", null, nonVariables));
				
				delete.add(new Predicate("TOWED", null, x));
				delete.add(new Predicate("USED-RAILWAYS", null, n));
				
				add.add(new Predicate("ON-STATION", null, x));
				add.add(new Predicate("USED-RAILWAYS", null, n1));
				add.add(new Predicate("FREE-LOCOMOTIVE", null, nonVariables));
				break;
				
			case 2:
				// DETACH
				// Preconditions: IN-FRONT-OF(x,y), FREE(x), FREE-LOCOMOTIVE
				// Eliminate: IN-FRONT-OF(x,y), FREE-LOCOMOTIVE
				// Add: TOWED(x), FREE(y)
				inputNames = xy;
				
				preconditions.add(new Predicate("IN-FRONT-OF", null, xy));
				preconditions.add(new Predicate("FREE", null, x));
				preconditions.add(new Predicate("FREE-LOCOMOTIVE", null, nonVariables));
				
				delete.add(new Predicate("IN-FRONT-OF", null, xy));
				delete.add(new Predicate("FREE-LOCOMOTIVE", null, nonVariables));
				
				add.add(new Predicate("TOWED", null, x));
				add.add(new Predicate("FREE", null, y));
				break;
				
			case 3:
				// ATTACH
				// Preconditions: TOWED(x), FREE(y)
				// Eliminate: TOWED(x), FREE(y)
				// Add: IN-FRONT-OF(x,y), FREE-LOCOMOTIVE
				inputNames = xy;
				
				preconditions.add(new Predicate("TOWED", null, x));
				preconditions.add(new Predicate("FREE", null, y));
				
				delete.add(new Predicate("TOWED", null, x));
				delete.add(new Predicate("FREE", null, y));
				
				add.add(new Predicate("IN-FRONT-OF", null, xy));
				add.add(new Predicate("FREE-LOCOMOTIVE", null, nonVariables));
				break;
				
			case 4:
				// LOAD
				// Preconditions: ON-STATION(x), EMPTY(x)
				// Eliminate: EMPTY(x)
				// Add: LOADED(x)
				inputNames = x;
				
				preconditions.add(new Predicate("ON-STATION", null, x));
				preconditions.add(new Predicate("EMPTY", null, x));
				
				delete.add(new Predicate("EMPTY", null, x));
				
				add.add(new Predicate("LOADED", null, x));
				break;
				
			case 5:
				// UNLOAD
				// Preconditions: ON-STATION(x), LOADED(x)
				// Eliminate: LOADED(x)
				// Add: EMPTY(x)
				inputNames = x;
				
				preconditions.add(new Predicate("ON-STATION", null, x));
				preconditions.add(new Predicate("LOADED", null, x));
				
				delete.add(new Predicate("LOADED", null, x));
				
				add.add(new Predicate("EMPTY", null, x));
				break;
			
			default:
				break;
		}
		
	}

	public boolean hasAdd(Predicate p){
		for(Predicate a : add){
			if(a.equalsName(p.getName())) return true;
		}
		return false;
	}

	public State apply(State current_state) {
		
		for(Predicate a : add){
			if(a.getName().equals(Predicate.USED_RAILWAYS)){
				for(String s : a.getInputNames()){
					if(s.equals(Predicate.PLUS)){
						current_state.increaseN();
					}else if(s.equals(Predicate.MINUS)){
						current_state.decreaseN();
					}
				}
			}
			current_state.addPredicate(a);
		}
		
		for(Predicate d : delete){
			current_state.delPredicate(d);
		}
		
		
		return current_state;
	}
	
	public int preconditionsAccomplished(State s){
		int return_val = 0;
		for(Predicate p : s.getPredicate()){
			for(Predicate prec : preconditions){
				if(prec.equalsName("N<MAX")){
					if(s.railwaysLower(Predicate.MAX_RAILWAYS)){
						return_val ++;
					}
				}else if(prec.equalsName(p.getName())){
					return_val ++;
				} 

			}
		}
		return return_val;
	}

	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public ArrayList<Predicate> getPreconditions() {
		return preconditions;
	}


	public void setPreconditions(ArrayList<Predicate> preconditions) {
		this.preconditions = preconditions;
	}


	public ArrayList<Predicate> getAdd() {
		return add;
	}


	public void setAdd(ArrayList<Predicate> add) {
		this.add = add;
	}


	public ArrayList<Predicate> getDelete() {
		return delete;
	}


	public void setDelete(ArrayList<Predicate> delete) {
		this.delete = delete;
	}


	public int getOp_type() {
		return op_type;
	}


	public void setOp_type(int op_type) {
		this.op_type = op_type;
	}

	public ArrayList<Object> getInput() {
		return input;
	}


	public void setInput(ArrayList<Object> input) {
		this.input = input;
		add = replaceInput(add, input);
		delete = replaceInput(delete, input);
		preconditions = replaceInput(preconditions, input);
	}

	private ArrayList<Predicate> replaceInput(ArrayList<Predicate> list, ArrayList<Object> input){
		ArrayList<Predicate> tmp = new ArrayList<Predicate>();
		for(Predicate p: list) {
			p.setInput(input);
			tmp.add(p);
		}
		return tmp;
	}
	
	@Override
	public String toString(){
		String out = name;
		if(input != null){
			out = out.concat("(");
			boolean first = true;
			for(Object o : input){
				if(!first) out = out.concat(",");
				out = out.concat(o.toString());
				first = false;
			}
			out = out.concat(")");
		}
		return out;
	}
	
	
	public void instantiate(ArrayList<Object> instances, List<String> names, State sf){
		ArrayList<Object> instantiation_array =  new ArrayList<Object>();
		ArrayList<Wagon> not_assigned = new ArrayList<Wagon>();
		for(Wagon tmp : SystemWagons.system_wagons){not_assigned.add(tmp);}
		
		if(instances != null && instances.size() == needed_objects[op_type]){
			for(int i = 0; i < instances.size(); i++){
				if(((String)names.get(i)).equals("x")){
					instantiation_array.add(0, instances.get(i));
					not_assigned.remove(instances.get(i));
				} else if (!((String)names.get(i)).contains("n")){
					instantiation_array.add(instances.get(i));
					not_assigned.remove(instances.get(i));
				}
			}
		}else{
			if(instances != null){
				for(int i = 0; i < instances.size(); i++){
					if(((String)names.get(i)).equals("x")){
						instantiation_array.add(0, instances.get(i));
						not_assigned.remove(instances.get(i));
					} else if (!((String)names.get(i)).contains("n")){
						instantiation_array.add(instances.get(i));
						not_assigned.remove(instances.get(i));
					}
				}
			}
			
			int max_wagon = 0;
			Wagon best = null;
			
			switch(op_type){
			case 0: 
				//COUPLE 
				//Preconditions: USED-RAILWAYS(n), ON-STATION(x), FREE-LOCOMOTIVE, FREE(x)
				//Eliminate: ON-STATION(x), FREE-LOCOMOTIVE, USED-RAILWAYS(n)
				//Add: TOWED(x), USED-RAILWAYS(n-1)
				
				for(Wagon w : not_assigned){
					int tmp_counter = 0;
					for(Predicate a : add){
						Predicate tmp = sf.getPredicate(a);
						if(tmp == null) continue;
						for(Object o : tmp.getInstances()){
							if(((Wagon) o).nameEquals(w.getName())) tmp_counter++;
						}
					}
					if(tmp_counter > max_wagon){
						max_wagon = tmp_counter;
						best = (Wagon) w.clone();
					}
					
				}
				
				instantiation_array.add(best);
				
				break;
			
			case 1:
				//PARK
				// Preconditions: TOWED(x), USED-RAILWAYS(n), n<max-railways
				// Eliminate: TOWED(x), USED-RAILWAYS(n)
				// Add: ON-STATION(x), USED-RAILWAYS(n+1), FREE-LOCOMOTIVE
				
				break;
				
			case 2:
				// DETACH
				// Preconditions: IN-FRONT-OF(x,y), FREE(x), FREE-LOCOMOTIVE
				// Eliminate: IN-FRONT-OF(x,y), FREE-LOCOMOTIVE
				// Add: TOWED(x), FREE(y)
				while(instantiation_array.size() < needed_objects[op_type]){
					for(Wagon w : not_assigned){
						int tmp_counter = 0;
						for(Predicate a : add){
							Predicate tmp = sf.getPredicate(a);
							if(tmp == null) continue;
							for(Object o : tmp.getInstances()){
								if(((Wagon) o).nameEquals(w.getName())) tmp_counter++;
							}
						}
						if(tmp_counter > max_wagon){
							max_wagon = tmp_counter;
							best = (Wagon) w.clone();
						}
						
					}
					
					instantiation_array.add(best);
					not_assigned.remove(best);
				}
				
				break;
				
			case 3:
				// ATTACH
				// Preconditions: TOWED(x), FREE(y)
				// Eliminate: TOWED(x), FREE(y)
				// Add: IN-FRONT-OF(x,y), FREE-LOCOMOTIVE
				
				break;
				
			case 4:
				// LOAD
				// Preconditions: ON-STATION(x), EMPTY(x)
				// Eliminate: EMPTY(x)
				// Add: LOADED(x)
				for(Wagon w : not_assigned){
					int tmp_counter = 0;
					for(Predicate a : add){
						Predicate tmp = sf.getPredicate(a);
						if(tmp == null) continue;
						for(Object o : tmp.getInstances()){
							if(((Wagon) o).nameEquals(w.getName())) tmp_counter++;
						}
					}
					if(tmp_counter > max_wagon){
						max_wagon = tmp_counter;
						best = (Wagon) w.clone();
					}
					
				}
				
				instantiation_array.add(best);
				
				break;
				
			case 5:
				// UNLOAD
				// Preconditions: ON-STATION(x), LOADED(x)
				// Eliminate: LOADED(x)
				// Add: EMPTY(x)
				
				
				
				
				break;
			
			default:
				break;
			}
		}
		
		
		for(Predicate prec : preconditions){
			prec.Instantiate(instantiation_array);
		}
		
		for(Predicate a : add){
			a.Instantiate(instantiation_array);
		}
		
		for(Predicate d : delete){
			d.Instantiate(instantiation_array);
		}
		
		
	}

/*
	public void instantiate(ArrayList<Object> instances, List<String> names, State sf) {
		//input = instances;
		
		//assign the instances we already know
		
		ArrayList<Tuple<String, Wagon>> assignements = new ArrayList<Operator.Tuple<String,Wagon>>();
		input = new ArrayList<Object>();
		int i = 0;
		int max_accomplished = 0;
		int best_object = -1;
		String name;
		ArrayList<Wagon> not_assigned = new ArrayList<Wagon>();
		for(Wagon tmp : SystemWagons.system_wagons){not_assigned.add(tmp);}
		for(i=0; i<names.size();i++){
			name = names.get(i);
			if(inputNames.contains(name) && !name.equals("n") && !name.equals("n+1") && !name.equals("n-1")){
				assignements.add(new Tuple<String, Wagon>(name, (Wagon)instances.get(i)));
				not_assigned.remove((Wagon)instances.get(i));
			} 
		}
		
		
		// check the best instance for each variable
		while(assignements.size() < needed_objects[op_type]){
			for(Predicate a : add){
				ArrayList<Predicate> final_pred = sf.getPredicate();
				Predicate tmp = (Predicate) a.clone();
				for(Wagon w : not_assigned){
					
					
				}
				
			}
			
			
		}
		
		
		for(Predicate prec : preconditions){
			prec.Instantiate(instances, inputNames, s);
		}
		
		for(Predicate a : add){
			a.Instantiate(instances, inputNames, s);
		}
		
		for(Predicate d : delete){
			d.Instantiate(instances, inputNames, s);
		}
		
	}

*/
	public ArrayList<Predicate> getArrayPrecs() {
		
		return preconditions;
	}
	
	
	public class Tuple<X, Y> { 
		  public final X x; 
		  public final Y y; 
		  public Tuple(X x, Y y) { 
		    this.x = x; 
		    this.y = y; 
		  } 
		} 
	
	
}

//
//public boolean checkPreconditions(State s, Wagon w, int n){
//	boolean[] precs = {false, false, false, false};
//	
//	ArrayList<Predicate> predicates = s.getPredicate();
//	
//	for(Predicate p : predicates){
//		if(p.getClass().getName().equals(UsedRailways.class.getName())){
//			if(((UsedRailways)p).compareRailways(n)){
//				precs[0] = true;
//			}
//		}else if(p.getClass().getName().equals(OnStation.class.getName())){
//			if(p.isApplicable(w)){
//				precs[1] = true;
//			}
//		}else if(p.getClass().getName().equals(FreeLocomotive.class.getName())){
//			precs[2] = true;
//		}
//		else if(p.getClass().getName().equals(Free.class.getName())){
//			if(p.isApplicable(w)){
//				precs[3] = true;
//			}
//		}
//	}
//	
//	for(boolean p : precs){ if(!p){return false;} }
//	
//	return true;
//}
//
//
//public State applyOperator(State s, Wagon w){
//	ArrayList<Predicate> predicates = s.getPredicate();
//	UsedRailways usedRails = null;
//	OnStation onStation = null;
//	FreeLocomotive freeLoco = null;
//	Free free = null;
//	for(Predicate p : predicates){
//		if(p.getClass().getName().equals(UsedRailways.class.getName())){
//			usedRails = (UsedRailways) p;
//		}else if(p.getClass().getName().equals(OnStation.class.getName())){
//			onStation = (OnStation) p;
//		}else if(p.getClass().getName().equals(FreeLocomotive.class.getName())){
//			freeLoco = (FreeLocomotive) p;
//		}
//		else if(p.getClass().getName().equals(Free.class.getName())){
//			free = (Free) p;
//		}
//	}  
//	
//	
//	int n = usedRails.getRailways();
//	usedRails.decreaseRailways();
//	ArrayList<Predicate> after = new ArrayList<Predicate>(); 
//	after.add(usedRails);
//	ArrayList<Wagon> tmp = new ArrayList<Wagon>();
//	tmp.add(w);
//	after.add(new Towed(tmp));
//	s = new State(after);
//	return s;
//}
