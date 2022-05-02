package euler;

import ch_03_graph_algs.DeBruijnGraphFromKmers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class K_UniversalCircularString {

    public static String kUniversalCircularString(int k){
        List<String> binaryStrings_k = new ArrayList<>();
        Map<String, List<String>> deBruijnMap = new HashMap<>();

        for (int i = 0; i < Math.pow(2, k); i++){
            String str = Integer.toBinaryString(i);
            int condition = k - str.length();
            for(int j = 0; j < condition; j++){
                str = "0" + str;
            }
            binaryStrings_k.add(str);
        }
        deBruijnMap = DeBruijnGraphFromKmers.deBruijnGraphFromKmers(binaryStrings_k);
        String result = HierholzerEuler.eulerianCircuitString(deBruijnMap, k);


        return result;
    }

    public static void main(String[] args) {
        System.out.println(kUniversalCircularString(8));
    }

}
