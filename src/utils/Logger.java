package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class used to log the results into a file
 * @author Alex Pardo & David Sanchez
 *
 */
public final class Logger {

	private static BufferedWriter bw;
	private static File fout;
	private static FileWriter out;
	
	public Logger(String problem_name){
		fout = new File(System.getProperty("user.dir")+ "/log/LOG_"+problem_name+".txt");
		try {
			out = new FileWriter(fout);
			bw = new BufferedWriter(out);
		} catch (IOException e) {
			System.out.println("Problem creating file");
			System.exit(0);
		}
		
	}
	
	/**
	 * Writes an string and adds an EOL character
	 * @param line
	 */
	public static void writeString(String line){
		try {
			bw.write(line);
			bw.write("\n");
		} catch (IOException e) {
			System.out.println("Problem writing to file");
			System.exit(0);
		}
	}
	
	/**
	 * Writes an string WITHOUT adding an EOL character
	 * @param line
	 */
	public static void writeStringInLine(String line) {
		try {
			bw.write(line);
		} catch (IOException e) {
			System.out.println("Problem writing to file");
			System.exit(0);
		}
		
	}
	
	/**
	 * Closes the opened file
	 */
	public static void closeFile(){
		try {
			bw.close();
		} catch (IOException e) {
			System.out.println("Problem closing file");
			System.exit(0);
		}
		
	}

	
}
