package edu.templ.usbfamilytree;

import java.util.*;

public class Graph
{
    public ArrayList<Node> nodes = new ArrayList<>();
    public LinkedList<LinkedList<Integer>> Adj = new LinkedList<LinkedList<Integer>>();; // adjacency list
    public int id;
    public Graph(){
        id = 0;
    }
    public Node addNode(Person person)
    {
        //node id should be same as position in arraylist
        Node node = new Node(person, id);
        nodes.add(node);
        id++;
        return node;
    }

    //marital == true means that the edge is bidirectional
    //if the edge is not marital and not ancestor it is descendant
    public void addNewEdge(int src, int dest, Edge.Relationship relationship)
    {
        Node source = nodes.get(src);
        Node destination = nodes.get(dest);

        //CAN UPDATE ALL THE FUNCTIONS TO ONLY NEED NODE ID

        if(relationship == Edge.Relationship.marital)
        {

            //Node edge lists get added as well
            source.addEdge(Edge.Relationship.marital, destination.id);
            destination.addEdge(Edge.Relationship.marital, source.id);
        }

        if(relationship == Edge.Relationship.ancestor)
        {
            //SOURCE is the ANCESTOR of DESTINATION

            source.addEdge(Edge.Relationship.ancestor, destination.id);
            destination.addEdge(Edge.Relationship.descendant, source.id);
        }

        //THIS IMPLEMENTATION REQUIRES NODE IDS TO BE CUMULATIVE AND NOT SKIPPING nS
        //integer adjacency list
        if(Adj.size() <= source.id + 1)
        {
            while(Adj.size() != source.id + 1)
            {
                Adj.add(new LinkedList<Integer>());
            }
        }

        if(Adj.size() < destination.id + 1)
        {
            while(Adj.size() != destination.id + 1)
            {
                Adj.add(new LinkedList<Integer>());
            }
        }



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

    ArrayList<Integer> Bidirectional(int start, int end)
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

        // System.out.println(intersect);
        // System.out.println(retpath);


        return retpath;
    }

    int closestRelative(int start, int end)
    {

        //finds intersection in BFS

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
                return intersect;
            }
        }
        return -1;
    }

    /* */

    String[] getNodeEdge(int s, int d)
    {
        //edges is a list of INTEGER IDs
        ArrayList<Integer> edges = Bidirectional(s, d);
        String[] relationships = new String[edges.size() - 1];

        for(int i = 0; i < relationships.length; i++)
        {
            for(Node n : nodes)
            {
                if(edges.get(i).intValue() == n.id)
                {
                    for(Edge e : n.edges)
                    {
                        if(e.id == edges.get(i + 1).intValue())
                        {
                            // System.out.println("The node we are on is");
                            // System.out.println(n.id);
                            // System.out.println("The edge connects it to");
                            // System.out.println(edges.get(i + 1).intValue());
                            if(e.relationship == Edge.Relationship.marital)
                            {
                                relationships[i] = "m";
                                continue; //can only be marital, we don't allow for Alabama moments
                            }
                            else if(e.relationship == Edge.Relationship.descendant)
                            {
                                relationships[i] = "d";
                            }
                            else
                            {
                                relationships[i] = "a";
                            }
                        }
                    }
                }
            }
        }
        return relationships;
    }

    String getOrd(int n)
    {
        int m100 = n % 100;
        int m10 = n % 10;
        if(m10 == 1 && m100 != 11)
        {
            return n + "st";
        }
        else if(m10 == 2 && m100 != 12)
        {
            return n + "nd";
        }
        else if(m10 == 3 && m100 != 13)
        {
            return n + "rd";
        }
        else
        {
            return n + "th";
        }
    }

    String findRelationship(int s, int d)
    {
        String path = "";
        /*This String[] will always be in the form
        ancestor*child* or
        child* because the DBFS finds shortest path*/
        String[] rel = getNodeEdge(s, d);
        int ancestorctr = 0;
        int childctr = 0;
        for(int i = 0; i < rel.length; i++)
        {
            if(rel[i] == "a")
            {
                ancestorctr++;
            }
            else
            {
                childctr++;
            }
        }

        // System.out.println(ancestorctr);
        // System.out.println(childctr);

        //all child only cases
        if(ancestorctr == 0)
        {
            switch(childctr)
            {
                case 1:
                {
                    return "child";
                }
                case 2:
                {
                    return "grand child";
                }
                default: //great +
                {
                    if(childctr > 5)
                    {
                        path = String.format("great^%d ", childctr - 2);
                    }
                    else
                    {
                        for(int i = 0; i < childctr - 2; i++)
                        {
                            path = "great " + path;
                        }
                    }
                    return path + "grand child";
                }
            }
        }

        //all parent only cases
        if(childctr == 0)
        {
            switch(ancestorctr)
            {
                case 1:
                {
                    return "parent";
                }
                case 2:
                {
                    return "grand parent";
                }
                default: //great +
                {
                    if(ancestorctr > 5)
                    {
                        path = String.format("great^%d ", ancestorctr - 2);
                    }
                    else
                    {
                        for(int i = 0; i < ancestorctr - 2; i++)
                        {
                            path = "great " + path;
                        }
                    }
                    return path + "grand parent";
                }
            }
        }

        if(ancestorctr == childctr) //sibling or cousin
        {
            switch(ancestorctr)
            {
                case 1:
                {
                    return "sibling";
                }
                default:
                {
                    return path = getOrd(childctr - 1) + " cousin";
                }
            }
        }

        if(ancestorctr > childctr)
        {
            switch(childctr)
            {
                case 1: //some type of aunt or uncle
                {
                    switch(ancestorctr) //dependent on type of grandparent
                    {
                        case 2:
                        {
                            return "aunt or uncle";
                        }
                        case 3:
                        {
                            return "great aunt or uncle";
                        }
                        case 4:
                        {
                            return "great grand aunt or uncle"; //this is a unique case idk why family trees are weird
                        }
                        default: //great +
                        {
                            path = String.format("great^%d ", ancestorctr - 3);
                            return path + "grand aunt or uncle";
                        }
                    }
                }
                default: //some type of cousin
                {
                    //dependent on childctr, if childctr == 2 then first cousin, 3 is second
                    //times removed is ancestorctr - childctr
                    return path = getOrd(childctr - 1) + String.format(" cousin %d time(s) removed", ancestorctr - childctr);
                }
            }
        }

        if(ancestorctr < childctr)
        {
            switch(ancestorctr)
            {
                case 1: //some type of niece or nephew
                {
                    switch(childctr)
                    {
                        case 2:
                        {
                            return "niece or nephew";
                        }
                        case 3:
                        {
                            return "grand niece or nephew";
                        }
                        default: //great +
                        {
                            if(childctr > 4)
                            {
                                path = String.format("great^%d ", childctr - 3);
                            }
                            else
                            {
                                for(int i = 0; i < childctr - 3; i++)
                                {
                                    path = "great " + path;
                                }
                            }
                            return path + "grand niece or nephew";
                        }
                    }
                }
                default: //some type of cousin
                {
                    //dependent on ancestorctr, if ancestorctr == 2 then first cousin, 3 is second
                    //times removed is childctr - ancestorctr
                    return path = getOrd(ancestorctr - 1) + String.format(" cousin %d time(s) removed", childctr - ancestorctr);
                }
            }
        }


        return path;
    }



}