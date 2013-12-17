package lgsp;


import java.util.ArrayList;
import java.util.Stack;

import utils.FileReader;
import utils.Logger;
import utils.Operator;
import utils.OperatorFinder;
import utils.Predicate;
import utils.State;

/** 
 * Main class of the system
 * Contains the main loop for solving the problem
 * 
 * @author Alex Pardo & David Sanchez
 *
 */
public class Main {

	private static final int MAX_MEMORY = 5;
	private static int numStates = 0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Problem problem = FileReader.readFile();
		
		
		Stack<Object> stack = new Stack<Object>();
		State current_state = problem.getInitialState();
		System.out.println("\n"+current_state+"\n\n");
		Logger.writeString(current_state.toString());
		State ef = problem.getGoalState();
		System.out.println("\n"+ef+"\n");
		Logger.writeString(ef.toString());
		
		//Create the plan
		ArrayList<Operator> plan = new ArrayList<Operator>();
		ArrayList<Object> last_used = new ArrayList<Object>();
		
		//Add the final state in the stack
		stack.add(ef);
		stack.add(ef.getPredicate());
		
		//Add the predicates of the final state
		ArrayList<Predicate> predicates = ef.getPredicate();		
		for (Predicate p : ef.getPredicate()) stack.add(p);
		Object e = null;
		
		OperatorFinder operatorFinder = new OperatorFinder(); 
		
		
		
		//While the stack isn't empty
		int counter = 0;
		while(!stack.isEmpty()){
			counter ++;
			
			e = stack.pop(); //Get the top element
			
			//Operator
			if (e instanceof Operator) {
				Operator o = (Operator) e;
				
				//Apply the operator the current state.
				current_state = o.apply(current_state);
				
				//Add the operator into the plan
				plan.add(o);
				numStates++;
				
				current_state.setName(String.valueOf(numStates));
				
			//Predicate
			}else if(e instanceof Predicate) {
				Predicate p = (Predicate) e;
				
				Logger.writeString("Unstacking " + p.toString());
				
				//The predicate isn't in the current_state
				if(!current_state.hasThisPredicate(p)){
					
					if(p.equalsName("N<MAX")){ 	// change N<MAX by USED-RAILWAYS(n-1) because N<MAX not accomplished 
												// (checked on the previous if)
						p = new Predicate("USED-RAILWAYS", null, Operator.nminus1);	
					}
					// find the best operator
					ArrayList<Operator> op_list = operatorFinder.findOperator(p);
					
					// find the best operator
					double max_prec = 0;
					Operator o = (Operator) op_list.get(0).clone();
					ArrayList<Operator> sorted_list = new ArrayList<Operator>();
					for(Operator op : op_list){
						Operator o_tmp = (Operator) op.clone();
					 	o_tmp.instantiate(p.getInstances(), p.getInputNames(o_tmp), stack, current_state, plan, ef);
						double tmp = o_tmp.preconditionsAccomplished(current_state); // check for the number of satisfied preconditions
						if(tmp > max_prec){
							max_prec = tmp;
							o = (Operator) o_tmp.clone();
							sorted_list.add(0, (Operator) o_tmp.clone());
						} else{
							sorted_list.add((Operator) o_tmp.clone());
						}
					}
					do{
						o = sorted_list.remove(0);
						o.instantiate(p.getInstances(), p.getInputNames(o), stack, current_state, plan, ef);
					}while(last_used.contains(o) && sorted_list.size() > 0);
					
					// END find the best operator
					Logger.writeString("Stacking the instantiated operator: " + o.toString());
					stack.add(o);
					// add the operator to the memory
					if(last_used.size() >= MAX_MEMORY){
						last_used.remove(0);
					} 
					last_used.add(o);
					
					ArrayList<Predicate> tmp = o.getPreconditions();
					stack.add(tmp);

					for(Predicate prec : tmp){
						stack.add(prec);
					}
				}
				
			//ArrayList of Predicates
			}else if(e instanceof ArrayList<?>){
				ArrayList<Predicate> notDisponible = new ArrayList<Predicate>();
				ArrayList<Predicate> array = (ArrayList<Predicate>) e;
				for(Predicate p : array){
					if(!current_state.hasThisPredicate(p)){
						notDisponible.add(p);
					}
				}
				
				if(!notDisponible.isEmpty()){
					stack.add(array);
					for(Predicate p : notDisponible) stack.add(p);
				}
			}
		} // END main loop
		
		// Show the obtined results
		System.out.println("Finish the process!");
		Logger.writeString("---------------------------\n---------------------------\n");
		Logger.writeString("Final plan");
		Operator o;
		for(int i = 0; i < plan.size()-1; i++){
			o = plan.get(i);
			System.out.println(o);
			Logger.writeStringInLine(o.toString());
			Logger.writeStringInLine(" -> ");
		}
		Logger.writeString(plan.get(plan.size()-1).toString());
		
		System.out.println(current_state);
		Logger.writeString(current_state.toString());
		Logger.writeString("END");
		

		Logger.closeFile();
	}
}
