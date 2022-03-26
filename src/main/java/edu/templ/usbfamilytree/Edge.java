package edu.templ.usbfamilytree;

public class Edge {
    private boolean marital;
    private boolean ancestor;
    private int id;
    public Edge(){

    }
    public Edge(boolean marital, boolean ancestor, int id){
        this.marital = marital;
        this.ancestor = ancestor;
        this.id = id;
    }
}
