package ch_01_replication_ori;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import  java.util.*;

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

	/**
	 * method that follows the pseudocode from the book
	 * @param genomAdress
	 * @param patternLength
	 * @param mismatches
	 * @return
	 */
	public static LinkedHashSet<String> frequentWordsWithMismatchesTwo(String genomAdress, int patternLength, int mismatches) {
		LinkedHashSet<String> patterns = new LinkedHashSet<>();
		int[] close = new int[(int) Math.pow(4.0, patternLength)];
		int[] frequencyArray = new int[(int) Math.pow(4.0, patternLength)];
		for(int i = 0; i < close.length; i++){
			close[i] = 0;
			frequencyArray[i] = 0;
		}
		String text = "";
		try {
			text = FileImporter.importFile(genomAdress);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		for(int i = 0; i < text.length() - patternLength + 1; i++){
			String pattern = text.substring(i, i + patternLength);
			LinkedHashSet<String> neighbors = Neighbors.neighbors(pattern, mismatches);
			for(String patt : neighbors){
				int index = Ori.patternToNumber(patt);
				close[index] = 1;
			}
		}
		for(int i = 0; i < close.length; i++){
			if(close[i] == 1){
				String pattern = Ori.numberToPattern(i, patternLength);
				frequencyArray[i] = ApproximatePatternCount.approximatePatternCountTwo(mismatches, text, pattern);
			}
		}
		int maxCount = Arrays.stream(frequencyArray).max().getAsInt();
		for(int i = 0; i < frequencyArray.length; i++){
			if(frequencyArray[i] == maxCount){
				patterns.add(Ori.numberToPattern(i, patternLength));
			}
		}

		return patterns;
	}

	public static LinkedHashSet<String> frequentWordsWithMismatchesBySorting(String genomAdress, int patternLength, int mismatches) {
		LinkedHashSet<String> patterns = new LinkedHashSet<>();
		ArrayList<String> neighborhoods = new ArrayList<>();
		String text = "";
		try {
			text = FileImporter.importFile(genomAdress);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		for(int i = 0; i < text.length() - patternLength + 1; i++){
			String pattern = text.substring(i, i + patternLength);
			for(String str : Neighbors.neighbors(pattern, mismatches)) {
				neighborhoods.add(str);
			}
		}
		int[] count = new int[neighborhoods.size()];
		int[] index = new int[neighborhoods.size()];
		String[] neighborhoodArray = neighborhoods.toArray(new String[0]);
		for(int i = 0; i < neighborhoods.size(); i++){
			String patt = neighborhoodArray[i];
			index[i] = Ori.patternToNumber(patt);
			count[i] = 1;
		}
		Arrays.sort(index);
		for(int i = 0; i < neighborhoods.size()-1; i++){
			if(index[i] == index[i+1]){
				count[i+1] = count[i] + 1;
			}
		}
		int maxCount = Arrays.stream(count).max().getAsInt();
		for(int i = 0; i < neighborhoods.size(); i++){
			if(count[i] == maxCount){
				String ptrn = Ori.numberToPattern(index[i], patternLength);
				patterns.add(ptrn);
			}
		}

		return patterns;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> result = new ArrayList<>();
		LinkedHashSet<String> res = new LinkedHashSet<>();
		result = frequentWordsWithMismatches(
				"E:\\java_learning\\Bioinformatics\\radni.txt",
				10, 2);
		for(String str : result) {
			System.out.print(str + " ");
		}
	}

}
