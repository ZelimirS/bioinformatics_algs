package ch_03_graph_algs;

import ch_01_replication_ori.FileImporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringCompositionProblem {

    public static List<String> composition = new ArrayList<>();

    /**
     *
     * @param k
     * @param textAddress
     * @return list of (composition) kmers of the text
     */
    public static List<String> stringComposition(int k, String textAddress) {
        String text = "";
        try{
            text = FileImporter.importFile(textAddress);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        for(int i = 0; i < text.length() - k + 1; i++){
            String str = text.substring(i, i + k);
            composition.add(str);
        }
        //composition = composition.stream().sorted().collect(Collectors.toList());
        return composition;
    }

    public static void main(String[] args) {
        stringComposition(3,
                "/Users/zelimirstojcevic/IdeaProjects/bioinformatics_algs/bio_files/radni.txt")
                .stream()
                .forEach(System.out::println);

    }

}