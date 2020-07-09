package ch_01_replication_ori;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;


public class MinimumSkew {
	
	public static ArrayList<Integer> skew(String genomeAddress) {
		ArrayList<Integer> minSkew = new ArrayList<>();
		String text = "";
		
		try {
			text = FileImporter.importFile(genomeAddress);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		int[] skewNums = new int[text.length()+1];
		skewNums[0] = 0;
		ArrayList<Integer> indexes = new ArrayList<>();
		int numC = 0;
		int numG = 0;
		String s = "";
		for(int i = 0; i < text.length(); i++) {
			s = text.substring(i, i+1);
			switch(s) {
			case "C":
				numC++;
				break;
			case "G":
				numG++;
				break;
			}
			int skewNum = numG - numC;
			skewNums[i+1] = skewNum;
		}
		int[] minArr = new int[skewNums.length];
		for(int n = 0; n < skewNums.length; n++) {
			minArr[n] = skewNums[n];
		}
		Arrays.sort(minArr);
		int min = minArr[0];
		for(int j = 0; j < skewNums.length; j++) {
			if(skewNums[j] == min) indexes.add(j); 
		}
		
		return indexes;
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Integer> aL = skew("E:\\java_learning\\Bioinformatics\\Salmonella enterica subsp. enterica serovar Typhi str. CT18 chromosome, complete genome.txt");
		for(int i : aL) {
			System.out.print(i + " ");
		}

	}

}
