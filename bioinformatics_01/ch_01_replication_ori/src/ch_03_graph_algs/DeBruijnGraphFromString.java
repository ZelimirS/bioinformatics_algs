package ch_03_graph_algs;

import ch_01_replication_ori.FileImporter;
import java.io.IOException;
import java.util.*;

import static ch_03_graph_algs.StringCompositionProblem.stringComposition;

public class DeBruijnGraphFromString {

    public static Map<String, List<String>> deBruijnGraphFromString(int k, String textAdress) {
        String text = "";
        Map<String, List<String>> deBruijnGraph = new TreeMap<>();
        List<String> kmers = new ArrayList<>();
        try{
            text = FileImporter.importFile(textAdress);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        kmers = stringComposition(k, textAdress);
        //System.out.println(kmers);
        for(int i = 0; i < kmers.size(); i++) {
            String str1 = kmers.get(i).substring(0, kmers.get(i).length() - 1);
            if(deBruijnGraph.containsKey(str1)){
                deBruijnGraph.get(str1).add(kmers.get(i).substring(1));
            }
            else{
                deBruijnGraph.put(str1, new ArrayList<>());
                deBruijnGraph.get(str1).add(kmers.get(i).substring(1));
            }
        }

        return deBruijnGraph;
    }

    public static void main(String[] args) {
        Map<String, List<String>> result = deBruijnGraphFromString(3,
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
