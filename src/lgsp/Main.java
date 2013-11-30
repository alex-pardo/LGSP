package lgsp;


import java.util.ArrayList;
import java.util.Stack;

import utils.FileReader;
import utils.Operator;
import utils.Predicate;
import utils.State;
import utils.SystemWagons;
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Problem problem = FileReader.readFile();
		
//		System.out.println(problem);
//		System.out.println(s);
		
		//Start the algorithm
		Stack<Object> stack = new Stack<Object>();
		State current_state = problem.getInitialState();
		State ef = problem.getGoalState();
		
		//Create the plan
		ArrayList<Operator> plan = new ArrayList<Operator>();
		
		//Add the final state in the stack
		stack.add(ef);
		//Add the predicates of the final state
		for (Predicate p : ef.getPredicate()) stack.add(p);
		Object e = null;
		
		
		
		//While the stack isn't empty
		while(!stack.isEmpty()){
			e = stack.get(0); //Get the top element
			
			//Operator
			if (e instanceof Operator) {
				Operator o = (Operator) e;
				//Applicate the operator the current state.
				//TODO
				
				//Add the operator into the plan
				plan.add(o);
				
			//Predicate
			}else if(e instanceof Predicate) {
				Predicate p = (Predicate) e;
				
				//The predicate isn't in the current_state
				if(!current_state.hasThisPredicate(p)){
					
				}
				
			//ArrayList of the Predicates
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
		}
	}
}
