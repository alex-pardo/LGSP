package predicates;

import java.util.ArrayList;

import utils.Predicate;
import utils.Wagon;

public class Towed extends Predicate {

	public Towed(String component) {
		super(component);
		// TODO Auto-generated constructor stub
	}

	public Towed(ArrayList<Wagon> wagon) {
		super(wagon);
	}

}
