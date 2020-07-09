package ch_01_replication_ori;

import java.io.IOException;
import java.util.ArrayList;

public class HammingDistance {
	
	/**
	 * Hamming distance - number of mismatches between two strings
	 * of equal length
	 * @param a
	 * @param b
	 * @return hamming distance
	 */
	public static int hammingDistance(String a, String b) {
		int aLength = a.length();
		int bLength = b.length();
		int distance = 0;
		if(aLength == bLength) {
			char[] aChar = a.toCharArray();
			char[] bChar = b.toCharArray();
			for(int i = 0; i < aChar.length; i++) {
				if(aChar[i] != bChar[i]) {
					distance++;
				}
			}
		}
		else {
			System.out.println("Strings are not of equal length.");
			return -1;
		}
		
		return distance;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String one = "";
		String two = "";
		try {
			one = FileImporter.importFile("E:\\java_learning\\Bioinformatics\\rosalind_ba1g_A.txt");
			two = FileImporter.importFile("E:\\java_learning\\Bioinformatics\\rosalind_ba1g_B.txt");
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		System.out.println(hammingDistance(one, two));
	}

}
