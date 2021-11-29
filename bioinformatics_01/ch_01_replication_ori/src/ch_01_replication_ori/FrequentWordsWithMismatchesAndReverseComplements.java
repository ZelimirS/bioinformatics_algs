package ch_01_replication_ori;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FrequentWordsWithMismatchesAndReverseComplements {


	public static HashSet<String> frequentWordsWithMismatchesAndReverseComplementsTwo(
			String genomAdress, int patternLength, int mismatches){
		HashSet<String> patterns = new HashSet<>();
		int[] close = new int[(int) Math.pow(4.0, patternLength)];
		int[] closeComplements = new int[(int) Math.pow(4.0, patternLength)];
		int[] frequencyArray = new int[(int) Math.pow(4.0, patternLength)];
		int[] frequencyArrayComplements = new int[(int) Math.pow(4.0, patternLength)];
		String text = "";
		try {
			text = FileImporter.importFile(genomAdress);
		}
		catch(IOException e){
			e.printStackTrace();
		}
				text = text.substring(3923620, 3923620+500); // 500-mer segment $$$$$$$$$$$$$$$$$$$$$$$$$$$$$
		for(int i = 0; i < text.length() - patternLength + 1; i++){
			String pattern = text.substring(i, i + patternLength);
			LinkedHashSet<String> neighbors = Neighbors.neighbors(pattern, mismatches);
			for(String patt : neighbors){
				int index = Ori.patternToNumber(patt);
				close[index] = 1;
			}
		}
		String textComplement = ReverseComplement.reverseComplement(text);
		for(int i = 0; i < textComplement.length() - patternLength + 1; i++){
			String pattern = textComplement.substring(i, i + patternLength);
			LinkedHashSet<String> neighborsComplements = Neighbors.neighbors(pattern, mismatches);
			for(String patt : neighborsComplements){
				int index = Ori.patternToNumber(patt);
				closeComplements[index] = 1;
			}
		}
		for(int i = 0; i < close.length; i++){
			if(close[i] == 1){
				String ptrn = Ori.numberToPattern(i, patternLength);
				frequencyArray[i] = ApproximatePatternCount.approximatePatternCount(mismatches, text, ptrn);
			}
		}
		for(int i = 0; i < closeComplements.length; i++){
			if(closeComplements[i] == 1){
				String ptrnComplement = Ori.numberToPattern(i, patternLength);
				frequencyArrayComplements[i] = ApproximatePatternCount.approximatePatternCount(mismatches, textComplement, ptrnComplement);
			}
		}
		int[] frequencyArrayTotal = new int[(int) Math.pow(4.0, patternLength)];
		for(int i = 0; i < frequencyArrayTotal.length; i++){
			frequencyArrayTotal[i] = frequencyArray[i] + frequencyArrayComplements[i];
		}
		int maxCount = Arrays.stream(frequencyArrayTotal).max().getAsInt();
		for(int i = 0; i < frequencyArrayTotal.length; i++){
			if(frequencyArrayTotal[i] == maxCount)
				patterns.add(Ori.numberToPattern(i, patternLength));
		}

		return patterns;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, String>> result = new ArrayList<>();
		HashSet<String> res = new HashSet<>();
//		result = frequentWordsWithMismatchesAndReverseComplements(
//				"E:\\java_learning\\Bioinformatics\\Salmonella enterica subsp. enterica serovar Typhi str. CT18 chromosome, complete genome.txt", 9, 1);
		res = frequentWordsWithMismatchesAndReverseComplementsTwo(
				"E:\\java_learning\\Bioinformatics\\e_coli_genome.txt", 9, 1);
//		HashSet<String> set = new HashSet<>();
//		for (HashMap<String, String> hm : result) {
//			for (Entry ent : hm.entrySet()) {
//				set.add((String) ent.getKey());
//				set.add((String) ent.getValue());
//			}
//		}
//		System.out.println("_______________________________________________");
//		for (String st : set) {
//			System.out.print(st + " ");
//		}
//		System.out.println(res);
		for (String st : res) {
			System.out.print(st + " ");
		}
	}
}
