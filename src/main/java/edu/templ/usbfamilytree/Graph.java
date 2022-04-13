package edu.templ.usbfamilytree;

import java.util.*;

class Graph
{
    ArrayList<Node> nodes = new ArrayList<>();
    LinkedList<LinkedList<Integer>> Adj = new LinkedList<LinkedList<Integer>>();; // adjacency list


    void addNode(int id)
    {
        //node id should be same as position in arraylist
        Node node = new Node(id);
        nodes.add(node);
    }

    //marital == true means that the edge is bidirectional
    //if the edge is not marital and not ancestor it is descendant
    public void addNewEdge(int src, int dest, boolean marital, boolean ancestor)
    {
        Node source = nodes.get(src);
        Node destination = nodes.get(dest);

        //CAN UPDATE ALL THE FUNCTIONS TO ONLY NEED NODE ID

        if(marital)
        {

            //Node edge lists get added as well
            source.addEdge(marital, ancestor, destination.id);
            destination.addEdge(marital, ancestor, source.id);
        }

        if(ancestor)
        {
            //SOURCE is the ANCESTOR of DESTINATION

            source.addEdge(marital, ancestor, destination.id);
            destination.addEdge(marital, !ancestor, source.id);
        }


        //THIS IMPLEMENTATION REQUIRES NODE IDS TO BE CUMULATIVE AND NOT SKIPPING NUMBERS
        //integer adjacency list
        if(!Adj.contains(source.id))
        {
            Adj.add(new LinkedList<Integer>());
        }
        if(!Adj.contains(destination.id))
        {
            Adj.add(new LinkedList<Integer>());
        }
        //ERROR WITH IT CREATING MANY MORE LINKEDLISTS THAN EXPECTED

        Adj.get(source.id).add(destination.id);
        Adj.get(destination.id).add(source.id);

    }

    void printadjacencylist()
    {
        for (int i = 0; i < Adj.size(); ++i) {

            // Printing the head
            System.out.print(i + "->");

            for (int v : Adj.get(i)) {
                // Printing the nodes
                System.out.print(v + " ");
            }

            // Now a new lin eis needed
            System.out.println();
        }
    }


    void BFS(ArrayList<Integer> queue, boolean[] visited, int[] parent)
    {
        int current = queue.remove(0);

        for(int i = 0; i < Adj.get(current).size(); i++)
        {
            int x = Adj.get(current).get(i);
            if(!visited[x])
            {
                queue.add(x);
                visited[x] = true;
                parent[x] = current;
            }
        }
    }

    ArrayList Bidirectional(int start, int end)
    {
        ArrayList<Integer> retpath = new ArrayList<>();

        boolean[] v1 = new boolean[Adj.size()];
        boolean[] v2 = new boolean[Adj.size()];
        int[] p1 = new int[Adj.size()];
        int[] p2 = new int[Adj.size()];
        for(int i = 0;i < Adj.size(); i++)
        {
            v1[i]=false;
            v2[i]=false;
            p1[i]=-1;
            p2[i]=-1;
        }


        ArrayList<Integer> q1=new ArrayList<>();
        ArrayList<Integer> q2=new ArrayList<>();
        q1.add(start);
        v1[start] = true;
        q2.add(end);
        v2[end] = true;
        int intersect = -1;


        while(q1.size() > 0 && q2.size() > 0)
        {
            BFS(q1, v1, p1);
            BFS(q2, v2, p2);

            for(int i = 0; i < Adj.size(); i++)
            {
                if(v1[i] && v2[i])
                {
                    intersect = i;
                    break;
                }
            }

            if(intersect != -1) //intersection is found
            {
                break;
            }
        }

        if(intersect == -1)
        {
            System.out.println("no intersection");
        }

        int j = intersect;

        while(j!=-1)
        {
            retpath.add(0, j);
            j=p1[j];
        }

        j=p2[intersect];

        while(j!=-1)
        {
            retpath.add(j);
            j=p2[j];
        }

        System.out.println(intersect);
        System.out.println(retpath);


        return retpath;
    }


    // void getNodeEdge(int s, int d)
    // {
    //     Arraylist edges = Bidirectional(s, d);
    //     String[] relationships = new String[edges.length];

    //     for(int i = 0; i < edges.size() - 1; i++)
    //     {

    //     }
    // }


}
