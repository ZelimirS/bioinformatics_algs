package ch_02_randomized_algs;

import ch_01_replication_ori.FileImporter;
import ch_01_replication_ori.HammingDistance;
import ch_01_replication_ori.Ori;

import java.io.IOException;
import java.util.ArrayList;

public class MedianString {

    public static int distanceBetweenPatternAndString(String pattern, String dnaStrings){
        int distance = 0;
        int k = pattern.length();
        ArrayList<String> listOfDnaStrings = new ArrayList<>();
        try {
            listOfDnaStrings = FileImporter.importFileLineByline(dnaStrings);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String str : listOfDnaStrings){
            int hammingDistance = (int) Double.POSITIVE_INFINITY;
            for(int i = 0; i < str.length() - k + 1; i++){
                String patt = str.substring(i, i + k);
                int hammingD = HammingDistance.hammingDistance(pattern, patt);
                if(hammingDistance > hammingD){
                    hammingDistance = hammingD;
                }
            }
            distance += hammingDistance;
        }
        return distance;
    }
    /**
     * DISTANCEBETWEENPATTERNANDSTRINGS(Pattern, Dna)
     * k  <---  |Pattern|
     * distance  <---  0
     * for each string Text in Dna
     *      HammingDistance <--- inf
     *      for each k-mer Pattern’ in Text
                    if HammingDistance > HAMMINGDISTANCE(Pattern, Pattern’)
     *                  HammingDistance <--- HAMMINGDISTANCE(Pattern, Pattern’)
     *      distance <--- distance + HammingDistance
     * return distance
     */

    public static String medianString(String dnaStrings, int k){
        String median = "";
        int distance = (int) Double.POSITIVE_INFINITY;
        for(int i = 0; i < (int)Math.pow(4.0, k) - 1; i++){
            String pattern = Ori.numberToPattern(i, k);
            int dist = distanceBetweenPatternAndString(pattern, dnaStrings);
            if(distance > dist){
                distance = dist;
                median = pattern;
            }
        }
        return median;
    }

    public static void main(String[] args) {
        System.out.println(medianString("E:\\java_learning\\Bioinformatics\\rosalind_ba2b.txt", 6));
    }
}

/**
 MEDIANSTRING(Dna, k)
 distance <--- 1
 for i 0 to 4k - 1
     Pattern <--- NUMBERTOPATTERN(i, k)
    if distance > DISTANCEBETWEENPATTERNANDSTRINGS(Pattern, Dna)
        distance <--- DISTANCEBETWEENPATTERNANDSTRINGS(Pattern, Dna)
        Median <--- Pattern
 return Median
 */
