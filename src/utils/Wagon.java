package utils;

/** 
 * Class used to represent the wagons
 * @author Alex Pardo & David Sanchez
 *
 */
public class Wagon  {

	private String wagon_name;
	
	public Wagon(String name){
		wagon_name = name;
	}

	@Override
	public String toString() {
		return wagon_name;
	}
	
	
	
	@Override
	public boolean equals(Object obj) {		
		return wagon_name.equals(((Wagon)obj).getName());
	}

	public boolean nameEquals(String name){
		return wagon_name.equals(name);
	}
	
	public String getName(){
		return wagon_name;
	}
	
	@Override
	public Object clone(){
		return new Wagon(this.wagon_name);
	}
	
	
}
