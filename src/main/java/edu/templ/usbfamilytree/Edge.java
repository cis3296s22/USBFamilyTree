package edu.templ.usbfamilytree;

/**
 * Each edge will have a type of either marital, ancestor, or descendant
 * the edge will also contain data about who the edge links TO
 */
public class Edge
{

    /**
     * Edge stores relationship data
     */
    public Relationship relationship;
    /**
     * Edge stores ID that it connects to
     */
    public int id;

    /**
     * Enum of all types of relationships:
     * marital, ancestor, and descendant
     */
    public enum Relationship{
        /**
         * this Person is married to another Person
         */
        marital,
        /**
         * this Person is an ancestor of the Person the Edge connects to
         */
        ancestor,
        /**
         * this Person is a descendant of the Person the Edge connects to
         */
        descendant
    }

    /**
     * Edge constructor for empty edges
     */
    public Edge(){

    }

    /**
     * Edge constructor
     * @param relationship takes edge relationship from source TO destination
     * @param id takes ID of destination
     */
    public Edge(Relationship relationship, int id){
        this.relationship = relationship;
        this.id = id;
    }
}
