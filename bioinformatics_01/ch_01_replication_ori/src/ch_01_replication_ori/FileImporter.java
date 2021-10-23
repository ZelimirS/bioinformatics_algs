package ch_01_replication_ori;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FileImporter {

	public static String importFile(String adress) throws IOException {
		File file = new File(adress);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str = "";
		String s = "";
		while((s = br.readLine()) != null) {
			str += s;
		}

		br.close();
		return str;

	}

	public static ArrayList<String> importFileLineByline(String address) throws IOException {
		ArrayList<String> arrList = new ArrayList<>();
		File file = new File(address);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str = "";
		while((str = br.readLine()) != null) {
			arrList.add(str);
		}
		br.close();

		return arrList;
	}

	

}
