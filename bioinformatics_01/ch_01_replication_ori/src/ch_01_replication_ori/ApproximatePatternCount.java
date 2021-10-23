package ch_01_replication_ori;

import java.io.IOException;
import java.util.ArrayList;

public class ApproximatePatternCount {
	
	/**
	 * Count of pattern matching, but with d or less mismatches
	 * (HammingDistance <= d)
	 * @param pattern to look for in the text
	 * @param textAddress to produce text (genome) 
	 * @param mismatches - number of mismatches
	 * @return the count of matching patterns
	 */
	public static int approximatePatternCount(
			String pattern, String textAddress, int mismatches) {
		ArrayList<String> matchingPatterns = new ArrayList<>();
		ArrayList<Integer> positionsOfMatches = new ArrayList<>();
		String text = "";
		try {
			text = FileImporter.importFile(textAddress);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < text.length() - pattern.length() + 1; i++) {
			if(text.substring(i, i + pattern.length()).equals(pattern) ||
					HammingDistance.hammingDistance(text.substring(i, i + pattern.length()), pattern) <= mismatches) {
				matchingPatterns.add(text.substring(i, i+pattern.length()));
				positionsOfMatches.add(i);
			}
		}

//		System.out.println(matchingPatterns);

		return matchingPatterns.size();
	}

	/**
	 * overloaded version with text, instead of the text address
	 * @param mismatches
	 * @param text
	 * @param pattern
	 * @return
	 */
	public static int approximatePatternCountTwo(
			int mismatches, String text, String pattern) {
		ArrayList<String> matchingPatterns = new ArrayList<>();
		ArrayList<Integer> positionsOfMatches = new ArrayList<>();
		for(int i = 0; i < text.length() - pattern.length() + 1; i++) {
			if(text.substring(i, i + pattern.length()).equals(pattern) ||
					HammingDistance.hammingDistance(text.substring(i, i + pattern.length()), pattern) <= mismatches) {
				matchingPatterns.add(text.substring(i, i+pattern.length()));
				positionsOfMatches.add(i);
			}
		}

//		System.out.println(matchingPatterns);

		return matchingPatterns.size();
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(approximatePatternCount("AAAAA", "E:\\java_learning\\Bioinformatics\\approximatePatternMatching.txt", 1));
		

	}

}
