package ch.ethz.nlp;

/**
 * This class represents a task to be processed by ROUGE.
 * It contains the peers generated for a piece of news as a HashMap
 * (where the key is the name of the news file and the value the generated
 * headline).
 * It also contains the paths to the models associated to this piece of news.
 * 
 * @author Álvaro Marco <malvaro@student.ethz.ch>
 * @author Amr Gamal <ahmeda@student.ethz.ch>
 */

import java.util.*;

public class Task {
	
	/** The key of the HashMap is the name of the file that is going to 
	 * be created, the value is the generated headline */
	HashMap<String, String> peers;
	/** The models associated to the current peers */
	ArrayList<String> models;
	
	public Task(HashMap<String, String> peers, ArrayList<String> models){
		this.peers = peers;
		this.models = models;
	}
	
}
