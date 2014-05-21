package ch.ethz.nlp;

/**
 * This class contains headline generators and a small set of utilities
 * to be used for the headline generation task.
 * 
 * @author Álvaro Marco <malvaro@student.ethz.ch>
 * @author Amr Gamal <ahmeda@student.ethz.ch>
 */

import java.io.*;
import java.util.*;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;

public class HeadlineGenerator {
	
	/**
	 * Given the content of the file returns the news text
	 * 
	 * @param text the content of the file
	 * @return the news text
	 */
	public String getTextTagContent(String text) {
		String beginTag = "<TEXT>";
		String endTag = "</TEXT>";
		return text.substring(text.indexOf(beginTag) + beginTag.length(), text.indexOf(endTag)).trim();
	}
	
	/**
	 * First approach for getting the first line of the news. Looks for the
	 * first '.' in the news and returns the text from the beginning until 
	 * that point.
	 * 
	 * @param text the news text
	 * @return the text from the beginning of the text until the first '.'
	 */
	public String getFirstSentenceNaive(String text) {
		return text.substring(0, text.indexOf('.')).replaceAll("\n", "").trim();
	}
	
	/**
	 * Gets the first sentence of a piece of news using the Stanford NLP library.
	 * 
	 * @param text the news text
	 * @return the first sentence of the piece of news
	 */
	public String getFirstSentence(String text) {
		Reader reader = new StringReader(text);
		DocumentPreprocessor dp = new DocumentPreprocessor(reader);
		
		Iterator<List<HasWord>> it = dp.iterator();
		StringBuilder sentenceSb = new StringBuilder();
		if (it.hasNext()) {
			List<HasWord> sentence = it.next();
			for (HasWord token : sentence) {
				if(sentenceSb.length()>=1) {
					sentenceSb.append(" ");
				}
				sentenceSb.append(token);
			}
		}
		
		return sentenceSb.toString();
	}
	
	/**
	 * Given a text returns the tokens contained in it.
	 * 
	 * @param text the text to extract tokens from
	 * @return <code>ArrayList<String></code> containing the tokens
	 */
	public ArrayList<String> getTokens(String text) {
		Reader reader = new StringReader(text);
		DocumentPreprocessor dp = new DocumentPreprocessor(reader);
		
		ArrayList<String> tokens = new ArrayList<String>();
		Iterator<List<HasWord>> it = dp.iterator();
		while (it.hasNext()) {
			List<HasWord> sentence = it.next();
			for (HasWord token : sentence)
				tokens.add(token.toString());
		}
		
		return tokens;
	}
	
}
