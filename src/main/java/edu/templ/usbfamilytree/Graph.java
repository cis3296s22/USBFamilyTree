package edu.templ.usbfamilytree;

import java.util.*;



/**
 * Graph contains all the underlying functions necessary
 * to make the family tree program work. Adding nodes, edges,
 * performing searches
 */
public class Graph
{
    /**
     * nodes arraylist will contain all nodes, node id corresponds to position in arraylist
     * Adj is the adjacency list for the bidirectional graph
     * id contains the incrementation of the ids of the nodes as new nodes are created
     */

    public ArrayList<Node> nodes = new ArrayList<>();
    public LinkedList<LinkedList<Integer>> Adj = new LinkedList<>(); // adjacency list
    //ID is incremented in the Graph class itself
    public int id;
    public Graph(){
        id = 0;
    }

    /**
     * addNode creates a node, adding it to the nodes arraylist
     * and incrementing the ID as its ID is set to the current ID
     * @param person nodes contain the Person themselves
     * @return returns the node after it's been created
     */
    public Node addNode(Person person)
    {
        //node id should be same as position in arraylist
        Node node = new Node(person, id);
        nodes.add(node);
        id++;
        return node;
    }

    /**
     * Taking the IDs as well as the relationship enum,
     * the function creates edges that are stored in the
     * Nodes themselves as well as adding it to the adjacency list
     * within the Graph class itself.
     * @param src start node id
     * @param dest end node id
     * @param relationship relationship between start and end
     */
    public void addNewEdge(int src, int dest, Edge.Relationship relationship)
    {
        Node source = nodes.get(src);
        Node destination = nodes.get(dest);


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

        //THIS IMPLEMENTATION REQUIRES NODE IDS TO BE CUMULATIVE AND NOT SKIPPING
        //integer adjacency list
        if(Adj.size() <= source.id + 1)
        {
            while(Adj.size() != source.id + 1)
            {
                Adj.add(new LinkedList<>());
            }
        }

        if(Adj.size() < destination.id + 1)
        {
            while(Adj.size() != destination.id + 1)
            {
                Adj.add(new LinkedList<>());
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

    /**
     * This BFS searches just one iteration at a time,
     * adding only immediate neighbors to the queue then stopping
     * @param queue contains the queue of neighbors or nodes we need to visit
     * @param visited boolean array to know whether a node has been visited
     * @param path contains the path taken as we move from node to node
     */
    void BFS(ArrayList<Integer> queue, boolean[] visited, int[] path)
    {
        //pop
        int current = queue.remove(0);

        for(int i = 0; i < Adj.get(current).size(); i++)
        {
            //get neighbors
            int x = Adj.get(current).get(i);
            if(!visited[x])
            {
                //add to queue
                queue.add(x);
                visited[x] = true;
                path[x] = current;
            }
        }
    }

    /**
     * Bidirectional implements a bidirectional search
     * which basically does a BFS from the end node
     * and the start node one at a time searching for
     * the intersecting point, then returning the node
     * path from one node to the other
     * @param start ID of starting node
     * @param end ID of ending node
     * @return returns integer array list of the path of nodes
     */
    ArrayList<Integer> Bidirectional(int start, int end)
    {
        ArrayList<Integer> retpath = new ArrayList<>();

        //keep track of visited
        boolean[] v1 = new boolean[Adj.size()];
        boolean[] v2 = new boolean[Adj.size()];

        //these arrays keep track of the path
        int[] p1 = new int[Adj.size()];
        int[] p2 = new int[Adj.size()];


        //initializing values
        for(int i = 0;i < Adj.size(); i++)
        {
            v1[i]=false;
            v2[i]=false;
            p1[i]=-1;
            p2[i]=-1;
        }


        //stores queues
        ArrayList<Integer> q1=new ArrayList<>();
        ArrayList<Integer> q2=new ArrayList<>();

        //start and end are visited
        q1.add(start);
        v1[start] = true;
        q2.add(end);
        v2[end] = true;

        //initializing intersect point
        int intersect = -1;


        while(q1.size() > 0 && q2.size() > 0)
        {
            //BFS one step at a time
            BFS(q1, v1, p1);
            BFS(q2, v2, p2);

            for(int i = 0; i < Adj.size(); i++)
            {
                //checking to see if any of the nodes we visited match
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
            retpath.add(0, j); //adding intersection to return path array and then path
            j=p1[j]; //this traverses backwards from the intersection to the start
        }

        j=p2[intersect];

        while(j!=-1)
        {
            retpath.add(j); //adding path from after intersection to the end of the search
            j=p2[j];
        }



        return retpath;
    }

    /**/

    /**
     * Part of the bidirectional search is finding the
     * intersection or closest node between two nodes.
     * This can also be used to find the closes relative
     * between two nodes. takes in two IDs and returns an ID
     * @param start starting node ID
     * @param end ending node ID
     * @return returns integer ID of the closest shared node (relative) between start and end
     */
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


    /**/

    /**
     * Takes the Node ID arraylist from Bidirectional and converts it to
     * a string array that contains the edge types.
     * @param s takes start node ID
     * @param d takes end node ID
     * @return String array of edges, or edge path from source to destination
     */
    String[] getNodeEdge(int s, int d)
    {
        //edges is a list of INTEGER IDs
        ArrayList<Integer> edges = Bidirectional(s, d);
        String[] relationships = new String[edges.size() - 1];

        //adding to specific indeces of relationships array
        for(int i = 0; i < relationships.length; i++)
        {
            for(Node n : nodes)
            {
                //checking to see if the edge from the BFS links to the current node
                if(edges.get(i) == n.id)
                {
                    for(Edge e : n.edges)
                    {
                        //basically checks for the edge the Node has with what we
                        //got from BFS and adds the relationship to our String[]
                        if(e.id == edges.get(i + 1))
                        {
                            if(e.relationship == Edge.Relationship.marital)
                            {
                                relationships[i] = "m";
                                //can only be marital, we don't allow for Alabama moments
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

    /*gets ordinal number given an integer
     *for better readability in the findRelationship*/

    /**
     * gets ordinal number given an integer
     * for better readability in the findRelationship
     * @param n any integer
     * @return String of ordinal form of int
     */
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

    /**/

    /**
     * Takes two IDs and performs a BFS
     * then gets their edge relationships
     * and traverses each edge type to find
     * the relationship between the start and end
     * nodes, then prints out the relationship
     * given the types of relationships between
     * each person passed.
     * @param s starting node
     * @param d destination node
     * @return String of the relationship, for example "child" or "1st cousing 2 time(s) removed"
     */
    String findRelationship(int s, int d)
    {
        String path = "";
        /*This String[] will always be in the form
        ancestor*child* or
        child* because the DBFS finds shortest path*/
        String[] rel = getNodeEdge(s, d);
        int ancestorctr = 0;
        int childctr = 0;
        for (String value : rel)
        {
            if (value == "a")
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
                    if(ancestorctr > 5) //don't want output to be too big
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
            if (ancestorctr == 1)
            {
                return "sibling";
            }
            return getOrd(childctr - 1) + " cousin";
        }

        if(ancestorctr > childctr)
        {
            //some type of cousin
            if (childctr == 1)
            { //some type of aunt or uncle
                switch (ancestorctr) //dependent on type of grandparent
                {
                    case 2: {
                        return "aunt or uncle";
                    }
                    case 3: {
                        return "great aunt or uncle";
                    }
                    case 4: {
                        return "great grand aunt or uncle"; //this is a unique case idk why family trees are weird
                    }
                    default: //great +
                    {
                        path = String.format("great^%d ", ancestorctr - 3);
                        return path + "grand aunt or uncle";
                    }
                }
            }//dependent on childctr, if childctr == 2 then first cousin, 3 is second
            //times removed is ancestorctr - childctr
            return getOrd(childctr - 1) + String.format(" cousin %d time(s) removed", ancestorctr - childctr);
        }

        //some type of cousin, last case
        if (ancestorctr == 1)
        { //some type of niece or nephew
            switch (childctr)
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
                    if (childctr > 4)
                    {
                        path = String.format("great^%d ", childctr - 3);
                    }
                    else
                    {
                        for (int i = 0; i < childctr - 3; i++) {
                            path = "great " + path;
                        }
                    }
                    return path + "grand niece or nephew";
                }
            }
        }//dependent on ancestorctr, if ancestorctr == 2 then first cousin, 3 is second
        //times removed is childctr - ancestorctr
        return getOrd(ancestorctr - 1) + String.format(" cousin %d time(s) removed", childctr - ancestorctr);


    }



}