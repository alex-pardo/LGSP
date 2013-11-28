package utils;

import java.util.ArrayList;

public final class SystemWagons {

	public static ArrayList<Wagon> system_wagons;
	
	public SystemWagons(ArrayList<Wagon> w){
		system_wagons = w;
	}
	
	
	public static Wagon searchWagon(String name){
		Wagon w = null;
		for(Wagon tmp : system_wagons){
			if(tmp.nameEquals(name)){w = tmp;}
		}
		return w;
	}
}
