package utils;

import java.util.ArrayList;



public final class PredicateCreator {

	public static final String ON_STATION = "ON-STATION";
	public static final String IN_FRONT_OF = "IN-FRONT-OF";
	public static final String FREE = "FREE";
	public static final String FREE_LOCOMOTIVE = "FREE-LOCOMOTIVE";
	public static final String TOWED = "TOWED";
	public static final String USED_RAILWAYS = "USED-RAILWAYS";
	public static final String LOADED = "LOADED";
	public static final String EMPTY = "EMPTY";
	
	public static Predicate createPredicate(String name){
		int par = name.indexOf('(');
		ArrayList<Object> comp = new ArrayList<Object>();
		String predicate;
		if( par == -1){
			predicate = name;
			comp = null;
		} else{
			Wagon tmp;
			for(String value : name.substring(par+1, name.length()-1).split(",")){
				if((tmp = SystemWagons.searchWagon(value)) != null){
					comp.add(tmp);
				} else{
					comp.add(value);
				}
				
			}
			predicate = name.substring(0, par);
		}
		return new Predicate(predicate, comp);
	}
}
