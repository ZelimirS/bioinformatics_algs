package euler;

import java.util.*;

// eulerian cycle
public class HierholzerEuler {

    public static void main(String[] args) {
        Map<Integer, List<Integer>> adj = new HashMap<>();
        adj = AdjecancyListImporter.importAdjecancy("/Users/zelimirstojcevic/" +
                "IdeaProjects/bioinformatics_algs/bio_files/rosalind_ba3f-7.txt"); // rosalind_ba3f

        printEulerianCircuit(adj);
    }

    public static void printEulerianCircuit(Map<Integer, List<Integer>> adj){

        Stack<Integer> currPath = new Stack<>();
        List<Integer> circuit = new ArrayList<>();

        currPath.push(0);
        int currVertex = 0;

        while(!currPath.empty()){
            if(adj.get(currVertex).size() > 0){
                currPath.push(adj.get(currVertex).get(adj.get(currVertex).size() - 1));
                adj.get(currVertex).remove(adj.get(currVertex).size() - 1);
                currVertex = currPath.peek();
            }
            else{
                circuit.add(currPath.peek());
                currPath.pop();
                if(!currPath.empty())
                    currVertex = currPath.peek();
            }
        }

        for(int i = circuit.size() - 1; i >= 0; i--){
            System.out.print(circuit.get(i));
            if(i != 0)
                System.out.print("->");
        }
    }

    public static String eulerianCircuitString(Map<String, List<String>> adjStrings, int k){

        Map<Integer, List<Integer>> adj = stringMapToIntegerMap(adjStrings);
        Stack<Integer> currPath = new Stack<>();
        List<Integer> circuit = new ArrayList<>();
        List<String> circuitStr = new ArrayList<>();
        List<String> circuitStrBinRev = new ArrayList<>();
        List<String> circuitStrBin = new ArrayList<>();

        currPath.push(0);
        int currVertex = 0;

        while(!currPath.empty()){
            if(adj.get(currVertex).size() > 0){
                currPath.push(adj.get(currVertex).get(adj.get(currVertex).size() - 1));
                adj.get(currVertex).remove(adj.get(currVertex).size() - 1);
                currVertex = currPath.peek();
            }
            else{
                circuit.add(currPath.peek());
                currPath.pop();
                if(!currPath.empty())
                    currVertex = currPath.peek();
            }
        }
        for(int m = 0; m < circuit.size(); m++){
            circuitStr.add(Integer.toBinaryString(circuit.get(m)));
        }
        //////////////////////////////
        for(String str : circuitStr){
            int condition = k-1 - str.length();
            for(int m = 0; m < condition; m++){
                str = "0" + str;
            }
            circuitStrBinRev.add(str);
        }
        /////////////////////////////////
//        String result = "";
//        for(int i = circuit.size() - 1; i >= 0; i--){
//            result += circuit.get(i);
//        }
        for(int i = circuitStrBinRev.size() - 1; i >= 0; i--){
            circuitStrBin.add(circuitStrBinRev.get(i));
        }
        String resultBin = circuitStrBin.get(0);
        for(int l = 1; l < Math.pow(2, k)-k+2; l++){
            String str = circuitStrBin.get(l);
            resultBin += str.substring(str.length() - 1);
        }
        return resultBin;
    }

    private static Map<Integer, List<Integer>> stringMapToIntegerMap(Map<String, List<String>> strMap){
        Map<Integer, List<Integer>> result = new HashMap<>();
        for(Map.Entry ent : strMap.entrySet()){
            String s = ent.getKey().toString();
            List<String> lst = (List<String>) ent.getValue();
            Integer i = Integer.parseInt(s, 2);
            List<Integer> lstInteger = new ArrayList<>();
            for(String str : lst){
                lstInteger.add(Integer.parseInt(str, 2));
            }
            result.put(i, lstInteger);

        }
        return result;
    }

}
