package ch.ethz.nlp;

/**
 * This class generates the settings document to be used by ROUGE.
 * 
 * @author Álvaro Marco <malvaro@student.ethz.ch>
 * @author Amr Gamal <ahmeda@student.ethz.ch>
 */

import java.io.*;
import java.util.ArrayList;

public class SettingsGenerator {
	
	/**
	 * This method generates the settings document to be used by ROUGE with the
	 * list of tasks to be processed.
	 * 
	 * @param tasks <code>ArrayList</code> of tasks to be processed by ROUGE
	 * @param peerRoot the directory where the peers are stored
	 * @param outputPath where the ROUGE settings will be stored
	 */
	public void generate(ArrayList<Task> tasks, String peerRoot, String outputPath) {
		PrintWriter pw = null;
		int evalId = 1;
		int peersId, modelsId;
		
		try {
			pw = new PrintWriter(outputPath, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		pw.println("<ROUGE_EVAL version=\"1.0\">");
		for (Task t : tasks) {
			peersId = modelsId = 1;
			
			pw.println("<EVAL ID=\""+ evalId +"\">");
			
			pw.println("<PEER-ROOT>"+ peerRoot +"</PEER-ROOT>");
			pw.println("<MODEL-ROOT>./models/1</MODEL-ROOT>");
			pw.println("<INPUT-FORMAT TYPE=\"SPL\"></INPUT-FORMAT>");
			
			pw.println("<PEERS>");
			for (String peer : t.peers.keySet())
				pw.println("<P ID=\""+ peersId++ +"\">"+ peer +"</P>");
			pw.println("</PEERS>");
			
			pw.println("<MODELS>");
			for (String model : t.models)
				pw.println("<M ID=\""+ modelsId++ +"\">"+ model +"</M>");
			pw.println("</MODELS>");
			
			pw.println("</EVAL>");
			++evalId;
		}
		pw.println("</ROUGE_EVAL>");
	
		pw.close();
	}
	
}
