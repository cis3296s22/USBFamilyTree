package edu.templ.usbfamilytree;

import java.util.ArrayList;

public class Node
{
    //each Node will contain a Person, the Node's ID, and its arraylist of edges
    public Person person;
    public ArrayList<Edge> edges = new ArrayList<>();
    public int id;

    public Node(Person person, int id)
    {
        this.person = person;
        this.edges = new ArrayList<Edge>();
        this.id = id;
    }

    //adds edge to the Node edges arraylist
    public void addEdge(Edge.Relationship relationship, int id)
    {
        edges.add(new Edge(relationship, id));
    }


}
