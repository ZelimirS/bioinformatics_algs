package ch_01_replication_ori;

import java.io.IOException;

public class ReverseComplement {

	public static String reverseComplement(String text) {
		String complement = new String();
		for (int i = 0; i < text.length(); i++) {
			String str = text.substring(i, i + 1);
			switch (str) {
			case "A":
				complement += "T";
				break;
			case "C":
				complement += "G";
				break;
			case "G":
				complement += "C";
				break;
			case "T":
				complement += "A";
				break;
			}
		}
		StringBuilder sb = new StringBuilder(complement);
		sb.reverse();
		complement = new String(sb);

		return complement;
	}

	public static void main(String[] args) {
		String text = "";
		try {
			text = FileImporter.importFile("E:\\java_learning\\Bioinformatics\\rosalind_ba1c.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(reverseComplement(text));
	}

}
