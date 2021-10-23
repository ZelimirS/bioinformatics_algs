package ch_01_replication_ori;

import java.io.IOException;
import java.util.ArrayList;

public class ApproximatePatternMatching {
	
	/**
	 * Pattern matching, but with d or less mismatches
	 * (HammingDistance <= d)
	 * @param pattern to look for in the text
	 * @param textAddress to produce text (genome) 
	 * @param mismatches - number of mismatches
	 * @return an ArrayList with starting positions of matching patterns
	 */
	public static ArrayList<Integer> approximatePatternMatching(
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
			String patt = text.substring(i, i+pattern.length());
			if(patt.equals(pattern) ||
					HammingDistance.hammingDistance(patt, pattern) <= mismatches) {
				matchingPatterns.add(patt);
				positionsOfMatches.add(i);
			}
		}

		return positionsOfMatches;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Integer> res = (approximatePatternMatching("ACT", "E:\\java_learning\\Bioinformatics\\approximatePatternMatching.txt", 1));
		for(int i : res) {
			System.out.print(i + " ");
		}
	}

}
