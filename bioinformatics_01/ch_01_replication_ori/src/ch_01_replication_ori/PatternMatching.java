package ch_01_replication_ori;

import java.io.IOException;
import java.util.ArrayList;

public class PatternMatching {
	
	public static ArrayList<Integer> patternMatching(String pattern, String genome){
		ArrayList<Integer> startingPositions = new ArrayList<>();
		
		for(int i = 0; i < genome.length() - pattern.length() + 1; i++) {
			if(genome.substring(i, pattern.length() + i).equals(pattern)) {
				startingPositions.add(i);
			}
		}
		
		
		return startingPositions;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String text = "";
		try {
			text = FileImporter.importFile("E:\\java_learning\\Bioinformatics\\vibrio_cholerae_genome.txt");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		ArrayList<Integer> list = patternMatching("ATGATCAAG", text);
		for(int i : list) {
			System.out.print(i + " ");
		}

	}

}
