package ch_01_replication_ori;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class Ori {

	public static int patternCount(String text, String pattern) {
		int count = 0;
		int kmer = pattern.length();
		for (int i = 0; i < text.length() - kmer + 1; i++) {
			if (text.substring(i, i+kmer).equalsIgnoreCase(pattern)) {
				count++;
			}
		}
		return count;
	}

	public static HashSet<String> frequentWords(String text, int k) {
		HashSet<String> frequentPatterns = new HashSet<>();
		int[] count = new int[text.length() - k + 1];
		for (int i = 0; i < text.length() - k + 1; i++) {
			String pattern = text.substring(i, k+i);
			count[i] = patternCount(text, pattern);
		}
		int maxCount = Arrays.stream(count).max().getAsInt();
		for (int i = 0; i < text.length() - k + 1; i++) {
			if (count[i] == maxCount) {
				frequentPatterns.add(text.substring(i, k+i));
			}
		}
		return frequentPatterns;
	}
	
	public static LinkedHashMap<String, Integer> frequencyTable(String text, int k) {
		LinkedHashMap<String, Integer> freqMap = new LinkedHashMap<>();
		int lenText = text.length();
		for(int i = 0; i < lenText - k + 1; i++) {
			String pattern = text.substring(i, i + k);
			if(!freqMap.containsKey(pattern)) {
				freqMap.put(pattern, 1);
			}
			else {
				freqMap.put(pattern, freqMap.get(pattern) + 1);
			}
		}
		return freqMap;
	}
	
	public static int maxMap(Map<String, Integer> map) {
		Collection<Integer> valuesSet = map.values();
		Integer max = Collections.max(valuesSet);
		return max;
	}
	public static int maxMap2(HashMap<HashMap<String, String>, Integer> map) {
		Collection<Integer> valuesSet = map.values();
		Integer max = Collections.max(valuesSet);
		return max;
	}
	public static ArrayList<String> betterFrequentWords(String text, int k) {
		ArrayList<String> frequentPatterns = new ArrayList<>();
		HashMap<String, Integer> freqMap = frequencyTable(text, k);
		int max = maxMap(freqMap);
		for(String str : freqMap.keySet()) {
			if(freqMap.get(str) == max) {
				frequentPatterns.add(str);
			}
		}
		System.out.println(freqMap);//*************************************
		return frequentPatterns;
	}
	
	public static String index(String text, int beginIndex, int segmentLength) {
		String segment = "";
		int textLength = text.length();
		if(textLength >= segmentLength && beginIndex + segmentLength <= textLength) {
			segment = text.substring(beginIndex, textLength);
		}
		return segment;
	}

	public static String text = "";
	
	public static void main(String[] args) {
//		String text = "";
		try {
			text = FileImporter.importFile("E:\\java_learning\\Bioinformatics\\e_coli_genome.txt");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		String pattern = "acg";
		String segment = index(text, 3729223, 500);
		
//		System.out.println(patternCount(text, pattern));
//		System.out.println(frequentWords(text, 3));
//		System.out.println(frequencyTable(text, 9));
		System.out.println("***************************************************");
//		System.out.println(maxMap(frequencyTable(text, 11)));
		System.out.println(betterFrequentWords(segment, 9));
		
	}

}
