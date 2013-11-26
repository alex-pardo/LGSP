package utils;

import java.util.ArrayList;
import java.util.List;

public class Predicate {

	private ArrayList<Wagon> applicable_wagons;
	
	/**
	 * 
	 * @param component
	 */
	
	public Predicate(String component){
		ArrayList<Wagon> all_wagons = SystemWagons.system_wagons;
		if(component.length() > 2){
			applicable_wagons = new ArrayList<Wagon>();
			for(String a_w : component.split(",")){
				for(Wagon w : all_wagons){
					if(w.nameEquals(a_w)){
						applicable_wagons.add(w);
					}
				}
			}
		} else if(component.length() == 1){
			applicable_wagons = new ArrayList<Wagon>();
			for(Wagon w : all_wagons){
				if(w.nameEquals(component)){
					applicable_wagons.add(w);
				}
			}
		}
	}
	
	
	public Predicate(ArrayList<Wagon> ws){
		applicable_wagons = new ArrayList<Wagon>();
		for(Wagon w : ws){
			applicable_wagons.add(w);
		}
	}
	
	public boolean isApplicable(Wagon x){
		for(Wagon w : applicable_wagons){
			if(w.nameEquals(x.getName())){
				return true;
			}
		}
		return false;
	}
	
	
	
	public ArrayList<Wagon> getWagon() {
		return applicable_wagons;
	}
	
	
	
	@Override
	public String toString() {
		String output = this.getClass().getName();
		if(applicable_wagons != null){
			output = output.concat("(");
			boolean first = true;
			for(Wagon w : applicable_wagons){
				if(!first){output = output.concat(",");}
				output = output.concat(w.toString());
				first = false;
			}
			output = output.concat(")");
		}
		return output;
	}
	
	
}
