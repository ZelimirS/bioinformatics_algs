package ch_02_randomized_algs;

import ch_01_replication_ori.FileImporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Motifs {

    public static char[][] motifs(String genomText) {
        ArrayList<String> listOfDna = new ArrayList<>();
        try {
            listOfDna = FileImporter.importFileLineByline(genomText);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int row = listOfDna.size();
        int column = listOfDna.get(0).length();
        char[][] chMotifs = new char[row][column];

        for (int i = 0; i < row; i++) {
            char[] ch = listOfDna.get(i).toCharArray();
            for (int j = 0; j < column; j++) {
                chMotifs[i][j] = ch[j];
            }
        }
        return chMotifs;
    }

    public static char[][] rotatedMotifs(String genomText) {
        ArrayList<String> listOfDna = new ArrayList<>();
        try {
            listOfDna = FileImporter.importFileLineByline(genomText);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int row = listOfDna.size();
        int column = listOfDna.get(0).length();
        char[][] chMotifs = new char[column][row];

        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row; j++) {
                chMotifs[i][j] = listOfDna.get(j).charAt(i);
            }
        }
        return chMotifs;
    }
    public static char[][] rotatedMotifs(char[][] motifsMatrix) {
        int column = motifsMatrix.length;
        int row = motifsMatrix[0].length;
        char[][] rotatedMotifs = new char[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                rotatedMotifs[i][j] = motifsMatrix[j][i];
            }
        }
        return rotatedMotifs;
    }
    public static int[][] rotatedMotifs(int[][] motifsMatrix) {
        int column = motifsMatrix.length;
        int row = motifsMatrix[0].length;
        int[][] rotatedMotifs = new int[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                rotatedMotifs[i][j] = motifsMatrix[j][i];
            }
        }
        return rotatedMotifs;
    }
    public static double[][] rotatedMotifs(double[][] motifsMatrix) {
        int column = motifsMatrix.length;
        int row = motifsMatrix[0].length;
        double[][] rotatedMotifs = new double[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                rotatedMotifs[i][j] = motifsMatrix[j][i];
            }
        }
        return rotatedMotifs;
    }

    public static void printMotifMatrix(char[][] motifMatrix){
        for(char[] charArr : motifMatrix){
            for(char ch : charArr){
                System.out.print(ch + "  ");
            }
            System.out.println();
        }
    }
    public static void printMotifMatrix(int[][] motifMatrix){
            for (int[] intArr : motifMatrix) {
                for (int in : intArr) {
                    System.out.format("%3d", in);
                }
                System.out.println();
            }
    }
    public static void printMotifMatrix(double[][] motifMatrix){
        for(double[] doubleArr : motifMatrix){
            for(double d : doubleArr){
                System.out.print(d + "  ");
            }
            System.out.println();
        }
    }

    public static double[][] profileAddressToProfileMatrix(String profileAddress){
        ArrayList<String> profileStrings = new ArrayList<>();
        try{
            profileStrings.addAll(FileImporter.importFileLineByline(profileAddress));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        double[][] profileMatrix = new double[4][profileStrings.get(0).length()/4+1];
        for(int i = 0; i < 4; i++){
            String profileRow = profileStrings.get(i).replaceAll("\\s", "");
            ArrayList<Double> tempList = new ArrayList<>();
            for(int j = 0; j < profileRow.length(); j += 3){
                Double d = Double.valueOf(profileRow.substring(j, j+3));
                tempList.add(d);
            }
            for(int m = 0; m < tempList.size(); m++){
                profileMatrix[i][m] = tempList.get(m);
            }
            tempList.clear();
        }

        return profileMatrix;
    }
    public static double[][] profileAddressToProfileMatrixThreeDecimals(String profileAddress){
        ArrayList<String> profileStrings = new ArrayList<>();
        try{
            profileStrings.addAll(FileImporter.importFileLineByline(profileAddress));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        double[][] profileMatrix = new double[4][profileStrings.get(0).length()/7+1];
        for(int i = 0; i < 4; i++){
            String profileRow = profileStrings.get(i).replaceAll("\\s", "");
            ArrayList<Double> tempList = new ArrayList<>();
            for(int j = 0; j < profileRow.length(); j += 5){
                Double d = Double.valueOf(profileRow.substring(j, j+5));
                tempList.add(d);
            }
            for(int m = 0; m < tempList.size(); m++){
                profileMatrix[i][m] = tempList.get(m);
            }
            tempList.clear();
        }

        return profileMatrix;
    }

    public static int[][] countMotifs(char[][] motifsMatrix){
        char[][] rotatedMotifsMatrix = rotatedMotifs(motifsMatrix);
        int[][] acgtCounts = new int[rotatedMotifsMatrix.length][4];
        int[][] acgtCountsRotated = new int[4][rotatedMotifsMatrix.length];
        int numA = 0, numC = 0, numG = 0, numT = 0;

        for(int i = 0; i < rotatedMotifsMatrix.length; i++){
            char[] chArr = rotatedMotifsMatrix[i];
            for(int j = 0; j < rotatedMotifsMatrix[0].length; j++){
                char ch = chArr[j];
                switch (ch){
                    case 'A':
                        numA += 1;
                        break;
                    case 'C':
                        numC += 1;
                        break;
                    case 'G':
                        numG += 1;
                        break;
                    case 'T':
                        numT += 1;
                        break;
                }
            }
            acgtCounts[i][0] = numA;
            acgtCounts[i][1] = numC;
            acgtCounts[i][2] = numG;
            acgtCounts[i][3] = numT;
            numA = 0; numC = 0; numG = 0; numT = 0;
        }
        acgtCountsRotated = rotatedMotifs(acgtCounts);

        return acgtCountsRotated;
    }

    public static int scoreMotifs(char[][] motifsMatrix){
        int[][] countedMotifs = countMotifs(motifsMatrix);
        int[][] rotatedCountedMotifs = rotatedMotifs(countedMotifs);
        int sum = 0;
        for(int[] in : rotatedCountedMotifs){
            Arrays.sort(in);
            for(int i = 0; i < 3; i++){
                sum += in[i];
            }
        }
        return sum;
    }

    public static double[][] profileMotifs(char[][] motifsMatrix){
        char[][] rotatedMotifsMatrix = rotatedMotifs(motifsMatrix);
        double[][] acgtCounts = new double[rotatedMotifsMatrix.length][4];
        double[][] acgtCountsRotated = new double[4][rotatedMotifsMatrix.length];
        int numA = 0, numC = 0, numG = 0, numT = 0;

        for(int i = 0; i < rotatedMotifsMatrix.length; i++){
            char[] chArr = rotatedMotifsMatrix[i];
            for(int j = 0; j < rotatedMotifsMatrix[0].length; j++){
                char ch = chArr[j];
                switch (ch){
                    case 'A':
                        numA += 1;
                        break;
                    case 'C':
                        numC += 1;
                        break;
                    case 'G':
                        numG += 1;
                        break;
                    case 'T':
                        numT += 1;
                        break;
                }
            }
            double d = motifsMatrix.length * 1.0;
            acgtCounts[i][0] = numA / d;
            acgtCounts[i][1] = numC / d;
            acgtCounts[i][2] = numG / d;
            acgtCounts[i][3] = numT / d;
            numA = 0; numC = 0; numG = 0; numT = 0;
        }
        acgtCountsRotated = rotatedMotifs(acgtCounts);

        return acgtCountsRotated;
    }

    public static ArrayList<String> consensusList(char[][] motifsMatrix){
        ArrayList<String> consensusList = new ArrayList<>();
        double[][] profileMatrix = profileMotifs(motifsMatrix);
        double[][] profileMatrixRotated = rotatedMotifs(profileMatrix);
        for(double[] d : profileMatrixRotated){
            double max = -1.0;
            int index = 0;
            for(int i = 0; i < 4; i++){
                if(d[i] > max) {
                    max = d[i];
                    index = i;
                }
            }
            if(index == 0)
                consensusList.add("A");
            else if(index == 1)
                consensusList.add("C");
            else if(index == 2)
                consensusList.add("G");
            else
                consensusList.add("T");
        }
        return consensusList;
    }

    public static double entropyScore(char[][] motifsMatrix){
        double entropyScore = 0.0;
        double[][] profileMatrix = profileMotifs(motifsMatrix);
        double[][] profileMatrixRotated = rotatedMotifs(profileMatrix);
        for(double[] column : profileMatrixRotated){
            for(double d : column){
                if(d == 0.0) entropyScore += 0.0;
                else
                    entropyScore += -(d * (Math.log10(d) / Math.log10(2.0)));
            }
        }
        return entropyScore;
    }

    public static void main(String[] args) {
        char[][] mtfs = motifs("E:\\java_learning\\Bioinformatics\\motifTest.txt");
        System.out.println("MOTIF MATRIX:");
        printMotifMatrix(mtfs);
        System.out.println();
        System.out.println("COUNT:");
        printMotifMatrix(countMotifs(mtfs));
        System.out.println();
        System.out.println("SCORE: " + scoreMotifs(mtfs));
        System.out.println();
        System.out.println("PROFILE:");
        printMotifMatrix(profileMotifs(mtfs));
        System.out.println();
        System.out.println("CONSENSUS: ");
        for(String s : consensusList(mtfs)){
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println();
        System.out.print("ENTROPY SCORE: ");
        System.out.print(entropyScore(mtfs));


        System.out.println("\n\n\n");

        printMotifMatrix(profileAddressToProfileMatrix("E:\\java_learning\\Bioinformatics\\profile_address.txt"));


    }



}
