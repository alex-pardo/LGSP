package lgsp;

import java.util.ArrayList;

import utils.State;
import utils.Wagon;

public class Problem {

	
	private State initial;
	private State goal;
	private ArrayList<Wagon> objects;
	
	public Problem(ArrayList<Wagon> wagons, State i, State g){
		objects = wagons;
		initial = i;
		goal = g;
		
	}
	
	public State getGoalState(){
		return goal;
	}
	
	public State getInitialState(){
		return initial;
	}

	@Override
	public String toString() {
		String output = "-------------\n";
		output = output.concat("  OBJECTS\n");
		output = output.concat("-------------\n");
		
		boolean first = true;
		for(Wagon w : objects){
			if(!first){output = output.concat(",");}
			output = output.concat(w.toString());
			first = false;
		}
		output = output.concat("\n");
		output = output.concat(initial.toString());
		output = output.concat(goal.toString());
		
		return output;
		
	}
	
	
	
}
