package ch_01_replication_ori;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map.Entry;

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
	public static ArrayList<HashMap<String, String>> frequentWordsWithMismatchesAndReverseComplements(
			String genomAdress, int patternLength, int mismatches) {
		HashMap<String, Integer> freqMap = new HashMap<>();
		LinkedHashSet<String> neighborhood = new LinkedHashSet<>();
		String text = "";
		try {
			text = FileImporter.importFile(genomAdress);
		} catch (IOException e) {
			e.printStackTrace();
		}
		text = text.substring(1882416, 1882416+500); // 500-mer segment $$$$$$$$$$$$$$$$$$$$$$$$$$$$$
		int textLength = text.length();
		for (int i = 0; i < textLength - patternLength + 1; i++) {
			String pattern = text.substring(i, i + patternLength);
			neighborhood = Neighbors.neighbors(pattern, mismatches);
			for (String neighbor : neighborhood) {
				if (!freqMap.containsKey(neighbor)) {
					freqMap.put(neighbor, 1);

				} else {
					freqMap.put(neighbor, freqMap.get(neighbor) + 1);
				}
			}
		}

		HashMap<HashMap<String, String>, Integer> additions = new HashMap<>();
		int complementValue = 0;
		for (Entry entry : freqMap.entrySet()) {
			if (freqMap.containsKey(ReverseComplement.reverseComplement((String) entry.getKey()))) {
				complementValue = freqMap.get(ReverseComplement.reverseComplement((String) entry.getKey()));
			}
			int tempAddition = (Integer) entry.getValue() + complementValue;
			HashMap<String, String> tempMap = new HashMap<>();
			tempMap.put((String) entry.getKey(), ReverseComplement.reverseComplement((String) entry.getKey()));
			additions.put(tempMap, tempAddition);
			complementValue = 0;
		}

		int maxAdd = Ori.maxMap2(additions);
		ArrayList<HashMap<String, String>> res = new ArrayList<>();
		for (Entry entry : additions.entrySet()) {
			if ((Integer) entry.getValue() == maxAdd) {
				res.add((HashMap) entry.getKey());
			}
		}
		for(HashMap<String, String> hm : res) {
			for(Entry<String, String> ent : hm.entrySet()) {
				System.out.print(ent.getKey() + ": " + freqMap.get(ent.getKey()) + "\t\t");
				System.out.println(ent.getValue() + ": " + freqMap.get(ent.getValue()));
			}
		}

		return res;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, String>> result = new ArrayList<>();
		result = frequentWordsWithMismatchesAndReverseComplements(
				"E:\\java_learning\\Bioinformatics\\Salmonella enterica subsp. enterica serovar Typhi str. CT18 chromosome, complete genome.txt", 9, 1);
		HashSet<String> set = new HashSet<>();
		for (HashMap<String, String> hm : result) {
			for (Entry ent : hm.entrySet()) {
				set.add((String) ent.getKey());
				set.add((String) ent.getValue());
			}
		}
		System.out.println("_______________________________________________");
		for (String st : set) {
			System.out.print(st + " ");
		}
	}
}
