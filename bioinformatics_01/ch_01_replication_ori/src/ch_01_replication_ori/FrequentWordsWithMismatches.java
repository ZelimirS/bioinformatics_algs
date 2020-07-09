package ch_01_replication_ori;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;

public class FrequentWordsWithMismatches {
	
	
	/**
	 * The algorithm for solving the Frequent Words 
	 * with Mismatches Problem becomes rather slow 
	 * as k and d increase. In practice, your solution 
	 * should work for k ≤ 12 and d ≤ 3
	 * @param genomAdress
	 * @param patternLength
	 * @param mismatches
	 * @return
	 */
	public static ArrayList<String> frequentWordsWithMismatches(
			String genomAdress, int patternLength, int mismatches) {
		HashMap<String, Integer> freqMap = new HashMap<>();
		LinkedHashSet<String> neighborhood = new LinkedHashSet<>();
		ArrayList<String> patterns = new ArrayList<>();
		String text = "";
		try {
			text = FileImporter.importFile(genomAdress);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		int textLength = text.length();
		for(int i = 0; i < textLength - patternLength + 1; i++) {
			String pattern = text.substring(i, i + patternLength);
			neighborhood = Neighbors.neighbors(pattern, mismatches);
			for(String neighbor : neighborhood) {
				if(!freqMap.containsKey(neighbor)) {
					freqMap.put(neighbor, 1);
				}
				else {
					freqMap.put(neighbor, freqMap.get(neighbor)+1);
				}
			}
		}
		int max = Ori.maxMap(freqMap);
		for(Entry entry : freqMap.entrySet()) {
			if(entry.getValue().equals(max)) {
				patterns.add((String) entry.getKey());
			}
		}
		
		return patterns;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> result = new ArrayList<>();
		result = frequentWordsWithMismatches(
				"E:\\java_learning\\Bioinformatics\\approximatePatternMatching.txt",
				4, 1);
		for(String str : result) {
			System.out.print(str + " ");
		}
	}

}
