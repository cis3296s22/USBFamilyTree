package edu.templ.usbfamilytree;

import java.util.ArrayList;
/**
 * Each Node contains a Person, the ID of the Node, as well
 * as an arraylist that contains all the Edges that this
 * Node uses to connect to other Nodes
 */
public class Node
{

    /**
     * Node contains a Person
     */
    public Person person;
    /**
     * Node contains a list of nodes that it connects to- edges are stored with IDs
     */
    public ArrayList<Edge> edges = new ArrayList<>();
    /**
     * Each Node has a unique ID, incremented in Graph class each time a new Node is created
     */
    public int id;

    /**
     * Node constructor
     * @param person Each node contains a person
     * @param id As well as their ID
     */
    public Node(Person person, int id)
    {
        this.person = person;
        this.edges = new ArrayList<Edge>();
        this.id = id;
    }

    /**
     * adds edge to the Node edges arraylist
     * @param relationship each Edge contains the type of relationship
     * @param id stores the ID of Node this edge connects to
     */
    public void addEdge(Edge.Relationship relationship, int id)
    {
        edges.add(new Edge(relationship, id));
    }

    public boolean isMarried() {
        for (Edge e : edges) {
            if (e.relationship == Edge.Relationship.marital) {
                return true;
            }
        }
        return false;
    }

}
