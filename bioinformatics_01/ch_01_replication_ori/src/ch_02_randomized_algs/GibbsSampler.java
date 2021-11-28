package ch_02_randomized_algs;

import ch_01_replication_ori.FileImporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GibbsSampler {

    public static ArrayList<String> gibbsSampler(String dnaAddress, int k, int t, int times){
        ArrayList<String> bestMotifsList = new ArrayList<>();
        ArrayList<String> listOfDna = new ArrayList<>();
        char[][] motifs = new char[t][k];
        char[][] bestMotifs = new char[t][k];
        int bestScore = Integer.MAX_VALUE;
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
        for(int i = 0; i < motifs.length; i++){
            for(int j = 0; j < motifs[i].length; j++){
                bestMotifs[i][j] = motifs[i][j];
            }
        }
        for(int i = 0; i < times; i++) {
            ArrayList<String> tempList = twoDArrayToList(motifs);
            int index = rand.nextInt(t);
            tempList.remove(index);
            char[][] tempMotifs = listToTwoDArray(tempList);
            double[][] profile = Motifs.profileMotifsLaplace(tempMotifs);
            char[] chArr = profileRandomlyGeneratedKmer(listOfDna.get(index), profile, k);
            tempList.add(index, new String(chArr));
            motifs = listToTwoDArray(tempList);
            int score = Motifs.scoreMotifs(motifs);
            if(score < bestScore){
                bestScore = score;
                for(int m = 0; m < motifs.length; m++){
                    for(int j = 0; j < motifs[m].length; j++){
                        bestMotifs[m][j] = motifs[m][j];
                    }
                }
                //Motifs.printMotifMatrix(bestMotifs);////////////////////////////////////////////////////////////////////
                //System.out.println(bestScore + "   ******************");////////////////////////////////////////////////
            }
        }
        for(char[] ch : bestMotifs){
            bestMotifsList.add(new String(ch));
        }
        return bestMotifsList;
    }

    public static char[] profileRandomlyGeneratedKmer(String text, double[][] profile, int k){
        double probability = 0.0;
        String bestKmer = "";
        char[] kMerArr = new char[k];
        double bestProbability = Double.MIN_VALUE;

        double[] probabilities = new double[text.length()-k+1];
        ArrayList<String> kmers = new ArrayList<>();
        double probabilitySum = 0.0;
        double currentSum = 0.0;

        for (int i = 0; i < text.length() - k + 1; i++){
            String kMer = text.substring(i, i+k);
            kmers.add(kMer);
            probability = probability(kMer, profile);
            probabilities[i] = probability;
        }
        probabilitySum = Arrays.stream(probabilities).sum();
        double randomPoint = new Random().nextDouble() * probabilitySum;
        out:
        for(int j = 0; j < probabilities.length; j++){
            currentSum += probabilities[j];
            if(currentSum >= randomPoint) {
                bestKmer = kmers.get(j);
                break out;
            }
        }
        return bestKmer.toCharArray();
    }
    /*
    def most_probable_gibbs_kmer(profile, dna_sequence, k):
    kmers = []
    probabilities = []

    for i in range(len(dna_sequence) - k + 1):
        kmer = dna_sequence[i:i + k]
        kmers.append(kmer)

        kmer_probability = probability(profile, kmer)
        probabilities.append(kmer_probability)


    probability_sum = sum(probabilities)
    random_point = random.random() * probability_sum

    current_sum = 0
    for i in range(len(probabilities)):
        p = probabilities[i]

        current_sum += p

        if current_sum >= random_point:
            return kmers[i]
     */

    private static double probability(String str, double[][] profile) {
        double probability = 1.0;
        for(int i = 0; i < str.length(); i++){
            char ch = str.charAt(i);
            switch(ch){
                case 'A':
                    probability *= profile[0][i];
                    break;
                case 'C':
                    probability *= profile[1][i];
                    break;
                case 'G':
                    probability *= profile[2][i];
                    break;
                case 'T':
                    probability *= profile[3][i];
                    break;
            }
        }
        return probability;
    }

    private static ArrayList<String> twoDArrayToList(char[][] charArr){
        ArrayList<String> list = new ArrayList<>();
        for(char[] ch : charArr){
            list.add(new String(ch));
        }
        return list;
    }
    private static char[][] listToTwoDArray(ArrayList<String> list){
        char[][] charArr = new char[list.size()][list.get(0).length()];
        for(int i = 0; i < list.size(); i++){
            charArr[i] = list.get(i).toCharArray();
        }
        return charArr;
    }

    public static ArrayList<String> runGibbsSampler(String dnaAddress, int k, int t, int times, int N){
        ArrayList<String> bestMotifsList = new ArrayList<>();
        ArrayList<String> listMotifs = new ArrayList<>();
        char[][] motifs = new char[t][k];
        char[][] bestMotifs = new char[t][k];
        int bestScore = Integer.MAX_VALUE;
        for(int i = 0; i < N; i++){
            listMotifs = gibbsSampler(dnaAddress, k, t, times);
            for(int j = 0; j < listMotifs.size(); j++){
                motifs[j] = listMotifs.get(j).toCharArray();
            }
            int score = Motifs.scoreMotifs(motifs);
            if(score < bestScore){
                for(int o = 0; o < motifs.length; o++){
                    for(int p =  0; p < motifs[o].length; p++){
                        bestMotifs[o][p] = motifs[o][p];
                    }
                }
                bestScore = score;

            }
        }
        listMotifs.clear();
        for(char[] c : bestMotifs)
            bestMotifsList.add(new String(c));

        return bestMotifsList;
    }

    public static void main(String[] args) {
        ArrayList<String> result = new ArrayList<>();
        result = runGibbsSampler("D:\\java_learning\\Bioinformatics\\rosalind_ba2g.txt", 15, 20, 2000, 100);
        for(String str : result){
            System.out.println(str);
        }
    }
}