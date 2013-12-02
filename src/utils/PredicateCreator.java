package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



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
		
		//Declarate the possible variables
        String[] string = new String[]{"x" , "y"};
		List<String> xy = Arrays.asList(string);
        string = new String[]{"y"};
		List<String> y = Arrays.asList(string);
        string = new String[]{"x"};
		List<String> x = Arrays.asList(string);
        string = new String[]{"n"};
		List<String> n = Arrays.asList(string);
        string = new String[]{};		
        List<String> nonVariables = Arrays.asList(string);
        List<String> inputNames = null;
        
        //Assign the name of the variable at each predicate
		if(predicate.equals("ON-STATION")){
			inputNames= x;
		}else if(predicate.equals("IN-FRONT-OF")){
			inputNames = xy;
		}else if(predicate.equals("FREE")){
			inputNames = x;
		}else if(predicate.equals("FREE-LOCOMOTIVE")){
			inputNames = nonVariables;
		}else if(predicate.equals("TOWED")){
			inputNames = x;
		}else if(predicate.equals("USED-RAILWAYS")){
			inputNames = n;
		}else if(predicate.equals("LOADED")){
			inputNames = x;
		}else if(predicate.equals("EMPTY")){
			inputNames = x; 
		}
		return new Predicate(predicate, comp, inputNames);
	}
}
