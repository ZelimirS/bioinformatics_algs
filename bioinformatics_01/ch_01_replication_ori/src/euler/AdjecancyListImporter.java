package euler;

import ch_01_replication_ori.FileImporter;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class AdjecancyListImporter {

    public static Map<Integer, List<Integer>> importAdjecancy(String adress) { // uskladi imena metoda
        Map<Integer, List<Integer>> adj = new HashMap<>();
        List<String> listFromFile = new LinkedList<>();
        try {
            listFromFile = FileImporter.importFileLineByline(adress);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(listFromFile);

        for(int i = 0; i < listFromFile.size(); i++){

            String str = listFromFile.get(i);
            int index = str.indexOf(">") + 2;
            String str1 = listFromFile.get(i).substring(0, listFromFile.get(i).indexOf(" "));
            int keyValue = Integer.valueOf(str1 + "");
            adj.put(keyValue, new LinkedList<>());
            String str2 = listFromFile.get(i).substring(index);
            String[] strArr = str2.split(",");
            for(String s : strArr){
                adj.get(keyValue).add(Integer.valueOf(s + ""));
            }
            Collections.sort(adj.get(keyValue));
        }


        return adj;
    }

    public static Map<BigInteger, List<BigInteger>> importAdjecancyBigInteger(String adress) { // uskladi imena metoda
        Map<BigInteger, List<BigInteger>> adj = new HashMap<>();
        List<String> listFromFile = new LinkedList<>();
        try {
            listFromFile = FileImporter.importFileLineByline(adress);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(listFromFile);

        for(int i = 0; i < listFromFile.size(); i++){

            String str = listFromFile.get(i);
            int index = str.indexOf(">") + 2;
            String str1 = listFromFile.get(i).substring(0, listFromFile.get(i).indexOf(" "));
            BigInteger keyValue = new BigInteger(str1);
            adj.put(keyValue, new LinkedList<>());
            String str2 = listFromFile.get(i).substring(index);
            String[] strArr = str2.split(",");
            for(String s : strArr){
                adj.get(keyValue).add(new BigInteger(s + ""));
            }
            Collections.sort(adj.get(keyValue));
        }


        return adj;
    }

    public static void main(String[] args) {
        Map<Integer, List<Integer>> adj = new HashMap<>();
        adj = AdjecancyListImporter.importAdjecancy("/Users/zelimirstojcevic/" +
                "IdeaProjects/bioinformatics_algs/bio_files/rosalind_ba3g.txt"); // rosalind_ba3f
        for (Map.Entry<Integer, List<Integer>> ent : adj.entrySet()) {
                System.out.println(ent.getKey() + " -> " + ent.getValue());
        }

    }

}
