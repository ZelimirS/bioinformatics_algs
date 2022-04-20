package ch_03_graph_algs;

import ch_01_replication_ori.FileImporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class overlapGraph {

    public static List<String> overlapGraph(String kmersAdress) {
        List<String> kmers = new ArrayList<>();
        List<String> overlapList = new ArrayList<>();
        try{
            kmers = FileImporter.importFileLineByline(kmersAdress);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        for(int i = 0; i < kmers.size(); i++) {
            String str1 = kmers.get(i);
            for(int j = 0; j < kmers.size(); j++) {
                String str2 = kmers.get(j);
                if(str1.substring(1).equals(str2.substring(0, str2.length()-1))){
                    overlapList.add(str1 + " -> " + str2);
                }
            }
        }
        return overlapList;
    }

    public static void main(String[] args) {
        List<String> result = overlapGraph(
                "/Users/zelimirstojcevic/IdeaProjects/bioinformatics_algs/bio_files/rosalind_ba3c.txt°");
        result.stream()
                .sorted()
                .forEach(System.out::println);
    }

}
