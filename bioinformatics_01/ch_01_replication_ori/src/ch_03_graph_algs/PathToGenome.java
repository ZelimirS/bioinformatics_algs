package ch_03_graph_algs;

import ch_01_replication_ori.FileImporter;
import java.util.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PathToGenome {

    public static String pathToGenome (String pathAdress) {
        List<String> path = new ArrayList<>();
        try{
            path = FileImporter.importFileLineByline(pathAdress);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        String genome = path.get(0);
        for(int i = 1; i < path.size(); i++){
            String str = path.get(i);
            genome += str.substring(str.length() - 1);
        }
        return genome;
    }

    public static void main(String[] args) {
        String result = pathToGenome(
                "/Users/zelimirstojcevic/IdeaProjects/bioinformatics_algs/bio_files/rosalind_ba3b.txt");
        System.out.println(result);
    }
}
