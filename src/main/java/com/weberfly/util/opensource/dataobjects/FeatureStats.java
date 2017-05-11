
package com.weberfly.util.opensource.dataobjects;

import java.util.HashMap;
import java.util.Map;

/**
 * The FeatureStats Object stores all the fields generated by the FeatureExtraction
 * class.
 * 
 * @author Vasilis Vryniotis <bbriniotis at datumbox.com>
 * @see <a href="http://blog.datumbox.com/developing-a-naive-bayes-text-classifier-in-java/">http://blog.datumbox.com/developing-a-naive-bayes-text-classifier-in-java/</a>
 */
public class FeatureStats {
    /**
     * total number of Observations
     */
    public int n;
    
    /**
     * It stores the co-occurrences of Feature and Category values
     */
    public Map<String, Map<String, Integer>> featureCategoryJointCount;
    
    /**
     * Measures how many times each category was found in the training dataset.
     */
    public Map<String, Integer> categoryCounts;

    /**
     * Constructor
     */
    public FeatureStats() {
        n = 0;
        featureCategoryJointCount = new HashMap<>();
        categoryCounts = new HashMap<>();
    }
}
