package lgsp;


import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import com.sun.xml.internal.stream.util.BufferAllocator;

import utils.FileReader;
import utils.Operator;
import utils.OperatorFinder;
import utils.Predicate;
import utils.State;
import utils.SystemWagons;
public class Main {

	private static final int MAX_MEMORY = 2;
	private static int numStates = 0;
	private static boolean DEBUG = true;
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
		//System.out.println("\n"+current_state+"\n\n");

		State ef = problem.getGoalState();
		System.out.println("\n"+ef+"\n");
		//Create the plan
		ArrayList<Operator> plan = new ArrayList<Operator>();
		ArrayList<Object> last_used = new ArrayList<Object>();
		//Add the final state in the stack
		stack.add(ef);
		stack.add(ef.getPredicate());
		
		//Add the predicates of the final state
		int i;
		ArrayList<Predicate> predicates = ef.getPredicate();
//		for(i=predicates.size()-1;i>=0;i--){
//			stack.add(predicates.get(i));
//		}
		
		for (Predicate p : ef.getPredicate()) stack.add(p);
		Object e = null;
		
		OperatorFinder operatorFinder = new OperatorFinder(); 
		
		
		//System.out.println(current_state);
		
		//While the stack isn't empty
		while(!stack.isEmpty()){
			System.out.println("-------");
			e = stack.pop();//Get the top element
			
			//Operator
			if (e instanceof Operator) {
				Operator o = (Operator) e;
				//System.out.println("Unstack" + o);
				//Applicate the operator the current state.
				//TODO
				current_state = o.apply(current_state);
				//Add the operator into the plan
				System.out.println("--------Applying plan: " + o);
				plan.add(o);
				numStates++;
				current_state.setName(String.valueOf(numStates));
				//System.out.println(current_state+"\n\n");
				//System.out.println("Stack: \n"+ stack);
				
			//Predicate
			}else if(e instanceof Predicate) {
				Predicate p = (Predicate) e;
				if(DEBUG)System.out.println("Unstack: " + p);
				//if(DEBUG)System.out.println(current_state.hasThisPredicate(p));
				//if(current_state.hasThisPredicate(p)) System.out.println(current_state);
				//The predicate isn't in the current_state
				
				if(!current_state.hasThisPredicate(p)){
					if(p.equalsName("N<MAX")){
						p = new Predicate("USED-RAILWAYS", null, Operator.nminus1);	
					}
					ArrayList<Operator> op_list = operatorFinder.findOperator(p);
					
					double max_prec = 0;
					Operator o = (Operator) op_list.get(0).clone();
					ArrayList<Operator> sorted_list = new ArrayList<Operator>();
					//do{
					for(Operator o_tmp : op_list){
						 	o_tmp.instantiate(p.getInstances(), p.getInputNames(o_tmp), stack, current_state, plan);
							double tmp = o_tmp.preconditionsAccomplished(current_state);
							System.out.println(o_tmp + ": "+ tmp);
							if(tmp > max_prec){
								max_prec = tmp;
								o = (Operator) o_tmp.clone();
								sorted_list.add(0, (Operator) o_tmp.clone());
							} else{
								sorted_list.add((Operator) o_tmp.clone());
							}
					}
					if(DEBUG)System.out.println("List:"+sorted_list);
					do{
						o = sorted_list.remove(0);
						o.instantiate(p.getInstances(), p.getInputNames(o), stack, current_state, plan);
						if(DEBUG)System.out.println("Select operator:"+o);
					}while(last_used.contains(o) && sorted_list.size() > 0);
					if(DEBUG)System.out.println("Stack " + o);
					stack.add(o);
					if(last_used.size() >= MAX_MEMORY){
						last_used.remove(0);
					} 
					last_used.add(o);
					
					ArrayList<Predicate> tmp = o.getArrayPrecs();
					stack.add(tmp);
//					for(i=tmp.size()-1;i>=0;i--){
//						stack.add(tmp.get(i));
//					}
					for(Predicate prec : tmp){
						stack.add(prec);
					}
				} else{
					// do nothing
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
//					for(i=notDisponible.size()-1;i>=0;i--){
//						stack.add(notDisponible.get(i));
//					}
					for(Predicate p : notDisponible) stack.add(p);
				}
			}
		}
		
		System.out.println("Finish the process!");
		for(Operator o : plan){
			System.out.println(o);
		}
		System.out.println(current_state);
	}
}
