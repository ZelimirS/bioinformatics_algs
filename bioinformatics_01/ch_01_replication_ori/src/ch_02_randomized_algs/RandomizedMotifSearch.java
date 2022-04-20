package ch_02_randomized_algs;

import ch_01_replication_ori.FileImporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class RandomizedMotifSearch {

    public static char[][] randomizedMotifSearch(char[][] motifs, int k, int t) {
        ArrayList<String> bestMotifsList = new ArrayList<>();
        //ArrayList<String> listOfDnaStrings = new ArrayList<>();
        //char[][] motifs = new char[t][k];
        char[][] bestMotifs = new char[t][k];
        /*try {
            listOfDnaStrings = FileImporter.importFileLineByline(dnaAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        /*Random rand = new Random();
        for (int i = 0; i < t; i++) {
            int index = rand.nextInt(motifs[i].length - k + 1);
            String str = new String(motifs[i]);
            motifs[i] = str.substring(index, index + k).toCharArray();
        }*/
        bestMotifs = motifs;
        while (true) {
            double[][] profile = Motifs.profileMotifsLaplace(motifs);
            motifs = motifs(profile, "/Users/zelimirstojcevic/IdeaProjects/bioinformatics_algs/bio_files/motif_test.txt", k);  // motifs iz motif_test -- sve
            if (Motifs.scoreMotifs(motifs) < Motifs.scoreMotifs(bestMotifs)) {
                bestMotifs = motifs;
            } else {
                //for (char[] chArr : bestMotifs) {
                  //  bestMotifsList.add(new String(chArr));
                //}
                //return bestMotifsList;
                return bestMotifs;
            }
        }
    }

    private static char[][] motifs(double [][] profiles, String dnaAddress, int k){
        ArrayList<String> listOfDnaStrings = new ArrayList<>();
        try {
            listOfDnaStrings = FileImporter.importFileLineByline(dnaAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
        char[][] pmpKmerArr = new char[listOfDnaStrings.size()][k];
        for(int i = 0; i < listOfDnaStrings.size(); i++){
            pmpKmerArr[i] = ProfileMostProbableKmer.profileMostProbableKmer(listOfDnaStrings.get(i), profiles, k).toCharArray();
        }
        return pmpKmerArr;
    }

    public static ArrayList<String> runRandomizedMotifSearchNTimes(String dnaAddress, int k, int t){
        ArrayList<String> bestMotifsList = new ArrayList<>();
        ArrayList<String> listOfDnaStrings = new ArrayList<>();
        char[][] motifs = new char[t][k];
        char[][] bestMotifs = new char[t][k];
        try {
            listOfDnaStrings = FileImporter.importFileLineByline(dnaAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Random rand = new Random();
        for (int i = 0; i < listOfDnaStrings.size(); i++) {
            int index = rand.nextInt(listOfDnaStrings.get(i).length() - k + 1);
            String str = new String(listOfDnaStrings.get(i));
            motifs[i] = str.substring(index, index + k).toCharArray();
        }
        bestMotifs = motifs;
        for (int i = 0; i < 1000; i++){
            motifs = randomizedMotifSearch(motifs, k, listOfDnaStrings.size());
            if(Motifs.scoreMotifs(bestMotifs) < Motifs.scoreMotifs(motifs)){
                bestMotifs = motifs;
            }
        }
        for(char[] ch : bestMotifs){
            bestMotifsList.add(new String(ch));
        }
        return bestMotifsList;
    }

    public static void main(String[] args) {
        ArrayList<String> result = new ArrayList<>();
        result = runRandomizedMotifSearchNTimes("/Users/zelimirstojcevic/IdeaProjects/bioinformatics_algs/bio_files/motif_test.txt", 8, 5);
        for (String str : result) {
            System.out.println(str);
        }
    }

}

/**
 * RANDOMIZEDMOTIFSEARCH(Dna, k, t)
 *         randomly select k-mers Motifs = (Motif1, …, Motift) in each string
 *             from Dna
 *         BestMotifs ← Motifs
 *         while forever
 *             Profile ← Profile(Motifs)
 *             Motifs ← Motifs(Profile, Dna)
 *             if Score(Motifs) < Score(BestMotifs)
 *                 BestMotifs ← Motifs
 *             else
 *                 return BestMotifs
 */