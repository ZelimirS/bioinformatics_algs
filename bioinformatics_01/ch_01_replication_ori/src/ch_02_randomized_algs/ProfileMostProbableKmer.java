package ch_02_randomized_algs;

import ch_01_replication_ori.FileImporter;

import java.io.IOException;

public class ProfileMostProbableKmer {

    public static String profileMostProbableKmer(String textAddress, int k, String profileAddress){
        String kmer = "";
        String text = "";
        double probability = 1.0;
        double kmerProbability = Double.NEGATIVE_INFINITY;
//        double[][] profileMatrix = Motifs.profileAddressToProfileMatrixOneDecimal(profileAddress);
        double[][] profileMatrix = Motifs.profileAddressToProfileMatrixThreeDecimals(profileAddress);
        try{
            text = FileImporter.importFile(textAddress);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        for(int i = 0; i < text.length() - k + 1; i++){
            String pattern = text.substring(i, i + k);
                for(int m = 0; m < pattern.length(); m++){
                    char ch = pattern.charAt(m);
                    switch(ch){
                        case 'A':
                            probability *= profileMatrix[0][m];
                            break;
                        case 'C':
                            probability *= profileMatrix[1][m];
                            break;
                        case 'G':
                            probability *= profileMatrix[2][m];
                            break;
                        case 'T':
                            probability *= profileMatrix[3][m];
                            break;
                    }
                }
                if(kmerProbability < probability){
                    kmerProbability = probability;
                    kmer = pattern;

                }
                probability = 1.0;
        }

        return kmer;
    }

    /**
     * overloaded version that doesn't use addresses but text and double[][] matrix directly
     * @param text
     * @param k
     * @param profileMatrix
     * @return
     */
    public static String profileMostProbableKmer(String text, double[][] profileMatrix, int k){
        String kmer = "";
        double probability = 1.0;
        double kmerProbability = Double.NEGATIVE_INFINITY;
        for(int i = 0; i < text.length() - k + 1; i++){
            String pattern = text.substring(i, i + k);
            for(int m = 0; m < pattern.length(); m++){
                char ch = pattern.charAt(m);
                switch(ch){
                    case 'A':
                        probability *= profileMatrix[0][m];
                        break;
                    case 'C':
                        probability *= profileMatrix[1][m];
                        break;
                    case 'G':
                        probability *= profileMatrix[2][m];
                        break;
                    case 'T':
                        probability *= profileMatrix[3][m];
                        break;
                }
            }
            if(kmerProbability < probability){
                kmerProbability = probability;
                kmer = pattern;

            }
            probability = 1.0;
        }

        return kmer;
    }

    public static void main(String[] args) {
        System.out.println(profileMostProbableKmer("E:\\java_learning\\Bioinformatics\\radni.txt",
                6, "E:\\java_learning\\Bioinformatics\\profile_address.txt"));
    }

}
