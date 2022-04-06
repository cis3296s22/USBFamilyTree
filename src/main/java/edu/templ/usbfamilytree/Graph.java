package edu.templ.usbfamilytree;


import java.util.*;

class Graph
{
    // creating an object of the Map class that stores the edges of the graph
    private Map<Node, List<Node>> map = new HashMap<>();
    private int V = Integer.MAX_VALUE - 5; //number of nodes/vertices
    ArrayList<Integer>[] Adj = new ArrayList[V]; // adjacency list but in integer form for BFS
    public Graph(){


    }
    // the method adds a new vertex to the graph
    public void addNewVertex(Node s)
    {
        map.put(s, new LinkedList<Node>());
    }

    //marital == true means that the edge is bidirectional
    //if the edge is not marital and not ancestor it is descendant
    public void addNewEdge(Node source, Node destination, boolean marital, boolean ancestor)
    {

        if (!map.containsKey(source))
            addNewVertex(source);

        if (!map.containsKey(destination))
            addNewVertex(destination);

        if(marital)
        {
            map.get(source).add(destination);
            map.get(destination).add(source);

            //Node edge lists get added as well
            source.addEdge(marital, ancestor, destination.getID());
            destination.addEdge(marital, ancestor, source.getID());
        }

        if(ancestor)
        {
            //SOURCE is the ANCESTOR of DESTINATION
            map.get(source).add(destination);
            map.get(destination).add(source);

            source.addEdge(marital, ancestor, destination.getID());
            destination.addEdge(marital, !ancestor, source.getID());
        }

        //integer adjacency list
        Adj[source.getID()].add(destination.getID());
        Adj[destination.getID()].add(source.getID());

    }

    public void containsVertex(Node s) {
        if (map.containsKey(s)) {
            System.out.println("The graph contains " + s + " as a vertex.");
        } else {
            System.out.println("The graph does not contain " + s + " as a vertex.");
        }
    }


    public void containsEdge(Node s, Node d) {
        if (map.get(s).contains(d)) {
            System.out.println("The graph has an edge between " + s + " and " + d + ".");
        } else {
            System.out.println("There is no edge between " + s + " and " + d + ".");
        }
    }




    void BFS(ArrayList<Integer> queue, boolean[] visited, int[] parent)
    {
        int current = queue.remove(0);
        for(int i = 0;i < Adj[current].size();i++)
        {
            int x = Adj[current].get(i);
            if(!visited[x])
            {
                queue.add(x);
                visited[x] = true;
                parent[x] = current;
            }
        }
    }


    //adjacency list - write code to store nodes according to IDs
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        // foreach loop that iterates over the keys
        for (Node v : map.keySet()) {
            builder.append(v.toString() + ": ");
            // foreach loop for getting the vertices
            for (Node w : map.get(v)) {
                builder.append(w.toString() + " ");
            }
            builder.append("\n");
        }
        return (builder.toString());
    }





    String BidirectionalSearchPath(Graph G, int src, int dest)
    {
        String path = "";
        boolean[] Visited1 = new boolean[V];
        boolean[] Visited2 = new boolean[V];
        int[] parent1 = new int[V];
        int[] parent2 = new int[V];
        for(int i=0;i<V;i++){
            Visited1[i] = false;
            Visited2[i] = false;
            parent1[i] = -1;
            parent2[i] = -1;
        }
        ArrayList<Integer> queue1 = new ArrayList<>();
        ArrayList<Integer> queue2 = new ArrayList<>();
        queue1.add(src);
        Visited1[src]=true;
        queue2.add(dest);
        Visited2[dest]=true;
        int intersection = -1;
        while(queue1.size() > 0 && queue2.size() > 0 && intersection == -1)
        {
            BFS(queue1, Visited1, parent1);
            BFS(queue2, Visited2, parent2);

            for(int i = 0;i < V;i++)
            {
                if(Visited1[i] && Visited2[i])
                {
                    intersection = i;
                    break;
                }
            }
        }

        if(intersection == -1)
            return "";

        String path1 = "";
        int j = intersection;
        while(j != -1){
            path1 = j + " " + path1;
            j = parent1[j];
        }
        String path2 = "";
        j=parent2[intersection];
        while(j!=-1)
        {
            path2=path2+j+" ";
            j=parent2[j];
        }
        path = path1 + path2;
        return path;
    }

}