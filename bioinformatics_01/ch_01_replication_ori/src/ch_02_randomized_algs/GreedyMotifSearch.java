package ch_02_randomized_algs;

import ch_01_replication_ori.FileImporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class GreedyMotifSearch {

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
                double[][] profileMatrixBestMotifs = Motifs.profileMotifs(bestMotifsArrCopy); // kmer1 ... kmerj-1 ??????????????
                // sljedeca metoda je overloaded verzija koja ne koristi adrese nego gotove Stringove i char[][]
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
        ArrayList<String> hs = greedyMotifSearch("E:\\java_learning\\Bioinformatics\\rosalind_ba2d.txt", 12, 25);
        for(String str : hs)
            System.out.println(str);

    }



}


/*
   GREEDYMOTIFSEARCH(Dna, k, t)
        BestMotifs ← motif matrix formed by first k-mers in each string
                      from Dna
        for each k-mer Motif in the first string from Dna
            Motif1 ← Motif
            for i = 2 to t
                form Profile from motifs Motif1, …, Motifi - 1
                Motifi ← Profile-most probable k-mer in the i-th string
                          in Dna
            Motifs ← (Motif1, …, Motift)
            if Score(Motifs) < Score(BestMotifs)
                BestMotifs ← Motifs
        return BestMotifs
 */