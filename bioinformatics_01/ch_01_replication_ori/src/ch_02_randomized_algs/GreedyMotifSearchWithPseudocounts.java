package ch_02_randomized_algs;

import ch_01_replication_ori.FileImporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class GreedyMotifSearchWithPseudocounts {

    public static ArrayList<String> greedyMotifSearch(String dnaStringsCollectionAddress, int k, int t){
        ArrayList<String> bestMotifsList = new ArrayList<>();
        char[][] bestMotifsArr = new char[t][k];
        char[][] bestMotifsArrCopy = new char[t][k];
        ArrayList<String> listOfDnaStrings = new ArrayList<>();
        try{
            listOfDnaStrings = FileImporter.importFileLineByline(dnaStringsCollectionAddress);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        for(int i = 0; i< listOfDnaStrings.size(); i++){
            String str = listOfDnaStrings.get(i);
            bestMotifsArr[i] = str.substring(0, k).toCharArray();
        }

        String dnaFirst = listOfDnaStrings.get(0);
        for(int i = 0; i< dnaFirst.length() - k + 1; i++){
            char[] kmer = dnaFirst.substring(i, i + k).toCharArray();
            bestMotifsArrCopy[0] = kmer;
            for(int j = 1; j < t; j++){
                double[][] profileMatrixBestMotifs = Motifs.profileMotifsLaplace(bestMotifsArrCopy);
                bestMotifsArrCopy[j] = ProfileMostProbableKmer.profileMostProbableKmer(listOfDnaStrings.get(j), profileMatrixBestMotifs, k).toCharArray();
            }
            if(Motifs.scoreMotifs(bestMotifsArrCopy) < Motifs.scoreMotifs(bestMotifsArr)){
                bestMotifsArr = bestMotifsArrCopy;
            }
            bestMotifsArrCopy = new char[t][k];
        }
        for(char[] ch : bestMotifsArr){
            bestMotifsList.add(new String(ch));
        }

        return bestMotifsList;
    }

    public static void main(String[] args) {
        ArrayList<String> hs = greedyMotifSearch("E:\\java_learning\\Bioinformatics\\rosalind_ba2e.txt", 12, 25);
        for(String str : hs)
            System.out.println(str);

    }
}