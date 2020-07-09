package ch_01_replication_ori;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileImporter {
	
	public static String importFile(String adress) throws IOException {
		File file = new File(adress);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str = "";
		while(br.readLine() != null) {
			str += br.readLine();
		}

		br.close();
		return str;
		
	}

	

}
