package euler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class EulerPath {

    public static void main(String[] args) {
        Map<Integer, List<Integer>> adj = new HashMap<>();
        adj = AdjecancyListImporter.importAdjecancy("/Users/zelimirstojcevic/" +
                "IdeaProjects/bioinformatics_algs/bio_files/radni_1.txt"); // rosalind_ba3g  rosalind_ba3g-2
        printEulerianPath(adj);
    }

    public static void printEulerianPath(Map<Integer, List<Integer>> adj){

        int beginVertex = findBeginingVertex(adj);

        Stack<Integer> currPath = new Stack<>();
        List<Integer> circuit = new ArrayList<>();

        currPath.push(beginVertex);
        int currVertex = beginVertex;

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

        String result = "";
        for(int i = circuit.size() - 1; i >= 0; i--){
            //System.out.print(circuit.get(i));
            result += circuit.get(i);
            if(i != 0)
                //System.out.print("->");
                result += "->";
        }
        //System.out.println(result);

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/zelimirstojcevic/" +
                    "IdeaProjects/bioinformatics_algs/bio_files/result_file.txt"));
            writer.write(result);

            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static int findBeginingVertex(Map<Integer, List<Integer>> adj){
        Map<Integer, Integer> outEdges = new HashMap<>();
        Map<Integer, Integer> inEdges = new HashMap<>();
        Set<Integer> keySet = new HashSet<>();
        List<Integer> keyList = new ArrayList<>();
        for(Map.Entry<Integer, List<Integer>> ent : adj.entrySet()){
            Integer key = ent.getKey();
            outEdges.put(key, ent.getValue().size());
            for(Integer i : ent.getValue()){
                keySet.add(i);
                keyList.add(i);
            }
        }

        for(Integer in : keySet){
            int count = 0;
            inEdges.put(in, count);
            for(Integer inn : keyList){
                if(in.equals(inn))
                    inEdges.put(in, ++count);
            }
        }

        Set<Integer> outEdgesKeys = outEdges.keySet();
        for(Integer innn : inEdges.keySet()){
            if(!outEdgesKeys.contains(innn))
                outEdges.put(innn, 0);
        }
        for(Integer i : outEdgesKeys){        ///// new new new new new new new new new new
            if(!inEdges.keySet().contains(i))
                inEdges.put(i, 0);
        }

        int beginVertex = -1;
        int endVertex = -1;
        for(Integer i : outEdgesKeys){
            if(outEdges.get(i) - inEdges.get(i) > 0)
                beginVertex = i;
            else if(outEdges.get(i) - inEdges.get(i) < 0)
                endVertex = i;
        }
        if(outEdges.get(endVertex) == 0)
            adj.put(endVertex, new ArrayList<>());// is it possible endVertex to have out edges? let's say it is
        

        return beginVertex;
    }

}
