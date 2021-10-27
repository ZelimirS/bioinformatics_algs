package ch_01_replication_ori;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FrequentWordsWithMismatchesAndReverseComplements {

	/**
	 * The algorithm for solving the Frequent Words with Mismatches Problem becomes
	 * rather slow as k and d increase. In practice, your solution should work for k
	 * ≤ 12 and d ≤ 3
	 * 
	 * @param genomAdress
	 * @param patternLength
	 * @param mismatches
	 * @return
	 */

/*	public static ArrayList<HashMap<String, String>> frequentWordsWithMismatchesAndReverseComplements(
			String genomAdress, int patternLength, int mismatches) {
		HashMap<String, Integer> freqMap = new HashMap<>();
		LinkedHashSet<String> neighborhood = new LinkedHashSet<>();
		HashMap<String, Integer> freqMapComplements = new HashMap<>();
		LinkedHashSet<String> neighborhoodComplements = new LinkedHashSet<>();
		String text = "";
		try {
			text = FileImporter.importFile(genomAdress);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String textComplement = ReverseComplement.reverseComplement(text);
//		text = text.substring(1882416, 1882416+500); // 500-mer segment $$$$$$$$$$$$$$$$$$$$$$$$$$$$$
		int textLength = text.length();
		for (int i = 0; i < textLength - patternLength + 1; i++) {
			String pattern = text.substring(i, i + patternLength);
			String patternComplement = textComplement.substring(1, i + patternLength);
			neighborhood = Neighbors.neighbors(pattern, mismatches);
			neighborhoodComplements = Neighbors.neighbors([patternComplement, mismatches]);
			for (String neighbor : neighborhood) {
				if (!freqMap.containsKey(neighbor)) {
					freqMap.put(neighbor, 1);

				} else {
					freqMap.put(neighbor, freqMap.get(neighbor) + 1);
				}
			}
			for (String neighbor : neighborhoodComplements) {
				if (!freqMapComplements.containsKey(neighbor)) {
					freqMapComplements.put(neighbor, 1);

				} else {
					freqMapComplements.put(neighbor, freqMapComplements.get(neighbor) + 1);
				}
			}
		}
		HashMap<HashMap<String, String>, Integer> freqMapTotal = new HashMap<>();
		HashMap<Map.Entry<String, String>, Integer> freqMapTotal2 = new HashMap<>();
		int complementValue = 0;
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		HashMap<String, String> patternsCombined = new HashMap<>();
		Set<String> fm1 = freqMap.keySet();
		Set<String> fm2 = freqMapComplements.keySet();
		for(String str : fm1){
			patternsCombined.put(str, fm2.stream().reduce())
		}
		patternsCombined = fm1





		for (Entry entry : freqMap.entrySet()) {
			if (freqMap.containsKey(ReverseComplement.reverseComplement((String) entry.getKey()))) {
				complementValue = freqMap.get(ReverseComplement.reverseComplement((String) entry.getKey()));
				System.out.println("****************************************");
			}
			int tempAddition = (Integer) entry.getValue() + complementValue;
			HashMap<String, String> tempMap = new HashMap<>();
			tempMap.put((String) entry.getKey(), ReverseComplement.reverseComplement((String) entry.getKey()));
			freqMapTotal.put(tempMap, tempAddition);
			complementValue = 0;
		}
		System.out.println(freqMapTotal + " >>>>>>>>>>>>>>>>>>>>>"); ///////////////////////////////////////

		int maxAdd = Ori.maxMap2(freqMapTotal);
		ArrayList<HashMap<String, String>> res = new ArrayList<>();
		for (Entry entry : freqMapTotal.entrySet()) {
			if ((Integer) entry.getValue() == maxAdd) {
				res.add((HashMap) entry.getKey());
			}
		}
		for(HashMap<String, String> hm : res) {
			for(Entry<String, String> ent : hm.entrySet()) {
				System.out.print(ent.getKey() + ": " + freqMap.get(ent.getKey()) + "\t\t");
				System.out.println(ent.getValue() + ": " + freqMap.get(ent.getKey()));
			}
		}

		return res;

	}*/


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
