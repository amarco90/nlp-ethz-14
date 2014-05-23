package ch.ethz.nlp;

/**
 * This class reads the news and generates the headlines using the
 * <code>HeadlineGenerator</code> class.
 * 
 * @author Álvaro Marco <malvaro@student.ethz.ch>
 * @author Amr Gamal <ahmeda@student.ethz.ch>
 */

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class NewsReader {
	
	// Different paths that are used in the class, this probably has to be modified
	public static final String BASE_PATH = "duc2004/";
	public static final String NEWS_PATH = BASE_PATH +"docs/";
	public static final String MODELS_PATH = BASE_PATH +"eval/models/1/";
	
	/**
	 * Given an array of <code>File</code>s and a fileName returns an <code>ArrayList<String></code>
	 * of the files that contain the <code>String</code> fileName. This is used for finding the
	 * models for a given a peer.
	 * 
	 * @param files array with the files to be checked
	 * @param fileName the file name to be looked for
	 * @return ArrayList that contains the matches
	 */
	private static ArrayList<String> getMatches(File files[], String fileName) {
	    Pattern p = Pattern.compile("^.*("+ fileName+")$");
	    ArrayList<String> matches = new ArrayList<>();

	    for (File f : files) {
	        if (p.matcher(f.getName().trim()).matches())
	            matches.add(f.getName());
	    }

	    return matches;
	}
	
	/**
	 * Given a file, returns its contents in a String.
	 * 
	 * @param f the file to extract the contents
	 * @return the contents of the File in a String
	 */
	public String getFileContent(File f) throws Exception {
		String content = "";
		String currentLine = "";
		BufferedReader br = new BufferedReader(new FileReader(f));
		
		while ((currentLine = br.readLine()) != null)
			content = content + currentLine+" ";
		
		br.close();
		return content.trim();
	}
	
	/**
	 * This method goes through all the news, generates the headlines and creates
	 * the Task objects.
	 * 
	 * @return the list of tasks to be processed by ROUGE
	 */
	public ArrayList<Task> processNews() throws Exception {
		ArrayList<Task> tasks = new ArrayList<Task>();
		HeadlineGenerator hg = new HeadlineGenerator();
		HashMap<String, String> peers;
		ArrayList<String> models;
		
		File listOfFolders[] = new File(NEWS_PATH).listFiles();
		
		// loop through all folders
		for (File folder : listOfFolders) {
			if (folder.isDirectory() && folder.getName().charAt(0) != '.') { // skip hidden folders (like .DS_Store)
				File currentFolderListOfFiles[] = folder.listFiles();
				// loop through all news in a folder
				for (File newsFile : currentFolderListOfFiles) {
					if (!newsFile.isDirectory() && newsFile.getName().charAt(0) != '.') {
						peers = new HashMap<>();
						String fileContents = hg.getTextTagContent(getFileContent(newsFile));
						
						// generate headlines here and add them to the map
						String headline = hg.getFirstSentenceNaive(fileContents);
						peers.put("first_sentence_naive_"+ folder.getName() +"_"+ newsFile.getName(), headline);
						
						headline = hg.getFirstSentence(fileContents);
						peers.put("first_sentence_"+ folder.getName() +"_"+ newsFile.getName(), headline);
						
						// getting the models of the current news
						File listOfModels[] = new File(MODELS_PATH).listFiles();
						models = getMatches(listOfModels, newsFile.getName());
						
						Task t = new Task(peers, models);
						tasks.add(t);
					}
				}
			}
		}
		return tasks;
	}
	
	/**
	 * Stores the generated headlines (stored as Task objects) in the pathToWrite 
	 * directory.
	 * 
	 * @param pathToWrite the path where the headlines will be stored
	 * @param tasks the tasks to be stored
	 */
	public void storeHeadlines(String pathToWrite, ArrayList<Task> tasks) {
		// create the directories if needed
		new File(pathToWrite).mkdirs();
		
		for ( Task t : tasks ) {
			for ( String fileName : t.peers.keySet() ) {
				String filePath = pathToWrite + fileName;
				String content = t.peers.get(fileName);
				
				Writer writer = null;
				
				try {
					writer = new BufferedWriter(new OutputStreamWriter(
							new FileOutputStream(filePath), "utf-8"));
					writer.write(content);
				} catch (IOException ex) {
					ex.printStackTrace();
				} finally {
					try {writer.close();} catch (Exception ex) {}
				}
			}
		}
	}
	
}
