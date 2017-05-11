
package com.weberfly.util.opensource.features;

import com.weberfly.util.opensource.dataobjects.Document;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;


public class TextTokenizer {

    private static String[] unusedWord = {"i", "he", "she", "we", "you", "they", "that", "her", "there"};
    private static StringTokenizer st = new StringTokenizer("i,he,she,we,you,they,that,her,there");

    /**
     * Preprocess the text by removing punctuation, duplicate spaces and
     * lowercasing it.
     *
     * @param text
     * @return
     */
    public static String preprocess(String text) {
//        while (st.hasMoreTokens()) {
//            text.split(st.nextToken(","));
//        }

        return text.replaceAll("\\p{P}", " ").replaceAll("\\s+", " ").toLowerCase(Locale.getDefault());
    }

    /**
     * A simple method to extract the keywords from the text. For real world
     * applications it is necessary to extract also keyword combinations.
     *
     * @param text
     * @return
     */
    public static String[] extractKeywords(String text) {
        return text.split(" ");
    }

    /**
     * Counts the number of occurrences of the keywords inside the text.
     *
     * @param keywordArray
     * @return
     */
    public static Map<String, Integer> getKeywordCounts(String[] keywordArray) {
        Map<String, Integer> counts = new HashMap<>();

        Integer counter;
        for (int i = 0; i < keywordArray.length; ++i) {
            counter = counts.get(keywordArray[i]);
            if (counter == null) {
                counter = 0;
            }
            counts.put(keywordArray[i], ++counter); //increase counter for the keyword
        }

        return counts;
    }

    /**
     * Tokenizes the document and returns a Document Object.
     *
     * @param text
     * @return
     */
    public static Document tokenize(String text) {
        String preprocessedText = preprocess(text);// remove all (/ et ' , ... .. )
        // System.out.println(preprocessedText);
        String[] keywordArray = extractKeywords(preprocessedText);
        for (String string : keywordArray) {
           // System.out.println(string);
        }
        Document doc = new Document();
        doc.tokens = getKeywordCounts(keywordArray);
        return doc;
    }
}
