package predicates;

import java.util.ArrayList;

import utils.Predicate;
import utils.Wagon;

public class UsedRailways extends Predicate {

	
	protected int used_railways = 3;
	
	public UsedRailways(String component) {
		super(component);
		// TODO Auto-generated constructor stub
	}

	public void increaseRailways(){
		used_railways++;
	}
	
	
	public void decreaseRailways(){
		used_railways--;
	}
	
	
	public boolean compareRailways(int r){
		return r==used_railways;
	}
	
	public int getRailways(){
		return used_railways;
	}
	
}
