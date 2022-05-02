package ch_03_graph_algs;

import ch_01_replication_ori.FileImporter;

import java.io.IOException;
import java.util.*;

public class DeBruijnGraphFromKmers {

    public static Map<String, List<String>> deBruijnGraphFromKmers(String patternsAdress){
        Map<String, List<String>> deBruijnMap = new TreeMap<>();
        List<String> patterns = new ArrayList<>();
        Set<String> deBruijnSet = new HashSet<>();
        try{
            patterns = FileImporter.importFileLineByline(patternsAdress);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        for(int i = 0; i < patterns.size(); i++){
            int k = patterns.get(i).length();
            String str1 = patterns.get(i).substring(0, k-1);
            String str2 = patterns.get(i).substring(1);
            deBruijnSet.add(str1);
            deBruijnSet.add(str2);
        }
        for (String kmer : patterns) {
            String str1 = kmer.substring(0, kmer.length()-1);
            String str2 = kmer.substring(1);
            if(deBruijnMap.keySet().contains(str1)){
                deBruijnMap.get(str1).add(str2);
            }
            else{
                deBruijnMap.put(str1, new ArrayList<>());
                deBruijnMap.get(str1).add(str2);
            }
        }
        return deBruijnMap;
    }

    public static Map<String, List<String>> deBruijnGraphFromKmers(List<String> lst){
        Map<String, List<String>> deBruijnMap = new TreeMap<>();
        List<String> patterns = new ArrayList<>();
        Set<String> deBruijnSet = new HashSet<>();
        patterns = lst;
        for(int i = 0; i < patterns.size(); i++){
            int k = patterns.get(i).length();
            String str1 = patterns.get(i).substring(0, k-1);
            String str2 = patterns.get(i).substring(1);
            deBruijnSet.add(str1);
            deBruijnSet.add(str2);
        }
        for (String kmer : patterns) {
            String str1 = kmer.substring(0, kmer.length()-1);
            String str2 = kmer.substring(1);
            if(deBruijnMap.keySet().contains(str1)){
                deBruijnMap.get(str1).add(str2);
            }
            else{
                deBruijnMap.put(str1, new ArrayList<>());
                deBruijnMap.get(str1).add(str2);
            }
        }
        return deBruijnMap;
    }

    public static void main(String[] args) {
        Map<String, List<String>> result = deBruijnGraphFromKmers(
                "/Users/zelimirstojcevic/IdeaProjects/bioinformatics_algs/bio_files/radni.txt");

        for(Map.Entry<String, List<String>> ent : result.entrySet()){
            System.out.print(ent.getKey() + " -> ");
            List<String> lstStr = ent.getValue();
            for(int i = 0; i < lstStr.size(); i++){
                if(i < lstStr.size()-1){
                    System.out.print(lstStr.get(i) + ", ");
                }
                else {
                    System.out.print(lstStr.get(i));
                }
            }
            System.out.println();
        }
    }

}
