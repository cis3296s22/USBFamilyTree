package edu.templ.usbfamilytree;

import java.util.*;

class Graph<T>
{
    // creating an object of the Map class that stores the edges of the graph
    private Map<Node, List<Node>> map = new HashMap<>();

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

        map.get(source).add(destination);
        //id of the edge is the id of the person the source is connecting to
        source.addEdge(marital, ancestor, destination.getID());

        if (marital == true) {
            map.get(destination).add(source);
        }

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
}


