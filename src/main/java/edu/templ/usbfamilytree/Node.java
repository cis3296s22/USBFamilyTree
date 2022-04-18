package edu.templ.usbfamilytree;

import java.util.ArrayList;

public class Node
{
    /**
     * Each Node contains a Person, the ID of the Node, as well
     * as an arraylist that contains all the Edges that this
     * Node uses to connect to other Nodes
     */
    public Person person;
    public ArrayList<Edge> edges = new ArrayList<>();
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


}
