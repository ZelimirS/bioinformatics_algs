package euler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class EulerPathBigInteger {

    public static void main(String[] args) {
        Map<BigInteger, List<BigInteger>> adj = new HashMap<>();
        adj = AdjecancyListImporter.importAdjecancyBigInteger("/Users/zelimirstojcevic/" +
                "IdeaProjects/bioinformatics_algs/bio_files/radni_1.txt"); // rosalind_ba3g  rosalind_ba3g-2
        printEulerianPath(adj);
    }

    public static void printEulerianPath(Map<BigInteger, List<BigInteger>> adj){

        BigInteger beginVertex = findBeginingVertex(adj);

        Stack<BigInteger> currPath = new Stack<>();
        List<BigInteger> circuit = new ArrayList<>();

        currPath.push(beginVertex);
        BigInteger currVertex = beginVertex;

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

    public static BigInteger findBeginingVertex(Map<BigInteger, List<BigInteger>> adj){
        Map<BigInteger, BigInteger> outEdges = new HashMap<>();
        Map<BigInteger, BigInteger> inEdges = new HashMap<>();
        Set<BigInteger> keySet = new HashSet<>();
        List<BigInteger> keyList = new ArrayList<>();
        for(Map.Entry<BigInteger, List<BigInteger>> ent : adj.entrySet()){
            BigInteger key = ent.getKey();
            outEdges.put(key, BigInteger.valueOf(ent.getValue().size()));
            for(BigInteger i : ent.getValue()){
                keySet.add(i);
                keyList.add(i);
            }
        }

        for(BigInteger in : keySet){
            BigInteger count = BigInteger.valueOf(0);
            inEdges.put(in, count);
            for(BigInteger inn : keyList){
                if(in.equals(inn))
                    inEdges.put(in, count.add(BigInteger.valueOf(1)));
            }
        }

        Set<BigInteger> outEdgesKeys = outEdges.keySet();
        for(BigInteger innn : inEdges.keySet()){
            if(!outEdgesKeys.contains(innn))
                outEdges.put(innn, BigInteger.valueOf(0));
        }
        for(BigInteger i : outEdgesKeys){        ///// new new new new new new new new new new
            if(!inEdges.keySet().contains(i))
                inEdges.put(i, BigInteger.valueOf(0));
        }

        BigInteger beginVertex = BigInteger.valueOf(-1);
        BigInteger endVertex = BigInteger.valueOf(-1);
        for(BigInteger i : outEdgesKeys){
            int compareVal = outEdges.get(i).subtract(inEdges.get(i)).compareTo(BigInteger.valueOf(0));
            if(compareVal > 0)
                beginVertex = i;
            else if(compareVal < 0)
                endVertex = i;
        }
        if(outEdges.get(endVertex).equals(BigInteger.valueOf(0)))
            adj.put(endVertex, new ArrayList<>());// is it possible endVertex to have out edges? let's say it is
        

        return beginVertex;
    }

}
