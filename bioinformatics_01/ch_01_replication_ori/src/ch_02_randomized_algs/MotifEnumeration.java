package ch_02_randomized_algs;

import ch_01_replication_ori.FileImporter;
import ch_01_replication_ori.FrequentWordsWithMismatches;
import ch_01_replication_ori.Neighbors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class MotifEnumeration {

    /**
     * brute force alg
     * @param dnaAddress
     * @param patternLength
     * @param mismatches
     * @return
     */
    public static HashSet<String> motifEnumeration(String dnaAddress, int patternLength, int mismatches){
        HashSet<String> patterns = new HashSet<>();
        HashSet<String> neighbors = new HashSet<>();
        HashSet<String> neighborsTwo = new HashSet<>();
        ArrayList<String> listOfDnaStrings = new ArrayList<>();
        try{
            listOfDnaStrings = FileImporter.importFileLineByline(dnaAddress);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        String firstDnaString = listOfDnaStrings.get(0);
        for (int i = 0; i < firstDnaString.length() - patternLength + 1; i++) {
            String pattern = firstDnaString.substring(i, i + patternLength);
            neighbors.addAll(Neighbors.neighbors(pattern, mismatches));
        }
        listOfDnaStrings.remove(0);

        outer:
        for (String neighbor : neighbors) {
            String patt = "";
            for (String dnaStr : listOfDnaStrings) {
                neighborsTwo.clear();
                for(int j = 0; j < dnaStr.length() - patternLength + 1; j++) {
                    patt = dnaStr.substring(j, j + patternLength);
                    neighborsTwo.addAll(Neighbors.neighbors(patt, mismatches));
                }
                if(!neighborsTwo.contains(neighbor))
                    continue  outer;
            }
            patterns.add(neighbor);
            neighborsTwo.clear();
        }

        return patterns;
    }

    public static void main(String[] args) {
        HashSet<String> result = motifEnumeration("E:\\java_learning\\Bioinformatics\\radni.txt", 5, 2);
        ArrayList<String> res = new ArrayList<>(result);
        Collections.sort(res);
        for(String str : res){
            System.out.print(str + " ");
        }
    }

}
