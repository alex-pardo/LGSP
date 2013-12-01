package utils;

import java.util.ArrayList;

public final class OperatorFinder {

	
	ArrayList<Operator> op_list;
	
	public OperatorFinder(){
		op_list = new ArrayList<Operator>();
		
		for(String name : Operator.OPERATOR_LIST){
			op_list.add(new Operator(name));
		}
	}
	
	public ArrayList<Operator> findOperator(Predicate p) {
		
		ArrayList<Operator> matching_operator = new ArrayList<Operator>();
		
		for(Operator o : op_list){
			if(o.hasAdd(p)){
				matching_operator.add(o);
			}
		}
		
		return matching_operator;
	}

	
	
}
