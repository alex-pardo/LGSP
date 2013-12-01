package utils;

import java.util.ArrayList;



public class Operator {

	
	public static final String[] OPERATOR_LIST = {"COUPLE", "PARK", "DETACH", "ATTACH", "LOAD", "UNLOAD"};
	
	
	
	private String name;
	private ArrayList<Predicate> preconditions;
	private ArrayList<Predicate> add;
	private ArrayList<Predicate> delete;
	private int op_type = -1;
	
	
	
	
	
	public Operator(String name){
		int i = 0;
		while(i < OPERATOR_LIST.length && op_type == -1){
			String s = OPERATOR_LIST[i];
			if(s.equals(name)){op_type = i;}
			i++;
		}
		this.name = name;
		constructLists();
		
	}
	
	
	private void constructLists() {
		preconditions = new ArrayList<Predicate>();
		add = new ArrayList<Predicate>();
		delete = new ArrayList<Predicate>();
		switch(op_type){
			case 0: 
				//COUPLE 
				//Preconditions: USED-RAILWAYS(n), ON-STATION(x), FREE-LOCOMOTIVE, FREE(x)
				//Eliminate: ON-STATION(x), FREE-LOCOMOTIVE, USED-RAILWAYS(n)
				//Add: TOWED(x), USED-RAILWAYS(n-1)
				preconditions.add(new Predicate("USED-RAILWAYS", null));
				preconditions.add(new Predicate("ON-STATION", null));
				preconditions.add(new Predicate("FREE-LOCOMOTIVE", null));
				preconditions.add(new Predicate("FREE", null));
				
				delete.add(new Predicate("ON-STATION", null));
				delete.add(new Predicate("FREE-LOCOMOTIVE", null));
				delete.add(new Predicate("USED-RAILWAYS", null));
				
				add.add(new Predicate("TOWED", null));
				add.add(new Predicate("USED-RAILWAYS", null));
				
				break;
			
			case 1:
				//PARK
				// Preconditions: TOWED(x), USED-RAILWAYS(n), n<max-railways
				// Eliminate: TOWED(x), USED-RAILWAYS(n)
				// Add: ON-STATION(x), USED-RAILWAYS(n+1), FREE-LOCOMOTIVE
				
				preconditions.add(new Predicate("TOWED", null));
				preconditions.add(new Predicate("USED-RAILWAYS", null));
				
				delete.add(new Predicate("TOWED", null));
				delete.add(new Predicate("USED-RAILWAYS", null));
				
				add.add(new Predicate("ON-STATION", null));
				add.add(new Predicate("USED-RAILWAYS", null));
				add.add(new Predicate("FREE-LOCOMOTIVE", null));
				
				break;
				
			case 2:
				// DETACH
				// Preconditions: IN-FRONT-OF(x,y), FREE(x), FREE-LOCOMOTIVE
				// Eliminate: IN-FRONT-OF(x,y), FREE-LOCOMOTIVE
				// Add: TOWED(x), FREE(y)
				
				preconditions.add(new Predicate("IN-FRONT-OF", null));
				preconditions.add(new Predicate("FREE", null));
				preconditions.add(new Predicate("FREE-LOCOMOTIVE", null));
				
				delete.add(new Predicate("IN-FRONT-OF", null));
				delete.add(new Predicate("FREE-LOCOMOTIVE", null));
				
				add.add(new Predicate("TOWED", null));
				add.add(new Predicate("FREE", null));
				
				break;
				
			case 3:
				// ATTACH
				// Preconditions: TOWED(x), FREE(y)
				// Eliminate: TOWED(x), FREE(y)
				// Add: IN-FRONT-OF(x,y), FREE-LOCOMOTIVE
				
				preconditions.add(new Predicate("TOWED", null));
				preconditions.add(new Predicate("FREE", null));
				
				delete.add(new Predicate("TOWED", null));
				delete.add(new Predicate("FREE", null));
				
				add.add(new Predicate("IN-FRONT-OF", null));
				add.add(new Predicate("FREE-LOCOMOTIVE", null));
				
				break;
				
			case 4:
				// LOAD
				// Preconditions: ON-STATION(x), EMPTY(x)
				// Eliminate: EMPTY(x)
				// Add: LOADED(x)
				
				preconditions.add(new Predicate("ON-STATION", null));
				preconditions.add(new Predicate("EMPTY", null));
				
				delete.add(new Predicate("EMPTY", null));
				
				add.add(new Predicate("LOADED", null));
				
				break;
				
			case 5:
				// UNLOAD
				// Preconditions: ON-STATION(x), LOADED(x)
				// Eliminate: LOADED(x)
				// Add: EMPTY(x)
				
				preconditions.add(new Predicate("ON-STATION", null));
				preconditions.add(new Predicate("LOADED", null));
				
				delete.add(new Predicate("LOADED", null));
				
				add.add(new Predicate("EMPTY", null));
				
				break;
			
			default:
				
				break;
		}
		
	}

	public boolean hasAdd(Predicate p){
		for(Predicate a : add){
			if(a.equalsName(p.getName())) return true;
		}
		return false;
	}

	public State apply(State current_state) {
		
		for(Predicate a : add){
			current_state.addPredicate(a);
		}
		
		for(Predicate d : delete){
			current_state.delPredicate(d);
		}
		
		
		return current_state;
	}
	
	public int preconditionsAccomplished(State s){
		int return_val = 0;
		for(Predicate p : s.getPredicate()){
			for(Predicate prec : preconditions){
				if(prec.equalsName(p.getName()))return_val ++;
			}
		}
		return return_val;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
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