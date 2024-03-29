package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;


/**
 * Defines an operator, its preconditions, add and delete list
 * 
 * @author Alex Pardo & David Sanchez
 *
 */
public class Operator {

	
	public static final String[] OPERATOR_LIST = {"COUPLE", "PARK", "DETACH", "ATTACH", "LOAD", "UNLOAD"};
	private static final int[] needed_objects = {1,1,2, 2,1,1};
	private String name;
	private ArrayList<Predicate> preconditions;
	private ArrayList<Predicate> add;
	private ArrayList<Predicate> delete;
	private int op_type = -1;
	private ArrayList<Object> input = null;
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
	
	
	@Override
	public boolean equals(Object o){
		boolean val = true;
		if(((Operator)o).getName().equals(this.name)){
			for(Object tmp : ((Operator)o).getInput()){
				if(!this.input.contains(tmp)) val = false;
			}
		}else{
			val = false;
		}
		
		return val;
	}
	/*
	 * Create the list used for the instantiation
	 */
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

	/**
	 * Returns whether an operator has a predicate on its add list
	 * @param p
	 * @return
	 */
	public boolean hasAdd(Predicate p){
		boolean value = false;
		for(Predicate a : add){
			if(p.getName().equals("USED-RAILWAYS")){
				if(a.getInputNames() != null && a.equalsName(p.getName()) && a.getInputNames().get(0).equals(p.getInputNames().get(0))) return true;
			} else{
				if(a.equalsName(p.getName())) return true;
			}
			
		}
		return false;
	}

	/**
	 * Applies the operator to an state
	 * @param current_state
	 * @return
	 */
	public State apply(State current_state) {
		
		for(Predicate a : add){
			if(a.getName().equals("USED-RAILWAYS")){
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
	
	/**
	 * Checks if the preconditions of the operator are satisfied in an specific state
	 * @param s
	 * @return
	 */
	public double preconditionsAccomplished(State s){
		double return_val = 0;
		double total_precs = preconditions.size();
		for(Predicate prec : preconditions){
			if(prec.equalsName("N<MAX")){
				if(s.railwaysLower(Predicate.MAX_RAILWAYS)){
					return_val ++;
				}
			}else{
				for(Predicate p : s.getPredicate()){
					if(prec.equals(p)){
						return_val ++;
					} 

				}
			}
		}
		return return_val/total_precs;
	}

	/*
	 * ##########################################################################
	 * 							Getters and setters
	 * ##########################################################################
	 */
	
	
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
	
	/*
	 * ########################################################################## 
	 * 							END Getters and setters
	 * ##########################################################################
	 */
	

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
				try{
					out = out.concat(o.toString());
				} catch(Exception e){
					System.out.println(name);
				}
				first = false;
			}
			out = out.concat(")");
		}
		return out;
	}
	
	@Override
	public Object clone(){
		return new Operator(this.name);
	}
	

	
	/**
	 * Instantiates the operator by using some metrics
	 * @param instances
	 * @param names
	 * @param s
	 * @param curr
	 * @param plan
	 * @param fs
	 */
	public void instantiate(ArrayList<Object> instances, List<String> names, Stack<Object> s, State curr, ArrayList<Operator> plan, State fs){
		if(input == null){
			
			ArrayList<Object> instantiation_array =  new ArrayList<Object>();
			for(int i = 0; i < needed_objects[op_type] ; i++) instantiation_array.add(null);
			
			ArrayList<Wagon> not_assigned = new ArrayList<Wagon>();
			for(Wagon tmp : SystemWagons.system_wagons){not_assigned.add(tmp);}
			
			if(instances != null && instances.size() == needed_objects[op_type]){
				for(int i = 0; i < instances.size(); i++){
					if(((String)names.get(i)).equals("x")){
						instantiation_array.remove(0);
						instantiation_array.add(0, instances.get(i));
						not_assigned.remove(instances.get(i));
					} else if (!((String)names.get(i)).contains("n")){
						instantiation_array.remove(1);
						instantiation_array.add(instances.get(i));
						not_assigned.remove(instances.get(i));
					}
				}
			}else{
				if(instances != null){
					for(int i = 0; i < instances.size(); i++){
						if(((String)names.get(i)).equals("x")){
							instantiation_array.remove(0);
							instantiation_array.add(0, instances.get(i));
							not_assigned.remove(instances.get(i));
						} else if (!((String)names.get(i)).contains("n")){
							instantiation_array.remove(1);
							instantiation_array.add(instances.get(i));
							not_assigned.remove(instances.get(i));
						}
					}
				}
				
				int max_wagon = 0;
				Wagon best = null;
				boolean first_null = false;
				Object first_wagon = null;
				if(instantiation_array.get(0) == null){
					first_null = true;
				} else{
					first_wagon = instantiation_array.get(0);
				}
				while(instantiation_array.contains(null)){
					for(int i = 0; i < instantiation_array.size(); i++){
						if(instantiation_array.get(i) == null){
							ArrayList<Tuple<Integer,Wagon>> bestList = new ArrayList<Tuple<Integer,Wagon>> ();
							for(Wagon w : not_assigned){
								
								int tmp_counter = 0;
								for(Predicate a : preconditions){
									for(Predicate tmp : curr.getPredicate()){
										double weight = 1;
										if(tmp == null || tmp.getInstances() == null) continue;
										if(tmp.equalsName(a.getName())){
											if(tmp.hasInstances(instantiation_array, w) > 1) weight += 1.5;
																					
											
											for(Object o : tmp.getInstances()){
												if(tmp.getName().equals("ON-STATION") && name.equals("COUPLE") && tmp.getInput().get(0).equals(w)) weight += 3;
												if(!first_null && tmp.getName().equals("IN-FRONT-OF") && name.equals("DETACH") && tmp.getInput().get(0).equals(first_wagon) && tmp.getInput().get(1).equals(w)) weight += 5;
												if(!first_null && tmp.getName().equals("IN-FRONT-OF") && name.equals("DETACH") && (tmp.getInput().get(0).equals(w) || tmp.getInput().get(1).equals(w))) weight += 3;
												if(tmp.getName().equals("TOWED") && name.equals("PARK")&& tmp.getInput().get(0).equals(w)) weight += 1;
												if(tmp.getName().equals("ON-STATION") && name.equals("LOAD")&& tmp.getInput().get(0).equals(w)) weight += 3;
												if(tmp.getName().equals("ON-STATION") && name.equals("UNLOAD")&& tmp.getInput().get(0).equals(w)) weight += 3;
																							
												if(((Wagon) o).nameEquals(w.getName())) tmp_counter+=1*weight;
											}
										}
									}
									
								}
								
								
								
								Tuple<Integer, Wagon> tuple;
								
								if(tmp_counter > max_wagon){
									max_wagon = tmp_counter;
									best = (Wagon) w.clone();
									//Delete all the elements of the list
									bestList = new ArrayList<Tuple<Integer,Wagon>> ();
									tuple = new Tuple<Integer, Wagon>(tmp_counter,best);
									bestList.add(tuple);
								}else if (tmp_counter == max_wagon){
									best = (Wagon) w.clone();
									tuple = new Tuple<Integer, Wagon>(tmp_counter,best);
									bestList.add(tuple);
								}
							}
		
							if(best == null){
								System.out.println("RANDOM");
								int tmp = 0 + (int)(Math.random() * (((bestList.size())-1 - 0) + 1));
								instantiation_array.remove(i);
								instantiation_array.add(i,bestList.get(tmp).y);
								not_assigned.remove(tmp);
							} else{
								if(bestList.size()==1){
									instantiation_array.remove(i);
									instantiation_array.add(i,best);
									not_assigned.remove(best);
								}else{
									int tmp = 0 + (int)(Math.random() * (((bestList.size())-1 - 0) + 1));
									instantiation_array.remove(i);
									instantiation_array.add(i,bestList.get(tmp).y);
									not_assigned.remove(not_assigned.indexOf(bestList.get(tmp).y));
								}
							}
							max_wagon = 0;
							best = null;
						}
					}
					
					
						
					if(plan.size() > 0){
						
						if(name.equals(plan.get(plan.size()-1).getName()) && !instantiation_array.contains(null)){
							Operator tmp = plan.get(plan.size()-1);
							if(tmp.getName().equals("DETACH")){
								ArrayList<Object> wagons = tmp.getInput();
								if(((Wagon)wagons.get(0)).getName().equals(((Wagon)instantiation_array.get(1)).getName()) &&
										((Wagon)wagons.get(1)).getName().equals(((Wagon)instantiation_array.get(0)).getName())){
									instantiation_array.remove(0);
									instantiation_array.add(0,null);
								}
							}
						}
						
						
						if(name.equals("ATTACH") && !instantiation_array.contains(null)){
							
							Operator tmp = plan.get(plan.size()-1);
							if(tmp.getName().equals("DETACH")){
								ArrayList<Object> wagons = tmp.getInput();
								if(((Wagon)wagons.get(0)).getName().equals(((Wagon)instantiation_array.get(0)).getName()) &&
										((Wagon)wagons.get(1)).getName().equals(((Wagon)instantiation_array.get(1)).getName())){
									instantiation_array.remove(1);
									instantiation_array.add(null);
								}
							}
						}
					}
				}
			}
			
			input = instantiation_array;
			
			
		}
		
		
		switch(op_type){
		case 0: 
			//COUPLE 
			//Preconditions: USED-RAILWAYS(n), ON-STATION(x), FREE-LOCOMOTIVE, FREE(x)
			//Eliminate: ON-STATION(x), FREE-LOCOMOTIVE, USED-RAILWAYS(n)
			//Add: TOWED(x), USED-RAILWAYS(n-1)
			
			for(Predicate prec : preconditions){
				prec.Instantiate(input);
			}
			
			for(Predicate a : add){
				a.Instantiate(input);
			}
			
			for(Predicate d : delete){
				d.Instantiate(input);
			}
			
			break;
		
		case 1:
			//PARK
			// Preconditions: TOWED(x), USED-RAILWAYS(n), n<max-railways
			// Eliminate: TOWED(x), USED-RAILWAYS(n)
			// Add: ON-STATION(x), USED-RAILWAYS(n+1), FREE-LOCOMOTIVE
			
			for(Predicate prec : preconditions){
				prec.Instantiate(input);
			}
			
			for(Predicate a : add){
				a.Instantiate(input);
			}
			
			for(Predicate d : delete){
				d.Instantiate(input);
			}
			
			break;
			
		case 2:
			// DETACH
			// Preconditions: IN-FRONT-OF(x,y), FREE(x), FREE-LOCOMOTIVE
			// Eliminate: IN-FRONT-OF(x,y), FREE-LOCOMOTIVE
			// Add: TOWED(x), FREE(y)
			
			for(Predicate prec : preconditions){
				prec.Instantiate(input);
			}
			
			for(Predicate a : add){
				if(a.getName().equals("FREE")){
					a.Instantiate(input.get(1));
				}else{
					a.Instantiate(input);
				}
			}
			
			for(Predicate d : delete){
				d.Instantiate(input);
			}
			
			break;
			
		case 3:
			// ATTACH
			// Preconditions: TOWED(x), FREE(y)
			// Eliminate: TOWED(x), FREE(y)
			// Add: IN-FRONT-OF(x,y), FREE-LOCOMOTIVE
			for(Predicate prec : preconditions){
				if(prec.getName().equals("FREE")){
					prec.Instantiate(input.get(1));
				}else{
					prec.Instantiate(input);
				}
			}
			
			for(Predicate a : add){
				a.Instantiate(input);
			}
			
			for(Predicate d : delete){
				if(d.getName().equals("FREE")){
					d.Instantiate(input.get(1));
				} else{
					d.Instantiate(input);
				}
				
			}
			break;
			
		case 4:
			// LOAD
			// Preconditions: ON-STATION(x), EMPTY(x)
			// Eliminate: EMPTY(x)
			// Add: LOADED(x)
			for(Predicate prec : preconditions){
				prec.Instantiate(input);
			}
			
			for(Predicate a : add){
				a.Instantiate(input);
			}
			
			for(Predicate d : delete){
				d.Instantiate(input);
			}
			break;
			
		case 5:
			// UNLOAD
			// Preconditions: ON-STATION(x), LOADED(x)
			// Eliminate: LOADED(x)
			// Add: EMPTY(x)
			for(Predicate prec : preconditions){
				prec.Instantiate(input);
			}
			
			for(Predicate a : add){
				a.Instantiate(input);
			}
			
			for(Predicate d : delete){
				d.Instantiate(input);
			}
			break;
		
		default:
			break;
	}
		
		
		
		
	}


	
	
	/**
	 * Class used on the instantiation
	 * 
	 *
	 * @param <X>
	 * @param <Y>
	 */
	public class Tuple<X, Y> { 
		  public final X x; 
		  public final Y y; 
		  public Tuple(X x, Y y) { 
		    this.x = x; 
		    this.y = y; 
		  } 
		}


	
	
}

