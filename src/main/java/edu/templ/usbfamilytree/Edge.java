package edu.templ.usbfamilytree;

public class Edge
{
    /**
     * Each edge will have a type of either marital, ancestor, or descendant
     * the edge will also contain data about who the edge links TO
     */

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
