package edu.templ.usbfamilytree;

public class Edge {
    public boolean marital;
    public boolean ancestor;
    public int id;
    public Edge(){

    }
    public Edge(boolean marital, boolean ancestor, int id){
        this.marital = marital;
        this.ancestor = ancestor;
        this.id = id;
    }
}
