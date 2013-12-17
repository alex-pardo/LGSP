package utils;

import java.util.ArrayList;

/** 
 * Class used to maintain the wagons present on the system 
 * @author Alex Pardo & David Sanchez
 *
 */
public final class SystemWagons {

	public static ArrayList<Wagon> system_wagons;
	
	public SystemWagons(ArrayList<Wagon> w){
		system_wagons = w;
	}
	
	/**
	 * Finds a wagon with a certain name
	 * @param name
	 * @return
	 */
	public static Wagon searchWagon(String name){
		Wagon w = null;
		for(Wagon tmp : system_wagons){
			if(tmp.nameEquals(name)){w = tmp;}
		}
		return w;
	}
}
