package ch_01_replication_ori;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class ClumpFinding {

	public static HashSet<String> findClumps(String genomeAddress, int k, int L, int times) {
		HashSet<String> patterns = new HashSet<>();
		LinkedHashMap<String, Integer> freqMap = new LinkedHashMap<>();
		String text = "";

		try {
			text = FileImporter.importFile(genomeAddress);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int textLength = text.length();
		for (int i = 0; i < textLength - L; i++) {
			String window = text.substring(i, L + i);
			freqMap = Ori.frequencyTable(window, k);
			for (Entry<String, Integer> entry : freqMap.entrySet()) {
				if (entry.getValue() >= times) {
					patterns.add(entry.getKey());
				}
			}
		}

		return patterns;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(findClumps("E:\\java_learning\\Bioinformatics\\e_coli_genome.txt", 9, 500, 3));
		// **************************************************
		
	}

}
