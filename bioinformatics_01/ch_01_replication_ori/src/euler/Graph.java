package euler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Graph {
    private int vertexNumber;
    private List<Integer>[] adj;
    private int[] inDegrees;

    public int getVertexNumber() { return vertexNumber; }

    public Graph(int vertexNumber) {
        this.vertexNumber = vertexNumber;
        adj = new LinkedList[vertexNumber];
        inDegrees = new int[vertexNumber];
        for(int i = 0; i < vertexNumber; i++){
            adj[i] = new LinkedList<>();
            inDegrees[i] = 0;
        }
    }

    public void addEdge(int v, int w){
        adj[v].add(w);
        inDegrees[w]++;
    }

    public void DFSUtil(int v, Boolean[] visited){
        visited[v] = true;
        int n;
        Iterator<Integer> iter = adj[v].iterator();
        while (iter.hasNext()){
            n = iter.next();
            if(!visited[n] == true){
                DFSUtil(n, visited);
            }
        }
    }

    public Graph getTranspose(){
        Graph gr = new Graph(vertexNumber);
        for(int i = 0; i < vertexNumber; i++){
            Iterator<Integer> iter = adj[i].listIterator();
            while (iter.hasNext()){
                gr.adj[iter.next()].add(i);
                gr.inDegrees[i]++;
            }
        }
        return gr;
    }

    public Boolean isStronglyConnected(){
        Boolean[] visited = new Boolean[vertexNumber];
        for (int i = 0; i < vertexNumber; i++){
            visited[i] = false;
        }

        DFSUtil(0, visited);
        for(int i = 0; i < vertexNumber; i++){
            if (visited[i] == false)
                return false;
        }

        Graph gra = getTranspose();
        for (int i = 0; i < vertexNumber; i++){
            visited[i] = false;
        }
        gra.DFSUtil(0, visited);
        for(int i = 0; i < vertexNumber; i++){
            if (visited[i] == false)
                return false;
        }

        return true;
    }

    public Boolean isEulerianCycle(){
        if (isStronglyConnected() == false)
            return false;

        for (int i = 0; i < vertexNumber; i++){
            if (adj[i].size() != inDegrees[i])
                return false;
        }

        return true;
    }

    public static void printAdjecancyList(Graph g){
        for (int i = 0; i < g.vertexNumber; i++){
            System.out.print(i + " -> ");
            List<Integer> lst = g.adj[i];
            for(int j = 0; j < lst.size(); j++){
                if(j < lst.size()-1){
                    System.out.print(lst.get(j) + ", ");
                }
                else {
                    System.out.print(lst.get(j));
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph(10);
        g.addEdge(0,3);
        g.addEdge(1,0);
        g.addEdge(2,1);
        g.addEdge(2,6);
        g.addEdge(3,2);
        g.addEdge(4,2);
        g.addEdge(5,4);
        g.addEdge(6,5);
        g.addEdge(6,8);
        g.addEdge(7,9);
        g.addEdge(8,7);
        g.addEdge(9,6);

        if (g.isEulerianCycle()){
            System.out.println("Given directed graph is eulerian");
            printAdjecancyList(g);
        }
        else{
            System.out.println("Given directed grapg is not eulerian");
        }
    }

}
