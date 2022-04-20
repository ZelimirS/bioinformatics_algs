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
	
	/* *************************************************************************************
	 * frequent patterns based on a frequency array
	 * */
	
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
	
	public static String numberToSymbol(int number) {
		switch(number) {
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
	
	public static int patternToNumber(String pattern) {
		if(pattern.length() == 0) return 0;
		String prefix = pattern.substring(0, pattern.length()-1);
		String suffix = pattern.substring(pattern.length()-1);
		return 4 * patternToNumber(prefix) + symbolToNumber(suffix);
	}
	
	public static String numberToPattern(int number, int k) {
		if(k == 1) return numberToSymbol(number);
		int remainder = number % 4;
		int prefixIndex = number / 4;
		String symbol = numberToSymbol(remainder);
		String prefixPattern = numberToPattern(prefixIndex, k-1);
		return prefixPattern.concat(symbol);
	}
	
	public static int[] computingFrequencies(String text, int k) {
		int[] frequencyArray = new int[(int)Math.pow(4.0, k)];
		String pattern = "";
		for(int i = 0; i < frequencyArray.length; i++) {
			frequencyArray[i] = 0;
		}
		for(int i = 0; i < text.length() - k + 1; i++) {
			pattern = text.substring(i, i+k);
			int j = (int) patternToNumber(pattern);
			frequencyArray[j] = frequencyArray[j]+1;
		}
		
		return frequencyArray;
	}
	
	public static HashSet<String> fasterFrequentWords(String text, int k){
		HashSet<String> frequentWords = new HashSet<>();
		int[] frequencyArray = computingFrequencies(text, k);
		int maxCount = Arrays.stream(frequencyArray).max().getAsInt();
		for(int i = 0; i < frequencyArray.length; i++) {
			if(frequencyArray[i] == maxCount)
				frequentWords.add(numberToPattern(i, k));
		}
		
		return frequentWords;
	}
	
	// frequent words by sorting
	
		public static HashSet<String> frequentWordsBySorting(String text, int k) {
			HashSet<String> patterns = new HashSet<>();
			int[] count = new int[text.length() - k + 1];
			int[] index = new int[text.length() - k + 1];
			for(int i = 0; i < text.length() - k + 1; i++) {
				String pattern = text.substring(i, i + k);
				index[i] = patternToNumber(pattern);
				count[i] = 1;
			}
			Arrays.sort(index);

			for(int j = 1; j < text.length() - k + 1; j++) {
				if(index[j] == index[j-1]) {
					count[j] = count[j-1] + 1;
				}
			}
			int maxCount = Arrays.stream(count).max().getAsInt();
			for(int i = 0; i < text.length() - k + 1; i++) {
				if(count[i] == maxCount) {
					String patt = numberToPattern(index[i], k);
					patterns.add(patt);
				}
			}
			
			return patterns;
		}
	
	
	
	//**************************************************************************************
	
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
		String text1 = "";
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
		//System.out.println("***************************************************");
//		System.out.println(maxMap(frequencyTable(text, 11)));
		//System.out.println(betterFrequentWords(segment, 9));


		System.out.println(patternToNumber("AGT"));
		System.out.println(numberToPattern(11, 3));
		System.out.println(2 / 4);
		
	}

}
