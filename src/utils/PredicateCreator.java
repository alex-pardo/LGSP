package utils;

import java.util.ArrayList;

import operator.Load;
import predicates.Empty;
import predicates.Free;
import predicates.FreeLocomotive;
import predicates.InFrontOf;
import predicates.Loaded;
import predicates.OnStation;
import predicates.Towed;
import predicates.UsedRailways;

public final class PredicateCreator {

	public static final String ON_STATION = "ON-STATION";
	public static final String IN_FRONT_OF = "IN-FRONT-OF";
	public static final String FREE = "FREE";
	public static final String FREE_LOCOMOTIVE = "FREE-LOCOMOTIVE";
	public static final String TOWED = "TOWED";
	public static final String USED_RAILWAYS = "USED-RAILWAYS";
	public static final String LOADED = "LOADED";
	public static final String EMPTY = "EMPTY";
	
	public static Predicate createPredicate(String name, ArrayList<Wagon> w){
		int par = name.indexOf('(');
		String type;
		String comp;
		if( par == -1){
			type = name;
			comp = "";
		} else{
			type = name.substring(0, par);
			comp = name.substring(par+1, name.length()-1);
		}
		if(type.equals(ON_STATION)){
			return new OnStation(comp, w);
		} else if(type.equals(IN_FRONT_OF)){
			return new InFrontOf(comp, w);
		} else if(type.equals(FREE)){
			return new Free(comp, w);
		} else if(type.equals(FREE_LOCOMOTIVE)){
			return new FreeLocomotive(comp, w);
		} else if(type.equals(TOWED)){
			return new Towed(comp, w);
		} else if(type.equals(USED_RAILWAYS)){
			return new UsedRailways(comp, w);
		} else if(type.equals(LOADED)){
			return new Loaded(comp, w);
		} else if(type.equals(EMPTY)){
			return new Empty(comp, w);
		} else{
			return null;
		}
	}
}
