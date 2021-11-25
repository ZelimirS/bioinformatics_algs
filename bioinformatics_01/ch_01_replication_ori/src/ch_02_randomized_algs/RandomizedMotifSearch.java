package ch_02_randomized_algs;

import ch_01_replication_ori.FileImporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RandomizedMotifSearch {

    public static ArrayList<String> randomizedMotifSearch(String dnaAddress, int k, int t){
        ArrayList<String> bestMotifsList = new ArrayList<>();
        ArrayList<String> listOfDna = new ArrayList<>();
        char[][] motifs = new char[t][k];
        char[][] bestMotifs = new char[t][k];
        try{
            listOfDna = FileImporter.importFileLineByline(dnaAddress);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        Random rand = new Random();
        for (int i = 0; i < t; i++){
            int index = rand.nextInt(listOfDna.get(i).length() - k + 1);
            String str = listOfDna.get(i).substring(index, index + k);
            motifs[i] = str.toCharArray();
        }
        bestMotifs = Arrays.copyOf(motifs, motifs.length);
        while(true){
            double[][] profile = Motifs.profileMotifsLaplace(motifs);
            motifs = motifs(profile, listOfDna, k);
            if(Motifs.scoreMotifs(motifs) < Motifs.scoreMotifs(bestMotifs)){
                bestMotifs = Arrays.copyOf(motifs, motifs.length);
            }
            else {
                for(char[] ch : motifs){
                    bestMotifsList.add(new String(ch));
                }
                return bestMotifsList;
            }
        }
    }

    public static char[][] motifs(double[][] profile, ArrayList<String> dnaLst, int k){
        char[][] pmpKmerArr = new char[dnaLst.size()][k];
        for (int i = 0; i < dnaLst.size(); i++){
            String text = dnaLst.get(i);
            pmpKmerArr[i] = ProfileMostProbableKmer.profileMostProbableKmer(text, profile, k).toCharArray();
        }
        return pmpKmerArr;
    }

    public static ArrayList<String> runRandomizedMotifsearchNTimes(String dnaAddress, int k, int t, int times){
        ArrayList<String> bestMotifsList = new ArrayList<>();
        ArrayList<String> listOfDna = new ArrayList<>();
        char[][] mtfs = new char[t][k];
        char[][] bestMtfs = new char[t][k];
        int bestScore = Integer.MAX_VALUE;

        /////////////////////////////////////////////////////////////////
        /*ArrayList<String> list = new ArrayList<>();
        list = randomizedMotifSearch(dnaAddress, k, t);
        for(int j = 0; j <  t; j++){
            String str = list.get(j);
            mtfs[j] = str.toCharArray();
        }*/
        /////////////////////////////////////////////////////////////////

        for(int i = 0; i < times; i++){

            bestMotifsList = randomizedMotifSearch(dnaAddress, k, t); //////////////////////////////////////////////////
            for(int j = 0; j <  t; j++){
                String str = bestMotifsList.get(j);
                bestMtfs[j] = str.toCharArray();
            }
            int score = Motifs.scoreMotifs(bestMtfs);
            if(score < bestScore){
                mtfs = Arrays.copyOf(bestMtfs, t);
                bestScore = score;
            }
        }
        bestMotifsList.clear();
        for(char[] c : mtfs)
            bestMotifsList.add(new String(c));

        return bestMotifsList;
    }

    public static void main(String[] args) {
        ArrayList<String> result = new ArrayList<>();
        result = runRandomizedMotifsearchNTimes("D:\\java_learning\\Bioinformatics\\motifTest.txt", 8, 5, 1000);
        for(String str : result)
            System.out.println(str);
    }


}
