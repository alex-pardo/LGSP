package utils;

import java.util.ArrayList;



public class Operator {

	
	
}

//
//public boolean checkPreconditions(State s, Wagon w, int n){
//	boolean[] precs = {false, false, false, false};
//	
//	ArrayList<Predicate> predicates = s.getPredicate();
//	
//	for(Predicate p : predicates){
//		if(p.getClass().getName().equals(UsedRailways.class.getName())){
//			if(((UsedRailways)p).compareRailways(n)){
//				precs[0] = true;
//			}
//		}else if(p.getClass().getName().equals(OnStation.class.getName())){
//			if(p.isApplicable(w)){
//				precs[1] = true;
//			}
//		}else if(p.getClass().getName().equals(FreeLocomotive.class.getName())){
//			precs[2] = true;
//		}
//		else if(p.getClass().getName().equals(Free.class.getName())){
//			if(p.isApplicable(w)){
//				precs[3] = true;
//			}
//		}
//	}
//	
//	for(boolean p : precs){ if(!p){return false;} }
//	
//	return true;
//}
//
//
//public State applyOperator(State s, Wagon w){
//	ArrayList<Predicate> predicates = s.getPredicate();
//	UsedRailways usedRails = null;
//	OnStation onStation = null;
//	FreeLocomotive freeLoco = null;
//	Free free = null;
//	for(Predicate p : predicates){
//		if(p.getClass().getName().equals(UsedRailways.class.getName())){
//			usedRails = (UsedRailways) p;
//		}else if(p.getClass().getName().equals(OnStation.class.getName())){
//			onStation = (OnStation) p;
//		}else if(p.getClass().getName().equals(FreeLocomotive.class.getName())){
//			freeLoco = (FreeLocomotive) p;
//		}
//		else if(p.getClass().getName().equals(Free.class.getName())){
//			free = (Free) p;
//		}
//	}  
//	
//	
//	int n = usedRails.getRailways();
//	usedRails.decreaseRailways();
//	ArrayList<Predicate> after = new ArrayList<Predicate>(); 
//	after.add(usedRails);
//	ArrayList<Wagon> tmp = new ArrayList<Wagon>();
//	tmp.add(w);
//	after.add(new Towed(tmp));
//	s = new State(after);
//	return s;
//}