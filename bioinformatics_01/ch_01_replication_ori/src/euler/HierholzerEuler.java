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

}
