package lgsp;

import utils.FileReader;
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Problem problem = FileReader.readFile();
		
		System.out.println(problem);
	}

}
