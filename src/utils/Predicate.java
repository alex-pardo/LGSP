package utils;

import java.util.ArrayList;
import java.util.List;

public class Predicate {

	private ArrayList<Wagon> aplicable_wagons;
	
	
	
	public Predicate(String component, ArrayList<Wagon> wagons){
		
		if(component.length() > 2){
			aplicable_wagons = new ArrayList<Wagon>();
			for(String a_w : component.split(",")){
				for(Wagon w : wagons){
					if(w.nameEquals(a_w)){
						aplicable_wagons.add(w);
					}
				}
			}
		} else if(component.length() == 1){
			aplicable_wagons = new ArrayList<Wagon>();
			for(Wagon w : wagons){
				if(w.nameEquals(component)){
					aplicable_wagons.add(w);
				}
			}
		}
	}
	
	@Override
	public String toString() {
		String output = this.getClass().getName();
		if(aplicable_wagons != null){
			output = output.concat("(");
			boolean first = true;
			for(Wagon w : aplicable_wagons){
				if(!first){output = output.concat(",");}
				output = output.concat(w.toString());
				first = false;
			}
			output = output.concat(")");
		}
		return output;
	}
	
	
}
