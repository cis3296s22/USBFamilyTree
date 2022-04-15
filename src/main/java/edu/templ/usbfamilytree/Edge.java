package edu.templ.usbfamilytree;

public class Edge {
    public Relationship relationship;
    public int id;

    public enum Relationship{
        marital,
        ancestor,
        descendant
    }

    public Edge(){

    }
    public Edge(Relationship relationship, int id){
        this.relationship = relationship;
        this.id = id;
    }
}
