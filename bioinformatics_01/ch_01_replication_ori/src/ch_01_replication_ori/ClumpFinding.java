package ch_01_replication_ori;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
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
		for (int i = 0; i < textLength - L + 1; i++) {
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
	
	public static HashSet<String> clumpFinding(String genomeAddress, int k, int L, int times) {
		HashSet<String> patterns = new HashSet<>();
		int[] clamp = new int[(int) Math.pow(4.0, k)];
		for(int i = 0; i < clamp.length; i++)
			clamp[i] = 0;
		
		String text = "";
		try {
			text = FileImporter.importFile(genomeAddress);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < text.length() - L + 1; i++) {
			String textL = text.substring(i, i + L);
			int[] frequencyArray = Ori.computingFrequencies(textL, k);
			for(int j = 0; j < Math.pow(4, k); j++) {
				if(frequencyArray[j] >= times)
					clamp[j] += 1;
			}
			for(int m = 0; m < Math.pow(4, k); m++) {
				if(clamp[m] >= times) {
					String patt = Ori.numberToPattern(m, k);
					patterns.add(patt);
				}			
			}
		}	
		return patterns;
	}
	
	public static HashSet<String> betterClumpFinding(String genomeAddress, int k, int L, int times){
		HashSet<String> patterns = new HashSet<>();
		int[] clamp = new int[(int) Math.pow(4.0, k)];
		for(int i = 0; i < clamp.length; i++)
			clamp[i] = 0;
		
		String text = "";
		try {
			text = FileImporter.importFile(genomeAddress);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String textL = text.substring(0, L);
		int[] frequencyArray = Ori.computingFrequencies(textL, k);
		for(int i = 0; i < (int) Math.pow(4, k); i++) {
			if(frequencyArray[i] >= times) {
				clamp[i] = 1;
			}
		}
		for(int i = 1; i < text.length() - L + 1; i++) {
			String firstPattern = text.substring(i-1, i-1 + k);
			int index = Ori.patternToNumber(firstPattern);
			frequencyArray[index] = frequencyArray[index] - 1;
			String lastPattern = text.substring(i + L - k, i + L);
			index = Ori.patternToNumber(lastPattern);
			frequencyArray[index] = frequencyArray[index] + 1;
			if(frequencyArray[index] >= times)
				clamp[index] = 1;
			
		}

		for(int l = 0; l < (int) Math.pow(4, k); l++) {
			if(frequencyArray[l] >= times)
				clamp[l] = 1;
			if(clamp[l] == 1) {
				String pattern = Ori.numberToPattern(l, k);
				patterns.add(pattern);
			}

		}
		
		return patterns;
	}


	public static void main(String[] args) {
		long start = System.nanoTime();
		// TODO Auto-generated method stub
//		String text1 = "CGGACTCGACAGATG TGAAGAAATGTGAAGACTGAGTGAAGAGAAGAGGAAACACGACACGACATTGCGACATAATGTACGAATGTAATGTGCCTATGGC";
//		System.out.println(findClumps("E:\\java_learning\\Bioinformatics\\e_coli_genome.txt", 5, 75, 4)); // 11 566 18
		System.out.println(betterClumpFinding("E:\\java_learning\\Bioinformatics\\e_coli_genome.txt", 5, 75, 4));
//		System.out.println(clumpFinding("E:\\java_learning\\Bioinformatics\\radni.txt", 5, 50, 4));
		long end = System.nanoTime();
		System.out.println((end - start) / Math.pow(10.0, 9));

		
	}

}
