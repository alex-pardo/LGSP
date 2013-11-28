package lgsp;


import utils.FileReader;
import utils.State;
import utils.SystemWagons;
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Problem problem = FileReader.readFile();
		
		System.out.println(problem);
		State s = problem.getInitialState();
		System.out.println(s);
		
		
	}

}
