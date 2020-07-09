package ch_01_replication_ori;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Neighbors {

	/**
	 * d-neighborhood - the set of all k-mers who's Humming distance from pattern <= mismatches
	 * using recursion
	 * @param pattern
	 * @param mismatches
	 * @return - the set of k-mers
	 */
	public static LinkedHashSet<String> neighbors(String pattern, int mismatches) {
		LinkedHashSet<String> neighbors = new LinkedHashSet<>();
		LinkedHashSet<String> suffixNeighbors = new LinkedHashSet<>();
		if (mismatches == 0) {
			neighbors.add(pattern);
			return neighbors;
		}
		if (pattern.length() == 1) {
			neighbors.add("A");
			neighbors.add("C");
			neighbors.add("G");
			neighbors.add("T");
			return neighbors;
		}
		suffixNeighbors = neighbors(pattern.substring(1, pattern.length()), mismatches);
		for (String text : suffixNeighbors) {
			if (HammingDistance.hammingDistance(pattern.substring(1, pattern.length()), text) < mismatches) {
				for (String s : new String[] { "A", "C", "G", "T" }) {
					neighbors.add(s.concat(text));
				}
			} else {
				neighbors.add(pattern.substring(0, 1).concat(text));
			}
		}

		return neighbors;
	}

	/**
	 * d-neighborhood - the set of all k-mers who's Humming distance from pattern <= 1
	 * @param pattern
	 * @param mismatches
	 * @return - the set of k-mers
	 */
	public static LinkedHashSet<String> immediateNeighbors(String pattern) {
		LinkedHashSet<String> neighborhood = new LinkedHashSet<>();
		neighborhood.add(pattern);
		int patternLength = pattern.length();
		String neighbor = "";
		for (int i = 0; i < patternLength; i++) {
			String symbol = pattern.substring(i, i + 1);
			for (String str : new String[] { "A", "C", "G", "T" }) {
				if (!str.equals(symbol)) {
					StringBuilder sb = new StringBuilder(pattern);
					char ch = str.charAt(0);
					sb.setCharAt(i, ch);
					neighbor = sb.toString();
					neighborhood.add(neighbor);
				}
			}
		}
		return neighborhood;
	}

	public static LinkedHashSet<String> iterativeNeighbors(String text, int mismatches) {
		LinkedHashSet<String> neighborhood = new LinkedHashSet<>();
		LinkedHashSet<String> immNeighborhood = new LinkedHashSet<>();
		neighborhood.add(text);
		for (int j = 0; j < mismatches; j++) {
			for(String s : neighborhood) {
				immNeighborhood = immediateNeighbors(s);
			}
			for(String str : immNeighborhood) {
				neighborhood.add(str);
			}
		}

		return neighborhood;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		LinkedHashSet<String> res = iterativeNeighbors("ACT", 1);
//		LinkedHashSet<String> res = iterativeNeighbors("GGCCCAGAG", 3);
//		for (String str : res) {
//			System.out.println(str);
//		}
		System.out.println("_____________________________________________________");
		LinkedHashSet<String> result = neighbors("ACT", 1);
		for (String str : result) {
			System.out.println(str);
		}

	}

}
