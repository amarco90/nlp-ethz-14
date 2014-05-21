package ch.ethz.nlp;

/**
 * Example of use of the classes.
 * 
 * @author Álvaro Marco <malvaro@student.ethz.ch>
 * @author Amr Gamal <ahmeda@student.ethz.ch>
 */

import java.util.ArrayList;

public class Main {
	
	public static void main(String args[]) throws Exception {
		String generatedHeadlinesPath = "duc2004/eval/our_peers/"; // directory where the headlines will be stored
		NewsReader nr = new NewsReader();
		
		ArrayList<Task> tasks = nr.processNews();
		nr.storeHeadlines(generatedHeadlinesPath, tasks);
		
		SettingsGenerator sg = new SettingsGenerator();
		sg.generate(tasks, "our_peers", NewsReader.BASE_PATH + "eval/rouge.in.xml");
	}
	
}
