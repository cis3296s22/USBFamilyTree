package edu.templ.usbfamilytree;

import java.util.ArrayList;

public class Node
{
    public Person person;
    public ArrayList<Edge> edges = new ArrayList<>();
    public int id;

    public Node(Person person, int id)
    {
        this.person = person;
        this.edges = new ArrayList<Edge>();
        this.id = id;
    }

    public void addEdge(boolean marital, boolean ancestor, int id)
    {
        edges.add(new Edge(marital, ancestor, id));
    }


}
