package utils;

public class Wagon extends Constant {

	private String wagon_name;
	
	public Wagon(String name){
		wagon_name = name;
	}

	@Override
	public String toString() {
		return wagon_name;
	}
	
	public boolean nameEquals(String name){
		return wagon_name.equals(name);
	}
	
	public String getName(){
		return wagon_name;
	}
	
	
}
