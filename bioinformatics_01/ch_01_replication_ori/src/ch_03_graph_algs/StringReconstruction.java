package ch_03_graph_algs;

import ch_01_replication_ori.Ori;
import euler.EulerPath;
import euler.EulerPathBigInteger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class StringReconstruction {

    public static String stringReconstruction(int k, String patternsAddress){
        Map<String, List<String>> adjMap = new HashMap<>();
        Map<BigInteger, List<BigInteger>> adjNumberMap = new TreeMap<>();

        adjMap = DeBruijnGraphFromKmers.deBruijnGraphFromKmers(patternsAddress);
        for(Map.Entry <String, List<String>> ent : adjMap.entrySet()){
            BigInteger key = patternToNumber(ent.getKey());
            adjNumberMap.put(key, new LinkedList<>());
            for(String str : ent.getValue()){
                BigInteger val = patternToNumber(str);
                adjNumberMap.get(key).add(val);
            }
        }

        EulerPathBigInteger.printEulerianPath(adjNumberMap);
        Path fileName = Path.of("/Users/zelimirstojcevic/" +
                "IdeaProjects/bioinformatics_algs/bio_files/result_file.txt");
        String res = "";
        try {
            res = Files.readString(fileName);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        List<String> resListStr = Arrays.asList(res.split("->"));
        List<String> resList = new ArrayList<>();
        for(int i = 0; i < resListStr.size(); i++){
            BigInteger inr = new BigInteger(resListStr.get(i));
            String str = numberToPattern(inr, k-1);
            resList.add(str);
        }
        String result = resList.get(0);
        for(int i = 1; i < resList.size(); i++){
            result += resList.get(i).substring(resList.get(i).length()-1);
        }
        return result;
    }

    public static void main(String[] args) {
        String result = stringReconstruction(25, "/Users/zelimirstojcevic/" +
                "IdeaProjects/bioinformatics_algs/bio_files/rosalind_ba3h-3.txt"); // rosalind_ba3h/25
        System.out.println(result);

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/zelimirstojcevic/" +
                    "IdeaProjects/bioinformatics_algs/bio_files/result_file_1.txt"));
            writer.write(result);

            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }


    }

    public static int symbolToNumber(String symbol) {
        switch(symbol) {
            case "A":
                return 0;
            case "C":
                return 1;
            case "G":
                return 2;
        }
        // T
        return 3;
    }

    public static String numberToSymbol(BigInteger number) {
        int num = number.intValue();
        switch(num) {
            case 0:
                return "A";
            case 1:
                return "C";
            case 2:
                return "G";
        }
        // 3
        return "T";
    }

    public static BigInteger patternToNumber(String pattern) {
        if(pattern.length() == 0) return BigInteger.valueOf(0);
        String prefix = pattern.substring(0, pattern.length()-1);
        String suffix = pattern.substring(pattern.length()-1);
        return BigInteger.valueOf(4).multiply(patternToNumber(prefix)).add(BigInteger.valueOf(symbolToNumber(suffix)));
    }

    public static String numberToPattern(BigInteger number, int k) {
        if(k == 1) return numberToSymbol(number);
        BigInteger i = BigInteger.valueOf(4);
        BigInteger remainder = number.mod(i);
        BigInteger prefixIndex = number.divide(i);
        String symbol = numberToSymbol(remainder);
        String prefixPattern = numberToPattern(prefixIndex, k-1);
        return prefixPattern.concat(symbol);
    }

}
